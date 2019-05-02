# springboot-rest-example

In this sample project we will demonstrate how to:
1. Create a REST Api using Spring Boot
2. Create a unit test to automatically test rest controller
3. Securing the REST Api. A simple `Bearer jwt_token` in `Authorization` http header will be used in order to access secured routes of this api.
4. Create sample methods in api to demonstrate: CRUD operations, entity to DTO conversion, pagination, ETag for REST, spring HATEOAS
5. Include a H2 in memory database for persistence and usage of `spring-boot-starter-data-jpa`  
6. Documenting the REST Api using Swagger

## How to run the application

In order to start the application from command line at root of project run `mvn clean spring-boot:run`. 
This will start the application on port `8080` and will give you access to the following:

1. [REST Api docs with Swagger](http://localhost:8080/spring-boot-rest-example/swagger-ui.html)
2. [H2 Console](http://localhost:8080/spring-boot-rest-example/h2c)

To automatically test all the routes defined in this REST Api from command line run `mvn test`.

## Application flow

In this sample application, the flow can be designated as the following steps:

1. Get the JWT based token from the authentication endpoint, eg `/api/auth/login`.
2. Extract token from the authentication result.
3. Set the HTTP header `Authorization` value as `Bearer jwt_token`.
4. Populate the foo table with dummy value sending a post to endpoint `/api/foos/init`
5. In the same way send other requests to access the protected routes or the public ones. 
6. If the requested resource is protected, Spring Security will use this custom `Filter` to validate the JWT token, and build an `Authentication` object and set it in Spring Security specific `SecurityContextHolder` to complete the authentication progress.
7. If the JWT token is valid it will return the requested response to client.

You can go through these steps using Swagger documentation or using your preferred method.

## Sources 

Check out the [source codes from my github](https://github.com/sorinmare/springboot-rest-example)