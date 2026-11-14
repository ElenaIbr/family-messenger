package com.elenaibraeva.familymessenger.core.security

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AndroidSecureStorage(context: Context) : SecureStorage {

    private val preferences = context.applicationContext
        .getSharedPreferences(
            STORAGE_NAME,
            Context.MODE_PRIVATE
        )

    private val keyStore = KeyStore
        .getInstance("AndroidKeyStore")
        .apply { load(null) }

    private val lock = Any()

    override suspend fun write(key: String, value: String): Unit = withContext(Dispatchers.IO) {
        synchronized(lock) {
            val cipher = Cipher.getInstance(TRANSFORMATION)

            cipher.init(
                Cipher.ENCRYPT_MODE,
                getOrCreateSecretKey()
            )

            val encrypted = cipher.doFinal(
                value.toByteArray(Charsets.UTF_8)
            )

            val storedValue =
                "${encode(cipher.iv)}:${encode(encrypted)}"

            check(
                preferences.edit()
                    .putString(key, storedValue)
                    .commit()
            )
        }
    }

    override suspend fun read(key: String): String? = withContext(Dispatchers.IO) {
        synchronized(lock) {
            val storedValue = preferences.getString(key, null)
                ?: return@synchronized null

            val (iv, encrypted) = storedValue.split(":", limit = 2)

            val cipher = Cipher.getInstance(TRANSFORMATION)

            val secretKey = keyStore.getKey(KEY_ALIAS, null)
                as? SecretKey
                ?: error("Encryption key is missing")

            cipher.init(
                Cipher.DECRYPT_MODE,
                secretKey,
                GCMParameterSpec(128, decode(iv))
            )

            cipher.doFinal(decode(encrypted))
                .toString(Charsets.UTF_8)
        }
    }

    override suspend fun delete(key: String): Unit = withContext(Dispatchers.IO) {
        synchronized(lock) {
            check(preferences.edit().remove(key).commit())
        }
    }

    private fun getOrCreateSecretKey(): SecretKey {
        val existingKey = keyStore.getKey(KEY_ALIAS, null)
            as? SecretKey

        if (existingKey != null) return existingKey

        return KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        ).apply {
            init(
                KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or
                        KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(
                        KeyProperties.ENCRYPTION_PADDING_NONE
                    )
                    .setKeySize(256)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    private fun encode(value: ByteArray): String = Base64.encodeToString(value, Base64.NO_WRAP)

    private fun decode(value: String): ByteArray = Base64.decode(value, Base64.NO_WRAP)

    private companion object {
        const val STORAGE_NAME = "secure_storage"
        const val KEY_ALIAS = "family_messenger_storage_key"
        const val TRANSFORMATION = "AES/GCM/NoPadding"
    }
}
