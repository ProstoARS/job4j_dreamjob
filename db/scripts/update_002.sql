CREATE TABLE if NOT Exists candidates (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description text,
   created timestamp,
   visible boolean not null,
   photo bytea;
);