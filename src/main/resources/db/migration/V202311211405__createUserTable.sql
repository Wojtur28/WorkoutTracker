CREATE TABLE IF NOT EXISTS users  (
    id UUID PRIMARY KEY,
    username VARCHAR(32) NOT NULL,
    email VARCHAR(32) NOT NULL,
    first_name VARCHAR(32) NOT NULL,
    last_name VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL,
    createdBy UUID REFERENCES users(id),
    modifiedBy UUID REFERENCES users(id),
    createdOn TIMESTAMP,
    modifiedOn TIMESTAMP
);
