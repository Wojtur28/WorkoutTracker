CREATE TABLE IF NOT EXISTS user_measurements (
    id UUID PRIMARY KEY,
    weight DOUBLE PRECISION NOT NULL,
    height DOUBLE PRECISION NOT NULL,
    age DOUBLE PRECISION NOT NULL,
    user_id UUID NOT NULL,
    createdBy UUID REFERENCES users(id),
    modifiedBy UUID REFERENCES users(id),
    createdOn TIMESTAMP,
    modifiedOn TIMESTAMP
);
