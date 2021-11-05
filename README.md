# Plygon rest microservice

## Requirements

- Java 11
- Maven 3.6.2+

## Prerequisites

### Configure PostgreSQL database:

- database host: localhost:5432
- database name: polygons
- user: postgres
- password: postgres

It can be run inside a docker container:

```
docker run -d --name pg-jpa -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=polygons -p 5432:5432 postgres:latest
```

### Configure Keycloak:

- Create a keycloak instance:

```
docker run -e KEYCLOAK_USER=<USERNAME> -e KEYCLOAK_PASSWORD=<PASSWORD> -d -p 8082:8080 jboss/keycloak
```

- Log into the Keycloak (open browser http://localhost:8082) using your admin account and create a new realm named: polygon-realm.
- Create two new clients. One will be used to retrieve access tokens and the other one will be used only to verify issued tokens.
  Create the first client with Client ID polygon-app. Leave the Protocol option set to openid-connect.
  After creating the client, verify, that the Access Type is set to public.
  Under Root URL and Web origins enter http://localhost:8080,
  under Valid Redirect URIs enter http://localhost:8080/\*. Port 8080 points to your sample application port.
- Create the second client with Client ID polygon-api. Change the Access Type to bearer-only.
- Create a user. Make sure to set User Enabled to ON. After adding the user, make sure that Required User Actions is empty.
  Go to Credentials and set the new password, and set Temporary to OFF before confirming.
- Create a roles admin and user.
- Open new user and go to Role Mappings and assign the Role user to new user.

## Usage

1. Build with maven:

```
cd polygon-rest-microservice
mvn clean package
```

2. Run with Uber-jar:

```
java -jar ./api/target/polygon-rest-microservice-api-1.0.0-SNAPSHOT.jar
```

# Run the app inside a docker container

1. Build the image

```
docker build -t polygons .
```

2. Run the image

```
docker run -d --network=host --rm -it polygons:latest
```

# Additional info

- Api will be availible on [http://localhost:8080](http://localhost:8080)

- For more info see [http://localhost:8080/api-specs/v1/openapi.json](http://localhost:8080/api-specs/v1/openapi.json)

  or [http://localhost:8080/api-specs/ui](http://localhost:8080/api-specs/ui)

- For healthchecks see [http://localhost:8080/health](http://localhost:8080/health)

- For version info see [http://localhost:8080/version](http://localhost:8080/version)
