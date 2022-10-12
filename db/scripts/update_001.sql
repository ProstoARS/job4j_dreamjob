CREATE TABLE if NOT Exists post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   city_id int,
   description text,
   created timestamp,
   visible boolean not null
);