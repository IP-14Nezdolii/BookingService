# Booking apartment service

## Project description
A web service for booking accommodation, which should provide users with 
the ability to find, compare, and book accommodation according to their 
to their needs and budget. The service should provide a wide range of options 
housing: apartments, houses, hotels, and other real estate, 
with the ability to view detailed information, photos, and reviews.

### Project team
- Front-end developer: Ганжа Христина
- Backend developer: Нездолій Владислав
- Test Engineer: Грубрин Роман

### Project structure
Frontend and backend develops in different branches.

## Backend
- Developing in the “back” branch
- Technology stack: Java(Maven, Spring Boot), PostgreSQL

### Launch instructions
Before starting the project, make sure that the following programs are installed on your system:
1. **Java 23:**  
   - Download and install the Java Development Kit (JDK) version 23 from the [official Oracle website](https://www.oracle.com/java/) or use an alternative.
   - To test Java, run the command:
      ```bash
      java -version
2. **Maven:**  
   - Download and install Apache Maven from the [official Maven website](https://maven.apache.org/).
   - To test Maven, run the command:
      ```bash
      mvn -v
Perform the following steps:
1.  Download the “back” branch.
2.  Change to the project directory.
3.  Run the command to load the dependencies and build the project:
    ```bash
     mvn clean install
5.  Run the project using Maven:
    ```bash
     mvn spring-boot:run
    
## Frontend
- Developing in the “front” branch
- Technology stack: Vue.js
### Launch Instructions
Before starting the project, make sure that the following programs are installed on your system:
1. Node.js (v16 or higher):
- Download and install Node.js from the official Node.js website.
- To verify Node.js installation, run:
   ```bash
    node -v
- Verify npm (Node Package Manager) version:
   ```bash
    npm -v
- Run the following command in the project directory to install all required dependencies:
   ```bash
    npm install
- Launch the project using Vite's development server:
   ```bash
    npm run dev

