CREATE TABLE options
(
    id         SERIAL PRIMARY KEY,
    autoload   BOOLEAN   DEFAULT true,
    key        VARCHAR(70) NOT NULL,
    value      TEXT NULL,
    visible    BOOLEAN   DEFAULT true,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    deleted_at TIMESTAMP DEFAULT NULL
);