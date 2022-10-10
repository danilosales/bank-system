# Bank System

The architecture for this project using the following technologies:

* Spring Boot - for build java application.
* Postgres - Data base for persistence.
* FlyWay - Database migration
* ModelMapper - Convert Entities to DTOs
* Lombok - Reduce boilerplate
* OpenAPI -  Used to describe methods for developers.
* Testcontainer - Used by integration tests.

## Instructions to run this project


The preferred way to run the project is through Docker running the following command:

```sh
docker-compose up
```

To run in background add -d parameter:

```sh
docker-compose up -d
```

If necessary rebuild a container, execute this command:

```sh
docker-compose up --build
```

If you wish to run the project outside the containers, run the follow command to start only a db container:

```sh
docker-compose up db
```

And then execute the BankApplication.java

Available Interfaces:

* [API documentation](http://localhost:8080/swagger-ui)

* [Base URL](http://localhost:8080/)






