CREATE TABLE IF NOT EXISTS exercises (
    id UUID PRIMARY KEY,
    training_id UUID NOT NULL,
    name VARCHAR(255),
    description TEXT,
    createdBy UUID REFERENCES users(id),
    modifiedBy UUID REFERENCES users(id),
    createdOn TIMESTAMP,
    modifiedOn TIMESTAMP
);
