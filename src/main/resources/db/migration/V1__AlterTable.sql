CREATE TABLE IF NOT EXISTS users_details
(
    id           uuid  PRIMARY KEY,
    email        VARCHAR(50),
    birthdate    DATE,
    phone_number VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS users
(
    id         uuid  PRIMARY KEY,
    username   VARCHAR(60)  NOT NULL unique,
    password   VARCHAR(200) NOT NULL,
    details_id uuid references users_details (id)
);
CREATE TABLE IF NOT EXISTS roles
(
    id   uuid  PRIMARY KEY,
    name varchar(30)
);
CREATE TABLE IF NOT EXISTS users_roles
(

    user_id uuid references users (id) ,
    role_id uuid references roles (id),
    primary key (user_id,role_id)
);

CREATE TABLE IF NOT EXISTS rooms
(
    id       uuid  PRIMARY KEY,
    type     varchar(70),
    price    DECIMAL,
    area     FLOAT,
    capacity INT
);
CREATE TABLE IF NOT EXISTS booking
(
    id        uuid  PRIMARY KEY,
    user_id   uuid references users (id),
    room_id   uuid references rooms (id),
    startdate DATE,
    enddate   DATE,
    status    VARCHAR(30)
);