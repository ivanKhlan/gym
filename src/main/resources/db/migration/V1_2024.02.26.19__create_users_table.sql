CREATE TABLE IF NOT EXISTS users
(
    id                SERIAL PRIMARY KEY,
    first_name        VARCHAR(70)  NOT NULL,
    last_name         VARCHAR(70)  NOT NULL,
    email             VARCHAR(70) UNIQUE,
    email_verified_at TIMESTAMP DEFAULT NULL,
    password          VARCHAR(70)  NOT NULL,
    remember_token    VARCHAR(70) DEFAULT NULL,
    photo             VARCHAR(255) DEFAULT NULL,
    phone_number      VARCHAR(17)  NOT NULL,
    visible           BOOLEAN   DEFAULT 'true',
    created_at        TIMESTAMP DEFAULT NOW(),
    updated_at        TIMESTAMP DEFAULT NOW(),
    deleted_at        TIMESTAMP DEFAULT NULL
);


