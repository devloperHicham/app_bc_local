-- Check if database exists
DO
$$
BEGIN
   -- Check if the database exists
   IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'schedule_db') THEN
      -- Create database if it doesn't exist
      EXECUTE 'CREATE DATABASE schedule_db';
   END IF;
END
$$;

-- Check if user exists and create if necessary
DO
$$
BEGIN
   -- Check if the user exists
   IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'schedule_db') THEN
      -- Create user if it doesn't exist
      EXECUTE 'CREATE USER schedule_db WITH ENCRYPTED PASSWORD ''schedule_password12345@@''';
   END IF;
END
$$;

-- Grant privileges to the user on the database
GRANT ALL PRIVILEGES ON DATABASE schedule_db TO schedule_db;
