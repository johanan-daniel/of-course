CREATE TABLE IF NOT EXISTS Users (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(254) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);