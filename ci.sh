#!/bin/bash

export JAVA_HOME=`/usr/libexec/java_home -v 11.0.11`

./gradlew clean bootJar

docker build -t avvero/jam-wui:latest -t avvero/jam-wui:0.2 .
docker push avvero/jam-wui:latest
docker push avvero/jam-wui:0.2