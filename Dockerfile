FROM maven:3.8.1-openjdk-11 AS build
COPY ./ /app
WORKDIR /app
RUN mvn --show-version --update-snapshots --batch-mode clean package

FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /app
WORKDIR /app
COPY --from=build ./app/api/target/polygon-rest-microservice-api-1.0.0-SNAPSHOT.jar /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "polygon-rest-microservice-api-1.0.0-SNAPSHOT.jar"]
