ALTER TABLE user_role
    DROP CONSTRAINT IF EXISTS user_role_user_id_fkey;

ALTER TABLE user_role
    ADD CONSTRAINT user_role_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
