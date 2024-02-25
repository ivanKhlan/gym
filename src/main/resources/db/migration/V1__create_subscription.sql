CREATE TABLE `subscriptions` (
                                 `id` SERIAL PRIMARY KEY,
                                 `description` VARCHAR(255) NOT NULL,
                                 `freezing` BOOLEAN DEFAULT 1,
                                 `price` NUMERIC(5,2),
                                 `image` VARCHAR(255) NOT NULL,
                                 `start_date` TIMESTAMP DEFAULT NOW(),
                                 `end_date` TIMESTAMP DEFAULT NOW(),
                                 `days_freezing` TIMESTAMP DEFAULT NULL,
                                 `expiration_at` TIMESTAMP DEFAULT NULL,
                                 `discount` BOOLEAN DEFAULT 0,
                                 `discount_sum` TIMESTAMP DEFAULT NOW(),
                                 `discount_date` TIMESTAMP DEFAULT NOW(),
                                 `text` TEXT NOT NULL,
                                 `title` VARCHAR(255) NOT NULL,
                                 `status_id` INT NOT NULL,
                                 `lastname` VARCHAR(255) NOT NULL
);