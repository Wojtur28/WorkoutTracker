CREATE TABLE IF NOT EXISTS user_gender
(
    user_id     UUID         NOT NULL
        REFERENCES users (id)
            ON DELETE CASCADE,
    user_gender VARCHAR(255) NOT NULL
        CONSTRAINT user_gender_user_gender_check
            CHECK ((user_gender)::TEXT = ANY
                   ((ARRAY['MALE'::character varying, 'FEMALE'::character varying, 'OTHER'::character varying])::TEXT[])),
    PRIMARY KEY (user_id, user_gender)
);


ALTER TABLE user_gender
    DROP CONSTRAINT IF EXISTS fkb7jut30pg2gc6jf5p3u2xwkl1;

ALTER TABLE user_gender
    DROP CONSTRAINT IF EXISTS user_gender_user_id_fkey;

ALTER TABLE user_gender
    ADD CONSTRAINT user_gender_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
