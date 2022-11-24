#!/bin/bash

set -e

mvn clean install -DskipTests

docker build -t booking-api .

docker run -p 0.0.0.0:8080:8080 booking-api -d
