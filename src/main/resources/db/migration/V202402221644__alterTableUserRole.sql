ALTER TABLE user_role ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();
