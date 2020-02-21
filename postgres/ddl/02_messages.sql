DROP SCHEMA IF EXISTS messages CASCADE;

CREATE SCHEMA messages;
COMMENT ON SCHEMA messages IS 'Схема для хранения сообщений для рассылки';

DROP TABLE IF EXISTS messages.current_post;
CREATE TABLE messages.current_post (
    number_in_post int NOT NULL,
    type character varying (50) NOT NULL,
    content character varying(1500),
    file_id character varying(100),
    admin_login character varying NOT NULL,
--     media_group_id character varying,
    PRIMARY KEY (number_in_post, admin_login),
    FOREIGN KEY (admin_login) REFERENCES users.users (login)
);

COMMENT ON TABLE messages.current_post IS 'Первое и последнее сообщение для текущего админа - служебные, и служат для проверки, что пост начат и закочнен соответственно. Если в базе присутствует сообщение с типом start_post, значит все последующие сообщения от админа сохраняются в базу до тех пор, пока не будет получено сообщение end_post или cancel_post';
-- COMMENT ON column messages.current_post.media_group_id IS 'Айдишник для файлов (изображений), которые приходят вместе. Телеграм не хранит это значение, так что это только способ узнать, какие файлы пришли в одной группе';

CREATE OR REPLACE FUNCTION messages.start_post_trigger() RETURNS TRIGGER AS $$
    BEGIN
        IF exists(
                SELECT * FROM messages.current_post
                WHERE
                      admin_login = NEW.admin_login
                    AND
                      NEW.type = 'START_POST'

            ) THEN RAISE EXCEPTION USING MESSAGE = 'Admin have started post already';
        END IF;
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER start_post_trigger BEFORE INSERT OR UPDATE
    ON messages.current_post
    FOR EACH ROW
EXECUTE PROCEDURE messages.start_post_trigger();

COMMENT ON FUNCTION messages.start_post_trigger() IS 'Если пост уже начат (содержит строку со типом START_POST), при попытке вставить еще одно начало поста будет сгенерирован эксепшн';