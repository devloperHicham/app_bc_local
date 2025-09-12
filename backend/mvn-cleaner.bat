cd "api-gateway-service"
call mvn clean install -DskipTests
call mvn clean package -DskipTests

cd "..\auth-service"
call mvn clean install -DskipTests
call mvn clean package -DskipTests

cd "..\config-service"
call mvn clean install -DskipTests
call mvn clean package -DskipTests

cd "..\discovery-service"
call mvn clean install -DskipTests
call mvn clean package -DskipTests

cd "..\comparison-service"
call mvn clean install -DskipTests
call mvn clean package -DskipTests

cd "..\client-service"
call mvn clean install -DskipTests
call mvn clean package -DskipTests

cd "..\setting-service"
call mvn clean install -DskipTests
call mvn clean package -DskipTests

cd "..\schedule-service"
call mvn clean install -DskipTests
call mvn clean package -DskipTests

cd "..\user-service"
call mvn clean install -DskipTests
call mvn clean package -DskipTests
