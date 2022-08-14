#!/bin/sh
echo "****************"
echo "producer: gradle build..."
echo "****************"
./gradlew producer:clean producer:build -xtest
echo "****************"
echo "producer: docker build..."
echo "****************"
docker build -t jordanec/store-producer:latest -f producer/Dockerfile ./producer/
echo "****************"
echo "producer: build done!"
echo "****************"
echo "****************"
echo "consumer: gradle build..."
echo "****************"
./gradlew consumer:clean consumer:build -xtest
echo "****************"
echo "consumer: docker build..."
echo "****************"
docker build -t jordanec/store-consumer:latest -f consumer/Dockerfile ./consumer/
echo "****************"
echo "consumer: build done!"
echo "****************"
echo "****************"
echo "store-web (dev): npm build"
#cd store-web/ && npm run build && cd ..
echo "****************"
echo "store-web (dev): docker build..."
echo "****************"
docker build -t jordanec/store-web-dev:latest -f store-web/Dockerfile-dev ./store-web/
echo "****************"
echo "store-web (dev): build done!"
echo "****************"
echo "****************"
echo "store-web (prod): docker build..."
echo "****************"
docker build -t jordanec/store-web:latest -f store-web/Dockerfile ./store-web/
echo "****************"
echo "store-web (prod): build done!"
echo "****************"