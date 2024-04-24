CREATE TABLE folder_types
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR(70) UNIQUE NOT NULL,
    visible    BOOLEAN   DEFAULT true,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE files
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(77) NOT NULL,
    visible    BOOLEAN   DEFAULT true,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),

    folder_id  INT REFERENCES folder_types(id) NOT NULL
);