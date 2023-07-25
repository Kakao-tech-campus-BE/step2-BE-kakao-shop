ALTER TABLE user_entity
    DROP COLUMN created_date;

ALTER TABLE user_entity
    DROP COLUMN role;

ALTER TABLE user_entity
    MODIFY COLUMN password VARCHAR(255);