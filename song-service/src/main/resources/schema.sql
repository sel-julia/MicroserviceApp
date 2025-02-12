DROP TABLE IF EXISTS song;

CREATE TABLE song (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    artist VARCHAR(255),
    album VARCHAR(255),
    year VARCHAR(255),
    duration VARCHAR(255)
);