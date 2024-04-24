CREATE TABLE applications
(
    id               SERIAL PRIMARY KEY,
    backend_version  VARCHAR(70)  NOT NULL,
    frontend_version VARCHAR(70)  NOT NULL,
    name             VARCHAR(77)  NOT NULL,
    image            VARCHAR(255) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    key              VARCHAR(70)  NOT NULL,
    text             TEXT,
    licence_type     VARCHAR(100) NOT NULL,
    type_id          INT          NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at       TIMESTAMP DEFAULT NULL
);