CREATE TABLE IF NOT EXISTS reset_password_tokens (
                                                     id SERIAL PRIMARY KEY,
                                                     token_body VARCHAR(255) NOT NULL,
                                                     expiry_date TIMESTAMP NOT NULL,
                                                     user_id BIGINT NOT NULL,
                                                     CONSTRAINT key FOREIGN KEY (user_id) REFERENCES users(id)
);