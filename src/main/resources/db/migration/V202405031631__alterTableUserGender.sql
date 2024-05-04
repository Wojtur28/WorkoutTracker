ALTER TABLE user_gender
    DROP CONSTRAINT IF EXISTS fkb7jut30pg2gc6jf5p3u2xwkl1;

ALTER TABLE user_gender
    DROP CONSTRAINT IF EXISTS user_gender_user_id_fkey;

ALTER TABLE user_gender
    ADD CONSTRAINT user_gender_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
