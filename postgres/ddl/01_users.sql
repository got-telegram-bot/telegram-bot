DROP SCHEMA IF EXISTS users CASCADE;

CREATE SCHEMA users;
COMMENT ON SCHEMA users IS 'Схема для хранения сущностей пользователей';

DROP TABLE IF EXISTS users.users;
CREATE TABLE users.users (
    login character varying(100) NOT NULL PRIMARY KEY,
    initials character varying NOT NULL,
    chat_id bigint,
    password character varying NOT NULL,
    house character varying,
    is_admin boolean,
    role_name character(100)
);


INSERT INTO users.users(
    login, initials, chat_id, password, house, is_admin, role_name)
VALUES ('got_admin', 'Бринден Риверс', '416724770', 'tob.margelet.tog', NULL, true, 'ROLE_ADMIN');

INSERT INTO users.users(
    login, initials, chat_id, password, house, is_admin, role_name)
VALUES ('got_admin1', 'Сива', '381855899', '111', NULL, true, 'ROLE_ADMIN');
