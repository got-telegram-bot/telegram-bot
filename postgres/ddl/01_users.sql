DROP SCHEMA IF EXISTS users CASCADE;

CREATE SCHEMA users;
COMMENT ON SCHEMA users IS 'Схема для хранения сущностей пользователей';

DROP TABLE IF EXISTS users.users;
CREATE TABLE users.users (
    login character varying(100) NOT NULL PRIMARY KEY,
    initials character varying NOT NULL,
    chat_id bigint UNIQUE,
    password character varying NOT NULL,
    house character varying UNIQUE,
    is_admin boolean NOT NULL,
    is_ready boolean NOT NULL,
    last_order_message character varying
);


INSERT INTO users.users(
    login, initials, chat_id, password, house, is_admin, is_ready)
VALUES ('got_admin', 'Бринден Риверс', '381855899', 'tob.margelet.tog', NULL, true,  false);

INSERT INTO users.users(
    login, initials, chat_id, password, house, is_admin, is_ready)
VALUES ('rainbow', 'Артем', '416724770', '111', NULL, false, false);

INSERT INTO users.users(
    login, initials, chat_id, password, house, is_admin, is_ready)
VALUES ('Drenal', 'Астахов А.с.', '427924506', '123', NULL, false, false);

INSERT INTO users.users(
    login, initials, chat_id, password, house, is_admin, is_ready)
VALUES ('xodov', 'ходов', '644472233', 'ходов', NULL, false, false);