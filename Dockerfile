FROM maven:3-jdk-11 as builder
RUN apt-get update
RUN apt-get -qq -y install curl
WORKDIR /source
COPY src ./src/
COPY pom.xml ./
COPY lombok.config ./
RUN mvn -f ./pom.xml package

FROM openjdk:11-jre-slim
WORKDIR /service
COPY --from=builder target/*.jar service.jar
EXPOSE 8080
ENTRYPOINT exec java -server \
-noverify \
-XX:TieredStopAtLevel=1 \
-Dspring.jmx.enabled=false \
-Djava.security.egd=file:/dev/./urandom \
$JAVA_OPTS \
-jar service.jar