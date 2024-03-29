FROM openjdk:14-alpine

LABEL version="0"
LABEL description="Tool for trade analysis in web app."
MAINTAINER "Adam Balski"
LABEL maintainer="Adam Balski"

# *.jar should work, because there should be only one such file

EXPOSE 8080/tcp
WORKDIR /
ADD target/*.jar /
ADD env.properties /

ENTRYPOINT /bin/sh -c 'export $(cat /env.properties | xargs); export E_MAIL_PASSWORD=kpvm\ kqym\ mhvo\ ldfh ; java -jar /*.jar'
