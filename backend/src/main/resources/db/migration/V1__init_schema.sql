
-- Users
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       display_name VARCHAR(100) NOT NULL,
                       created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                       CONSTRAINT users_display_name_check
                           CHECK (LENGTH(TRIM(display_name)) > 0)
);

-- Chats
CREATE TABLE chats (
                       id UUID PRIMARY KEY,
                       type VARCHAR(20) NOT NULL,
                       name VARCHAR(100),
                       created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                       CONSTRAINT chats_type_check
                           CHECK (type IN ('DIRECT', 'GROUP')),

                       CONSTRAINT chats_name_check
                           CHECK (type = 'GROUP' OR name IS NULL),

                       CONSTRAINT chats_group_name_check
                           CHECK (name IS NULL OR LENGTH(TRIM(name)) > 0)
);

-- Chat members
CREATE TABLE chat_members (
                              chat_id UUID NOT NULL,
                              user_id UUID NOT NULL,
                              joined_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                              PRIMARY KEY (chat_id, user_id),

                              CONSTRAINT fk_chat_members_chat
                                  FOREIGN KEY (chat_id)
                                      REFERENCES chats(id),

                              CONSTRAINT fk_chat_members_user
                                  FOREIGN KEY (user_id)
                                      REFERENCES users(id)
);

-- Messages
CREATE TABLE messages (
                          id UUID PRIMARY KEY,
                          chat_id UUID NOT NULL,
                          sender_id UUID NOT NULL,
                          content TEXT NOT NULL,
                          created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                          CONSTRAINT fk_messages_chat
                              FOREIGN KEY (chat_id)
                                  REFERENCES chats(id),

                          CONSTRAINT fk_messages_sender
                              FOREIGN KEY (sender_id)
                                  REFERENCES users(id),

                          CONSTRAINT messages_content_check
                              CHECK (
                                  LENGTH(TRIM(content)) > 0
                                      AND CHAR_LENGTH(content) <= 4000
                                  )
);

-- Indexes
CREATE INDEX idx_chat_members_user_id
    ON chat_members(user_id);

CREATE INDEX idx_messages_chat_created_at
    ON messages(chat_id, created_at DESC, id DESC);

CREATE INDEX idx_messages_sender_id
    ON messages(sender_id);
