-- GENDER --
CREATE TABLE genders (
                         id SERIAL PRIMARY KEY,
                         title VARCHAR(70) NOT NULL,
                         visible BOOLEAN DEFAULT 'true',
                         created_at TIMESTAMP DEFAULT NOW(),
                         updated_at TIMESTAMP DEFAULT NOW(),
                         deleted_at TIMESTAMP
);

-- TYPE --
CREATE TABLE types (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(70) NOT NULL,
                       visible BOOLEAN DEFAULT 'true',
                       created_at TIMESTAMP DEFAULT NOW(),
                       updated_at TIMESTAMP DEFAULT NOW(),
                       deleted_at TIMESTAMP
);