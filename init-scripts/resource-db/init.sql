DROP TABLE IF EXISTS resource;

CREATE TABLE resource
(
    id SERIAL PRIMARY KEY,
    file_data OID NOT NULL
);