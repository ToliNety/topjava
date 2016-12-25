DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description, calories, user_id)
VALUES ('завтрак', 500, 100000);

INSERT INTO meals (description, calories, user_id)
VALUES ('обед', 700, 100000);

INSERT INTO meals (description, calories, user_id)
VALUES ('ужин', 800, 100000);

INSERT INTO meals (description, calories, user_id)
VALUES ('завтрак', 500, 100001);

INSERT INTO meals (description, calories, user_id)
VALUES ('обед', 700, 100001);

INSERT INTO meals (description, calories, user_id)
VALUES ('ужин', 810, 100001);