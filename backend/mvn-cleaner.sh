#!/bin/bash
set -e  # stop if any command fails

# List of services
services=(
  "api-gateway-service"
  "auth-service"
  "config-service"
  "discovery-service"
  "comparison-service"
  "client-service"
  "schedule-service"
  "setting-service"
  "user-service"
)

# Java release version to use
JAVA_RELEASE=17

# Save current path
basePath=$(pwd)

for service in "${services[@]}"; do
  echo "��� Building $service ..."
  cd "$basePath/$service"

  # Use Maven with forced compiler release
  mvn clean install -DskipTests -Dmaven.compiler.release=$JAVA_RELEASE
  mvn clean package -DskipTests -Dmaven.compiler.release=$JAVA_RELEASE
done

# Return to original directory
cd "$basePath"
echo "✅ All services built successfully!"

