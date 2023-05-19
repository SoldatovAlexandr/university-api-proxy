FROM openjdk:11

ARG JAR_FILE=target/*SNAPSHOT.jar

WORKDIR /opt/app

COPY ${JAR_FILE} university-api-proxy.jar

ENTRYPOINT ["java","-jar","university-api-proxy.jar"]
