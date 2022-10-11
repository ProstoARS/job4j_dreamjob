ALTER TABLE post
ADD COLUMN if not exists
city_id int,
ADD COLUMN if not exists
description text,
ADD COLUMN if not exists
created timestamp,
ADD COLUMN if not exists
visible boolean not null;