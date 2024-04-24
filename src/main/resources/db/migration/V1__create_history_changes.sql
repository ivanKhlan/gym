CREATE TABLE history_changes
(
    id         SERIAL PRIMARY KEY,
    user_id    INT,
    text       VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW()
);