FROM openjdk:14-alpine

LABEL version="Here insert VERSION"
LABEL description="Tool for trade analysis in web app."
MAINTAINER "Adam Balski"
LABEL maintainer="Adam Balski"

# *.jar should work, because there should be only one such file

EXPOSE 8080/tcp
WORKDIR /
ADD target/*.jar /

ENTRYPOINT jar -jar *.jar