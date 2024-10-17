# Library Management System API

## Overview
This is a Library Management System API built with Spring Boot, It uses an H2 database and you can test these APIs by Postman.

## Endpoints

### Books
- `GET    /api/books` - Retrieve a list of all books.
- `GET    /api/books/{id}` - Retrieve details of a specific book by ID.
- `POST   /api/books/add` - Add a new book.
- `PUT    /api/books/update/{id}` - Update an existing book.
- `DELETE /api/books/delete/{id}` - Remove a book.

### Patrons
- `GET    /api/patrons` - Retrieve a list of all patrons.
- `GET    /api/patrons/{id}` - Retrieve details of a specific patron by ID.
- `POST   /api/patrons/add` - Add a new patron.
- `PUT    /api/patrons/update/{id}` - Update an existing patron.
- `DELETE /api/patrons/delete/{id}` - Remove a patron.

### Borrowing
- `POST /api/borrow/{bookId}/patron/{patronId}` - Allow a patron to borrow a book.
- `PUT  /api/borrow/return/{bookId}/patron/{patronId}` - Record the return of a borrowed book.

## Running the Application
1. Build the project with `mvn clean install`.
2. Run the application with `mvn spring-boot:run`.
3. Access the H2 console at `http://localhost:8080/h2-console`.

## Security
user: `user` with password `password`.


## Auth for Database
url: `jdbc:h2:mem:test_dp`, 
driverClassName= `org.h2.Driver`, 
path: `/h2-console`,
user: `test`,
password `pass`.
