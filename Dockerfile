FROM alpine:latest
EXPOSE 8082
RUN apk update
RUN apk upgrade --update-cache --available
RUN apk add openjdk11 && apk add --no-cache librdkafka
VOLUME /tmp
ADD target/messageStore-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

