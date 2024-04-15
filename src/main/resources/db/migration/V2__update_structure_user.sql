ALTER TABLE users ADD column notation TEXT,
ADD column birthday TIMESTAMP NOT NULL,
ADD column gender_id INT NOT NULL,
ADD column type_id INT DEFAULT 1;