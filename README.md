The Record Shop
===============
The Record Shop API is a backend application which is designed as an inventory management tool for a record shop. 
It is a RESTful API, following the MVC pattern which allows users to manage albums stock using CRUD operations. 

Features
=========

- Get a list of all albums in the inventory
- Retrieve an album from the inventory by its ID
- Add an album to the inventory
- Update an existing album using its ID
- Delete an album by its ID
- Retrieve albums using query parameters of: artist name, release year and genre
- Exception handling 
  
**Technologies**: 
- Java 21
- Maven
- H2 (in-memory) Database (in testing)
- PostgreSQL (in production)
- Spring Boot 

Getting Started
===============

**Prerequisites**
- Java 21
- IDE (e.g. IntelliJ IDEA, Eclipse, VS code)
- Database: PostgreSQL (if using in production)
- A tool for testing API e.g. Postman 

**Installation**
1. Fork and clone the repository:
 `git clone https://github.com/hollie11-dot/Record-Shop-Project.git`

2. Open the project in your IDE
   
3. To use PostgreSQL database in production:
- Create a `application-prod.properties` file with the following, ensuring these are kept out of version control
  
     ```
     spring.datasource.url=<POSTGRES_URL>
     spring.datasource.driverClassName=org.postgresql.Driver
     spring.datasource.username=<ENTER_YOUR_USERNAME>
     spring.datasource.password=<ENTER_YOUR_PASSWORD>
4. Set the active profile to `prod` in `application.properties` file 

     ``` spring.profiles.active=prod ```
   
5. Build the project via Maven:
   
        mvn clean install 
6. Run the program 
    - Find the `Main` class at `src/main/java/RecordShopProjectApplication` 
    - Run the program

**Testing**
  - Tests include controller tests, repository tests and service layer tests
  - Find the test folder at:
       `src/test/java`
- Endpoints can also be tested using Postman 

API Endpoits 
============
| Method | Endpoint | Description |
| ------- | ---------| ------------ |
| *GET* | `/api/v1/albums` | Get all albums | 
| *GET* | `/api/v1/albums/{albumID}` | Get album by ID | 
| *POST* | `/api/v1/albums` | Add an album | 
| *PUT* | `/api/v1/albums/{albumID}` | Update an album by ID |
| *DELETE* | `/api/v1/albums/{albumID}` | Delete an album by ID |
| *GET* | `/api/v1/albums/filter?query=value` | Filter albums by query paramters: artist name, release year or genre |

**Swagger**
- Swagger provides interactive API documentation which can be viewed at:
  -  `http://localhost:8080/swagger-ui/index.html`
