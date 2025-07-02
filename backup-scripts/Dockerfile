# syntax=docker/dockerfile:1.4

FROM postgres:16

# Install cron and cleanup apt cache (resolves SonarQube warning S6587)
RUN apt update && \
    apt install -y cron && \
    apt clean && \
    rm -rf /var/lib/apt/lists/*

# Create necessary folders and log file
RUN mkdir -p /scripts /backups /var/log && touch /var/log/cron.log

# Copy the backup script
COPY backup.sh /scripts/backup.sh
RUN chmod +x /scripts/backup.sh

# Set up cron jobs: 2:00 PM and 10:30 PM
CMD ["bash", "-c", "\
echo '0 14 * * * /scripts/backup.sh >> /var/log/cron.log 2>&1' >> /etc/crontab && \
echo '30 22 * * * /scripts/backup.sh >> /var/log/cron.log 2>&1' >> /etc/crontab && \
cron && tail -f /var/log/cron.log"]
