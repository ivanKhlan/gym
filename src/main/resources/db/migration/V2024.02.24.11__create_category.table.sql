CREATE TABLE categories
(
    id         SERIAL PRIMARY KEY,
    value      VARCHAR(70) NOT NULL,
    visible    BOOLEAN   DEFAULT 'true',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    deleted_at TIMESTAMP DEFAULT NULL
);