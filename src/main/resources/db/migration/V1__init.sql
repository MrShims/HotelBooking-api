CREATE TABLE IF NOT EXISTS users_details
(
    id           SERIAL PRIMARY KEY,
    email        VARCHAR(255),
    birthdate    DATE,
    phone_number VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(255) NOT NULL unique,
    password   VARCHAR(255) NOT NULL,
    details_id INT references users_details (id)
);
CREATE TABLE IF NOT EXISTS roles
(
    id   SERIAL PRIMARY KEY,
    name varchar(30)
);
CREATE TABLE IF NOT EXISTS users_roles
(

    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) references users (id),
    FOREIGN KEY (role_id) references roles (id)
);

CREATE TABLE IF NOT EXISTS rooms
(
    id       SERIAL PRIMARY KEY,
    type     varchar(255),
    price    DECIMAL,
    area     FLOAT,
    capacity INT
);
CREATE TABLE IF NOT EXISTS booking
(
    id        BIGSERIAL PRIMARY KEY,
    user_id   INT,
    room_id   INT,
    startdate DATE,
    enddate   DATE,
    status    VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (room_id) REFERENCES rooms (id)
);



