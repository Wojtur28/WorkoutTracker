CREATE TABLE IF NOT EXISTS user_role (
    user_id UUID NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
