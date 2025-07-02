# schedulerates
Plateforme : de Gestion des Horaires et Tarifs Maritimes pour les Ports
# this part for create database manual step by step
✅ 1. Enter the PostgreSQL container

In Git Bash, use winpty:

winpty docker exec -it postgres-user bash

✅ 2. Access the PostgreSQL shell

Now you're inside the container. Use this command to access the psql CLI:

psql -U postgres

    If you get "psql: command not found", install it inside the container:

apt update
apt install -y postgresql

Then try psql -U postgres again.
✅ 3. Manually create the database and user

In the psql shell, run:

CREATE DATABASE user_db;
CREATE USER user_db WITH PASSWORD 'user_pass';
GRANT ALL PRIVILEGES ON DATABASE user_db TO user_db;

To confirm the database exists:

\l

✅ 4. Connect to the new database

\c user_db

Now you're inside the user_db database.
✅ 5. Create your tables manually

Example: Create a simple users table:

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

✅ 6. Check the table

\d

This lists all tables.

To view table structure:

\d users

To view inserted data:

SELECT * FROM users;

✅ 7. Exit

To exit the psql shell:

\q

Then to exit the container shell:

exit

# finish create database


