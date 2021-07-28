#!/bin/bash

./gradlew clean bootJar

docker build -t avvero/jam-wui:latest -t avvero/jam-wui:0.1 .
docker push avvero/jam-wui:latest
docker push avvero/jam-wui:0.1