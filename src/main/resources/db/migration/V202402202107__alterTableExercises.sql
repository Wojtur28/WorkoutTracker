ALTER TABLE exercises
    ADD CONSTRAINT exercises_training_fkey
        FOREIGN KEY (training_id)
            REFERENCES trainings (id);
