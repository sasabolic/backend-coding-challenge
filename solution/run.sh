#!/usr/bin/env bash
echo "***********************************"
echo "Building the app..."
echo "***********************************"

./gradlew clean build

echo "***********************************"
echo "Starting postgres and the app..."
echo "***********************************"

docker-compose up
