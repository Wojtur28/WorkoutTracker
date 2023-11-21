CREATE TABLE IF NOT EXISTS trainings (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    createdBy UUID REFERENCES users(id),
    modifiedBy UUID REFERENCES users(id),
    createdOn TIMESTAMP,
    modifiedOn TIMESTAMP
);
