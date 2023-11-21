CREATE TABLE IF NOT EXISTS training_category (
    training_id UUID NOT NULL,
    category_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (training_id) REFERENCES trainings (id)
);
