#!/bin/bash

TIMESTAMP=$(date +"%Y-%m-%d_%H-%M-%S")

# Backup each database
pg_dump -h postgres-user -U user_db user_db > /backups/user_db_$TIMESTAMP.sql
pg_dump -h postgres-setting -U setting_db setting_db > /backups/setting_db_$TIMESTAMP.sql
pg_dump -h postgres-comparison -U comparison_db comparison_db > /backups/comparison_db_$TIMESTAMP.sql
pg_dump -h postgres-schedule -U schedule_db schedule_db > /backups/schedule_db_$TIMESTAMP.sql
