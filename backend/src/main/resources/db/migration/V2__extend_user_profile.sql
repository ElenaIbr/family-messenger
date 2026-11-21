
-- Extend user profile
ALTER TABLE users
    ADD COLUMN first_name VARCHAR(100),
    ADD COLUMN last_name VARCHAR(100),
    ADD COLUMN date_of_birth DATE,
    ADD COLUMN avatar_url TEXT,
    ADD COLUMN show_birthday BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

-- Backfill existing users
UPDATE users
SET first_name = display_name
WHERE first_name IS NULL;

-- Require first name for new users
ALTER TABLE users
    ALTER COLUMN first_name SET NOT NULL;

-- Validate profile fields
ALTER TABLE users
    ADD CONSTRAINT users_first_name_check
        CHECK (LENGTH(TRIM(first_name)) > 0),
    ADD CONSTRAINT users_last_name_check
        CHECK (
            last_name IS NULL
            OR LENGTH(TRIM(last_name)) > 0
        ),
    ADD CONSTRAINT users_date_of_birth_check
        CHECK (
            date_of_birth IS NULL
            OR date_of_birth <= CURRENT_DATE
        );
