# Taller 7 | AREP

## Twitter-Like API From Monolith to Microservices

In this lab, we developed a Twitter-like API that allows users to create and post short messages (up to 140 characters) in a unified stream. The project was initially designed as a monolithic Spring Boot application and later evolved into a microservices-based architecture. The lab was divided into the following key stages:

- Monolithic API Development.
- Frontend Development and Deployment.
- Security Implementation.
- Microservices Architecture.
- Final Deployment and Testing.

The entire application is deployed on AWS. This lab provided hands-on experience in API design, cloud deployment, security integration, and microservices architecture.

## Architecture

The system follows a serverless microservices approach, ensuring scalability and security through AWS services.

- Frontend: A JavaScript-based web app hosted on AWS S3, accessible via CloudFront or direct S3 hosting.
- API Gateway & Security: Amazon API Gateway routes requests to microservices, secured with AWS Cognito.
- Microservices Layer (AWS Lambda):
  - User Service → Manages user authentication and profiles.
  - Post Service → Handles post creation (140-character limit).
  - Stream Service → Aggregates posts into a global feed.
- Database: AWS EC2 with a Docker container running MySQL.

Requests flow through API Gateway, invoking Lambda functions, which interact with DynamoDB, ensuring a scalable, serverless architecture.

## Class Design

```
src/main/java
└── co.edu.ecuelaing.arep.microservicios
    ├── config
    │   ├── CognitoLogoutHandler.java
    │   ├── JwtAuthenticationFilter.java
    │   ├── SecurityConfig.java
    │   ├── SecurityConfiguration.java
    │   └── WebConfiguration.java
    ├── controller
    │   ├── AuthController.java
    │   ├── HomeController.java
    │   ├── PostController.java
    │   ├── RepostController.java
    │   └── UserController.java
    ├── model
    │   ├── Post.java
    │   ├── Repost.java
    │   └── User.java
    ├── repository
    │   ├── PostRepository.java
    │   ├── RepostRepository.java
    │   └── UserRepository.java
    ├── service
    │   ├── JwtUtil.java
    │   ├── PostService.java
    │   ├── RepostService.java
    │   └── UserService.java
    └── MicroserviciosApplication.java      # Main Class

src/main/resources
    ├── images/                             # README resources
    ├── application.properties
    └── application.yml

src/test/java
└── co.edu.ecuelaing.arep.microservicios
    ├── Lab7
    │   └── Lab7ApplicationTests.java
    └── MicroserviciosApplicationTests.java

HELP.md
pom.xml
README.md
```

## Secure Login with Hashing

This system safeguards user passwords by utilizing hashing techniques, eliminating the need to store them in plain text. We implement the `BCrypt` algorithm, a robust and widely adopted password hashing function that integrates a salt to defend against rainbow table attacks.

When a user registers or updates their password, the system applies `BCrypt` hashing before saving it in the database. This approach ensures that even if the database is compromised, retrieving or decrypting the actual passwords remains extremely difficult.

During authentication, the entered password is hashed and compared with the stored hash to verify user credentials securely.

## Getting Started

These instructions will allow you to get a working copy of the project on your local machine for development and testing purposes.

### Prerequisites

- [Java](https://www.oracle.com/co/java/technologies/downloads/) 17 or higher.
- [Maven](https://maven.apache.org/download.cgi). 3.8.1 o higher.
- [AWS](https://aws.amazon.com/). Account
- [Git](https://git-scm.com/downloads) (optional).
- Web Browser.

To check if installed, run:

```
java -version
```
```
mvn --version
```
```
docker --version
```
```
git --version
```

### Installing

1. Download the repository from GitHub in a .zip or clone it to your local machine using Git.

    ```
    git clone https://github.com/CristianAlvarez-b/AREP-Lab7.git
    ```

2. Navigate to the project directory.

    ```
    cd AREP-Lab7
    ```

3. Build the project by running the following command:

    ```
    mvn clean install
    ```

   ![](src/main/resources/images/succes.png)

## Monolith

Design an API and create a Spring monolith that allows users to make posts of up to 140 characters and register them in a single stream of posts (like Twitter). Consider three entities: User, Stream, and Posts.

https://github.com/user-attachments/assets/176ce6c4-b5b2-4ee0-8996-b119d1dc7edc

## JS Application

Create a JS application to use the service. Deploy the application on S3. Ensure it is accessible on the internet.

https://github.com/user-attachments/assets/ac980164-5293-4c56-ac68-4f6de058c4f7

## EC2 Database Creation

1. Create a default EC2 instance on AWS and add a new Security Rule on the Security Group of the instance.

   ![](src/main/resources/images/security-db.png)

2. Connect to the EC2 instance, install docker with these commands:

    ```
    sudo yum update -y
    sudo yum install docker
    ```

3. Create and install a MySQL image on Docker, this will be our Database:

    ```
    docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=properties_db -p 3306:3306 -d mysql
    ```

4. You can get into the database in the container (password: root) with the command:

    ```
    docker exec -it mysql-container mysql -u root -p
    ```

5. Create a new table for the users and passwords:

    ```
    CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password CHAR(60) NOT NULL
    );
    ```
   
## Cognito

Add security using JWT with AWS Cognito or another technology.

https://github.com/user-attachments/assets/5b23ca6b-346a-4a0e-8785-187f4abd51e9

## Lambda

Split the monolith into three independent microservices using Lambda.  
Deploy the service on AWS Lambda.

https://github.com/user-attachments/assets/416c9f0f-8e6c-46a6-8a50-9cc717d5570e

## Running the Tests

The tests performed verify the Controller and Services of the application.

To run the tests from the console, use the following command:

```
mvn test
```

If the tests were successful, you will see a message like this in your command console.

![](src/main/resources/images/tests.png)

## Built With

* [Java Development Kit](https://www.oracle.com/co/java/technologies/downloads/) - Software Toolkit
* [Maven](https://maven.apache.org/) - Dependency Management
* [Git](https://git-scm.com/) - Distributed Version Control System

## Authors

* **Cristián Javier Alvaréz Baquero** | **Juan David Contreras Becerra** - *Taller 7 | AREP* - [AREP-Lab7](https://github.com/CristianAlvarez-b/AREP-Lab7.git)
