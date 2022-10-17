CREATE TABLE if NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  name varchar (60),
  email varchar (60) UNIQUE,
  password varchar (60)
);