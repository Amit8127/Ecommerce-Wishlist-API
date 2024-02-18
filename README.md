# Ecommerce Solutions Authentication and Wishlist API with JUnit Testing

This repository contains the source code for the Authentication and Wishlist APIs, including JUnit tests for the controller, service, and repository layers. The Authentication API provides endpoints for user registration (signup) and user login, while the Wishlist API provides endpoints for managing wishlists.
## Table of Contents
* Dependencies
* Installation
* Database Setup
* Authentication API
  * User Signup
  * User Login
  * JUnit Tests
* Wishlist API
  * Create Wishlist
  * Get All Wishlists
  * Delete Wishlist by ID
  * JUnit Tests

## Dependencies
The Authentication and Wishlist APIs are built using the following dependencies:
  * Spring Boot
  * Spring Web
  * Spring Data JPA
  * Spring Security
  * MySQL Driver
  * Lombok
  * Other dependencies as specified in the project's `pom.xml` file.

## Installation

To run the Authentication and Wishlist APIs locally, follow these steps:

1. Clone the repository: `git clone https://github.com/Amit8127/Ecommerce-Wishlist-API.git`
2. Navigate to the project directory: `cd Ecommerce-Wishlist-API`
3. Configure the database settings in `application.properties` file.
4. Build the project using Maven: `mvn clean install`
5. Run the application: `mvn spring-boot:run`
6. The application will be accessible at `http://localhost:8080`.

## Database Setup
This project uses **MySQL** as the database. Follow these steps to set up the database:
1. Install MySQL on your local machine.
2. Create a new database named ecommerceDb.
3. Update the database configuration in `application.properties` file.

## Authentication APIs
### User Signup
* Endpoint: `POST /auth/signup`
* Request Body:
```json
{
  "name": "user",
  "email": "user@example.com",
  "password": "password123"
}
```
#### Response:
* Success (HTTP 201 Created):
```json
{
  "id": 1,
  "email": "user@example.com"
}
```
* Failure (HTTP 400 Bad Request):
```text
User already Present with email : user@example.com
```

### User Login
* Endpoint: `POST /auth/login`
* Request Body:
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```
#### Response:
* Success (HTTP 200 OK):
```json
{
  "jwtToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
  "username": "user@example.com"
}
```
* Failure (HTTP 401 UNAUTHORIZED):
```text
Invalid Username or Password  !!
```

## JUnit Tests
* Controller Layer: `AuthControllerTest.java`
* Service Layer: `AuthUserDetailServiceTest.java`
* Repository Layer: `AuthRepositoryTest.java`

## Wishlist APIs
### Create Wishlist
* Endpoint: `POST /api/wishlists`
* Request Body:
```json
{
  "title": "New Wishlist"
}
```
#### Response:
* Success (HTTP 201 Created):
```json
{
  "id": 1,
  "userId": 1,
  "title": "New Wishlist"
}
```
* Failure (HTTP 400 Bad Request):
```text
Wishlist Already Exist With This Title : New Wishlist
```

### Get All Wishlists
* Endpoint: `GET/api/wishlists`
#### Response:
* Success (HTTP 200 OK):
```json
[
     {
       "id": 1,
       "userId": 1,
       "title": "New Wishlist"
     },
     {
       "id": 2,
       "userId": 1,
       "title": "New Wishlist2"
     }
]
```
* Failure (HTTP 400 Bad Request):
```text
Failed to retrieve wishlists
```
### Delete Wishlist by ID
* Endpoint: `GET/api/wishlists/{id}`
#### Response:
* Success (HTTP 200 OK):
```text
Wishlist Deleted Successfully
```
* Failure (HTTP 400 Bad Request):
```text
Wishlist is not Exist with Id : 1
```

## JUnit Tests
* Controller Layer: `WishlistControllerTest.java`
* Service Layer: `WishlistServiceImplTest.java`
* Repository Layer: `WishlistRepositoryTest.java`




