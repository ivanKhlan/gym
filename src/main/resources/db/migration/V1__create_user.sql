CREATE TABLE users
(
    id                SERIAL PRIMARY KEY,
    first_name        VARCHAR(70)  NOT NULL,
    last_name         VARCHAR(70)  NOT NULL,
    email             VARCHAR(70) UNIQUE,
    email_verified_at TIMESTAMP DEFAULT NULL,
    password          VARCHAR(70)  NOT NULL,
    remember_token    VARCHAR(70),
    photo             VARCHAR(255),
    phone_number      VARCHAR(17)  NOT NULL,
    visible           BOOLEAN   DEFAULT 'true',
    created_at        TIMESTAMP DEFAULT NOW(),
    updated_at        TIMESTAMP DEFAULT NOW(),
    deleted_at        TIMESTAMP DEFAULT NULL
);

CREATE TABLE users_roles
(
    role VARCHAR(255) NOT NULL,
    user_id INTEGER NOT NULL
);

alter table users_roles
add constraint fk_roles_users foreign key (user_id) references users;

insert into users (id, first_name, last_name, email, password, phone_number, visible, created_at, updated_at)
values (1, 'Volodymry', 'Holovetskyi', 'vholo@gmail.com', '$2a$12$v.1GhQ0QFKesfVEDf/EeD.Y.OUuqO1q.Aqmkg5whtKvy4Ai4Nd6PO',
        '+380976545231',true, now(), now());

insert into users_roles (role, user_id)
values ('ROLE_ADMIN', 1);


