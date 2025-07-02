-- Check if database exists
DO
$$
BEGIN
   -- Check if the database exists
   IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'comparison_db') THEN
      -- Create database if it doesn't exist
      EXECUTE 'CREATE DATABASE comparison_db';
   END IF;
END
$$;

-- Check if user exists and create if necessary
DO
$$
BEGIN
   -- Check if the user exists
   IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'comparison_db') THEN
      -- Create user if it doesn't exist
      EXECUTE 'CREATE USER comparison_db WITH ENCRYPTED PASSWORD ''comparison_password12345@@''';
   END IF;
END
$$;

-- Grant privileges to the user on the database
GRANT ALL PRIVILEGES ON DATABASE comparison_db TO comparison_db;
