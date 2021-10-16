# Polygon REST microservice
A REST microservice written in JAVA using Maven project management and
[KumuluzEE](https://github.com/kumuluz/kumuluzee) microservices framework.

The service saves and returns polygons with their coordinates.

## Requirements

  * Java v8+
  * Maven v3.2.1+
  * PostgreSQL
  * Git

## Usage
* Build with Maven:
```
cd polygon-rest-microservice
mvn clean package
```
* Run with Uber-jar:
```
cd polygon-rest-microservice
java -jar target/polygon-1.0-SNAPSHOT.jar
```
* Create PostgreSQL database with:
  - name: `polygons`
  - username: `postgres`
  - password: `postgres`
  - *If using different name, username and password, edit `config.yaml` accordingly.*

* The service should be availible on [http://localhost:8080](http://localhost:8080)

* See OpenAPI specification on [http://localhost:8080/api-specs/v1/openapi.json](http://localhost:8080/api-specs/v1/openapi.json)
  - or UI specs on [http://localhost:8080/api-specs/ui](http://localhost:8080/api-specs/ui)

* Health checks on [http://localhost:8080/health](http://localhost:8080/health)
