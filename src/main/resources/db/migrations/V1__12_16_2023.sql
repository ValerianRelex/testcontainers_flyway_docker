CREATE TABLE IF NOT EXISTS temp
(
    id SERIAL PRIMARY KEY,
    city varchar(32),
    temp_in_celsius numeric(5,2)
)