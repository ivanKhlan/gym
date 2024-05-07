CREATE TABLE user_history
(
    id         SERIAL PRIMARY KEY,
    user_id    INT,
    text       VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);