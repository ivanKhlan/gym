CREATE TABLE folder
(
    id         SERIAL PRIMARY KEY NOT NULL,
    title      VARCHAR(70) UNIQUE NOT NULL,
    visible    BOOLEAN   DEFAULT true,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE files
(
    id         SERIAL PRIMARY KEY NOT NULL,
    name       VARCHAR(77) NOT NULL,
    visible    BOOLEAN   DEFAULT true,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    folder_id  INT REFERENCES folder(id) NOT NULL
);