FROM openjdk:8-jre-alpine

ENV APP_JAR="catface-1.0.0.jar"

RUN apk add --update bash openntpd && \
    rm -rf /var/cache/apk/*

EXPOSE 8080

WORKDIR /app
COPY ./build/libs/${APP_JAR} /app

# sh is needed to resolve environment variables
CMD ["sh", "-c", "java -jar -Dspring.profiles.active=docker -Dserver.port=${PORT} /app/${APP_JAR}"]

