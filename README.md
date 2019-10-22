# spring-cloud-simple-authorization-service

This project has the objective to present a sample for a simple authorization server using OAuth flows to authenticate and an user for an application. 
In this sample I'll not get into OAuth 2.0 details. It is easy to find articles to explain OAuth 2.0 flows (here is a suggestion: 
https://connect2id.com/learn/oauth-2).

The main idea is to use a grant_type password to get access to a JWT token as an access token. This token gonna be used as a bearer token into requests 
to grant access to some protected resources. For this demonstration the authorization server gonna be a simple Spring Cloud app and for represent a 
client requests gonna be done by Postman to represent the access token request and an authorized request using a bearer token.

It is important to reinforce that authorization and authentication are different responsibilities. Into this sample the component do both responsibilities. 
When we are requesting an access token we are doing the authentication responsibility. Our user plays at the same time the role as application user 
and as resource client. After getting the access token, he plays the role as resource client and uses its access token (as an authenticated user) 
to access the user resource.


## Approach

The objective of this sample is present baby steps with details of configurations and Spring Cloud resources used to achieve the final goal of
 providing an authorization server. The example is far of representing a scenario of production environment, but will make clear why some resources 
 are being used. It might be a starting point.

The OAuth 2.0 flow used for this example is represented above:

<p align="center">
  <img src="https://raw.githubusercontent.com/tnfigueiredo/spring-cloud-account-service/master/diagram.png" title="App OAuth password grant_type flow">
</p>

## The application

The application is composed a set of classes to represent a simple authorization and resource server, like a sample for an account service that has 
the responsibility of authorize users and deal with users information. The project structure is organized as explained above:

 - A main class that starts the Spring Cloud app;
 - A web security configuration class;
 - An authorization server configuration class;
 - A resource server configuration class;
 - A restful controller, user service and user bean to represent the resource to be managed.


### Web security configuration

This configuration class [SecurityConfigu](https://raw.githubusercontent.com/tnfigueiredo/spring-cloud-account-service/master/src/main/java/com/sample/tnfigueiredo/config/SecurityConfigu.java) is responsible to enable the web security, inject the UserDetailsService and override the AuthenticationManager. The user detail service is the service which gonna recover the user information for authentication. The overrided AuthenticationManager gonna be used at the AuthorizationServer for some configurations.


### Authorization server configuration

This configuration class [AuthServerConfig](https://raw.githubusercontent.com/tnfigueiredo/spring-cloud-account-service/master/src/main/java/com/sample/tnfigueiredo/config/AuthServerConfig.java) is responsible to enable the Authorization Server, create the JWT Token configuration, create client credentials configuration. The AuthServerConfig creates a JwtAccessTokenConverter and creates a JwtTokenStore using this converter to deal with the JWT Tokens. Without it, the access token returned is a simple access token. This components are used to override the AuthorizationServerEndpointsConfigurer, using the token store, the access token converter and the authentication manager created until now at the application.


### Authorization server configuration

This configuration class [ResourceServerConfig](https://raw.githubusercontent.com/tnfigueiredo/spring-cloud-account-service/master/src/main/java/com/sample/tnfigueiredo/config/ResourceServerConfig.java) is responsible to enable the resource server configuration, the security for the resource server, enable resource servers to be stateless and to be accessed only with access token.

## Usage

For this example, we gonna avoid get into client coding details. The focus is about the request for the access token and the resource server request 
with a valid access token.

### Access Token:

To get an access token, here is an example with CURL and with Postman:

CURL:
```CURL
    curl -X POST \
  	http://localhost:9000/simple-auth-service/oauth/token \
  	-H 'Content-Type: application/x-www-form-urlencoded' \
  	-H 'Postman-Token: 74d4f8d4-3ac4-4a77-9e1c-8a64b70b86a2' \
  	-H 'cache-control: no-cache' \
  	-d 'grant_type=password&username=myuser&password=password'
```

Postman:

<p align="center">
  <img src="https://raw.githubusercontent.com/tnfigueiredo/spring-cloud-account-service/master/Postman_access_token_header.png" title="Postman - Basic Auth">
</p>


<p align="center">
  <img src="https://raw.githubusercontent.com/tnfigueiredo/spring-cloud-account-service/master/Postman_access_token_body.png" title="Postman - Body">
</p>

### Resource Server Request

Using an endpoint to access a resource server information:

- With no access token:

CURL:
```CURL
curl -X GET \
  http://localhost:9000/simple-auth-service/users \
  -H 'cache-control: no-cache'
```

<p align="center">
  <img src="https://raw.githubusercontent.com/tnfigueiredo/spring-cloud-account-service/master/Postman_resourse_request_fail.png" title="Postman - No access token">
</p>

- With access token:

CURL:
```CURL
curl -X GET \
  http://localhost:9000/simple-auth-service/users \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDMzODQyNzksInVzZXJfbmFtZSI6Im15dXNlciIsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiZmI2YzY2NmYtMDUxMy00YzE5LWI2YjktOTdiYTE3OGEyZTc0IiwiY2xpZW50X2lkIjoibXl1c2VyYXBwIiwic2NvcGUiOlsiQURNSU4iLCJVU0VSIl19.bSP-Us6Yx60A-MXSnfdNlZySpiLQwd0FM5wHDAMWjN0"
  -H 'cache-control: no-cache'
```

<p align="center">
  <img src="https://raw.githubusercontent.com/tnfigueiredo/spring-cloud-account-service/master/Postman_resourse_request_success.png" title="Postman - Valid access token">
</p>


## Content sources

 - https://www.devglan.com/spring-security/spring-boot-oauth2-jwt-example
 - https://connect2id.com/learn/oauth-2
 - https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-encoding
 - https://dzone.com/articles/whats-better-oauth-access-tokens-or-json-web-token
 - https://projects.spring.io/spring-security-oauth/docs/oauth2.html
 - https://auth0.com/docs/api-auth/tutorials/password-grant
