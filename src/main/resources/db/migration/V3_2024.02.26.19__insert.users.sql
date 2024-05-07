INSERT INTO users(first_name, last_name, email, password, phone_number)
VALUES ('Volodymyr', 'Holovetskyi', 'vholo@gmail.com', '$2a$12$LhgqSN83Tl0fcLbENLtA/.AhBkxtIE6m5dGanSP3rmmsUT4fTYqHS',
        '+380976545231');

INSERT INTO users_roles(user_id, role_id)
VALUES (1, 1);