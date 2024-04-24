CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    value VARCHAR(70) NOT NULL,
    visible BOOLEAN DEFAULT 1,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    deleted_at TIMESTAMP DEFAULT NULL

);