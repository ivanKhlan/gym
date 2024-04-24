CREATE TABLE accounts
(
    id         SERIAL PRIMARY KEY,
    user_id    INT NOT NULL,
    balance    NUMERIC(5, 2),
    order_id   INT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    deleted_at TIMESTAMP DEFAULT NULL
);