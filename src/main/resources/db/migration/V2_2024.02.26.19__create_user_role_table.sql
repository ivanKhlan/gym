CREATE TABLE IF NOT EXISTS users_roles
(
    user_id INT8 NOT NULL,
    role_id INT8 NOT NULL,
    PRIMARY KEY (user_id, role_id),
    foreign key (user_id) references users(id),
    foreign key (role_id) references roles(id)
    );