CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    password VARCHAR(255),
    role VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS flight (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    airline VARCHAR(255) NOT NULL,
    from_place VARCHAR(255) NOT NULL,
    to_place VARCHAR(255) NOT NULL,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    price INT NOT NULL,
    total_seats INT NOT NULL,
    available_seats INT NOT NULL
);

CREATE TABLE IF NOT EXISTS ticket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pnr VARCHAR(255),
    user_id BIGINT,
    departure_flight_id BIGINT,
    return_flight_id BIGINT,
    trip_type VARCHAR(50),
    seats_booked VARCHAR(255),
    total_price DOUBLE,
    booking_time DATETIME,
    cancel BOOLEAN
);

CREATE TABLE IF NOT EXISTS passenger (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    age INT NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    meal_preference VARCHAR(50),
    ticket_id BIGINT
);
