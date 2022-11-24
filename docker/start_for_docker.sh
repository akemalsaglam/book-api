#!/bin/bash

# Application Name
APP_NAME="book-api-0.0.1-SNAPSHOT"

# Java Options
JAVA_OPTS="-Xms128m -Xmx512m -XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -XX:MaxGCPauseMillis=200 -XX:+UseStringDeduplication -XX:ParallelGCThreads=4 -XX:InitiatingHeapOccupancyPercent=70 -noverify -XX:TieredStopAtLevel=1"

# Spring Options
SPRING_OPTS="-Dspring.profiles.active=test -Denvironment=test"

echo "App Name: ${APP_NAME}"
echo "Java Options: ${JAVA_OPTS}"
echo "Spring Options: ${SPRING_OPTS}"
echo ${APP_NAME}.jar "${SPRING_OPTS}" "${JAVA_OPTS}" "-web -webAllowOthers -tcp -tcpAllowOthers -browser"

# Start the application

exec java -jar ${APP_NAME}.jar "${SPRING_OPTS}" "${JAVA_OPTS}" "-web -webAllowOthers -tcp -tcpAllowOthers -browser"  --nodaemon
