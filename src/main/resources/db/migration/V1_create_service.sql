CREATE TABLE services (
    id SERIAL PRIMARY KEY,
    title VARCHAR(70) NOT NULL,
    description VARCHAR(255) NOT NULL,
    image VARCHAR(255) NOT NULL,
    price NUMERIC(5,2),
    counts INTEGER NOT NULL,
    start_date TIMESTAMP DEFAULT NOW(),
    end_date TIMESTAMP DEFAULT NOW(),
    text TEXT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    deleted_at TIMESTAMP DEFAULT NULL
    );