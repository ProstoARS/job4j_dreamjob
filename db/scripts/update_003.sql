CREATE TABLE if NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  name varchar (60) NOT NULL,
  email varchar (60) UNIQUE NOT NULL,
  password varchar (60) NOT NULL
);