# ARG GITHUB_ACTOR
# ARG GITHUB_TOKEN
# FROM gradle:jdk11-jammy AS builder

# ENV GITHUB_ACTOR=$GITHUB_ACTOR
# ENV GITHUB_TOKEN=$GITHUB_TOKEN

# COPY ./ ./

# RUN gradle clean build --no-daemon
# RUN ls -lash && false

FROM openjdk:11-jre-slim-buster

RUN apt update && apt install -y jq && apt clean

COPY docker/entrypoint.sh /entrypoint.sh
COPY docker/FixInitiator.cfg /FixInitiator.cfg
COPY docker/application-deploy.properties /application-deploy.properties
COPY build/libs/FixInitiator.jar /app.jar

ENTRYPOINT [ "/bin/bash", "/entrypoint.sh" ]