# spring-cloud-simple-authorization-service

This project has the objective to present a sample for a simple authorization server using OAuth flows to authenticate and an user for an application. In this sample I'll not get into OAuth 2.0 details. It is easy to find articles to explain OAuth 2.0 flows (here is a suggestion: https://connect2id.com/learn/oauth-2).

The main idea is to use a grant_type password to get access to a JWT token as an access token. This token gonna be use as a bearer token into requests to grant access to some protected resources. For this demonstration the authorization server gonna be a simple Spring Cloud app and for represent a client requests gonna be done by Postman to represent the access token request and an authorized request using a bearer token.

## Approach

The objective of this sample is present baby steps with details of configurations and Spring Cloud resources used to achieve the final goal of providing an authorization server. The example is far of represent a scenario of production environment, but will make clear why some resources are being used. It might be a starting point.

The OAuth 2.0 flow used for this example is represented above:

<p align="center">
  <img src="https://raw.githubusercontent.com/tnfigueiredo/spring-cloud-account-service/master/diagram.png" title="App OAuth password grant_type flow">
</p>

## Usage

For this example, we gonna avoid get into client coding details. The focus is about the request for the access token and the resource server request with a valid access token.

### Access Token:

To get an access token, here is an example with CURL and with Postman:

CURL:
```CURL
    curl -X POST \
    http://localhost:9000/simple-auth-service/oauth/token \
    -H "Authorization: Basic myuserapp:myusersecret"
    -H 'Content-Type: application/x-www-form-urlencoded' \
    -H 'Postman-Token: 8f87dee2-71a1-4cba-9ea8-29f8597aab5c' \
    -H 'cache-control: no-cache' \
    -d 'username=myuser&password=password&grant_type=password&client_id=myuserapp'
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
  -H 'Postman-Token: b7835041-de26-4330-814c-da62846e36dd' \
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
  -H 'Postman-Token: b7835041-de26-4330-814c-da62846e36dd' \
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
