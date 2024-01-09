---
output:
  pdf_document: default
  html_document: default
---

# Final Practice: Hotels recommendations for an island based on its climate.

### Name: Néstor Ortega Pérez
### Subject: Desarrollo de Aplicaciones para Ciencia de Datos
### Course: 2º
### University: ULPGC
### Degree: Ciencia e Ingeniería de Datos
### Escuela de Ingeniería Informática

---
This application is presented as a utilitarian tool designed to assist users in choosing accommodations in the Canary Islands, based on weather forecasts for the number of days they choose (Maximum: 5 days). The application calculates the island with the best weather forecast and recommends 5 of its top-rated hotels according to TripAdvisor, along with the dates when their prices are the cheapest, average, and most expensive. This enables the user to book a hotel based on their daily costs on a certain island.

This system consists of five main components: the Weather Provider, the Hotel Provider, the Broker (using ActiveMQ),the Event Store Builder(data-lake) and the Business-Unit(island-hotel-recommendation).

The Weather Provider acquires every 6 hours meteorological data from the 8 Canary Islands, generates events in JSON format with detailed information, and sends them to the Broker through the topic prediction.Weather.

The Hotel Provider acquires every 6 hours the data from the five best hotels of each island along with the dates when their prices are the cheapest, average, and most expensive. It generates events in JSON format with detailed information, and sends them to the Broker through the topic prediction.Hotel.

The Broker, implemented using ActiveMQ, serves as an intermediary for communication between components. It receives meteorological and hotel events from the Weather and Hotel Providers and publishes them on the topics, allowing other modules to subscribe and consume this information.

The Event Store Builder(data-lake), subscribed to the topics, is responsible for storing these events in an organized directory structure. 

The Business Unit(island-hotel-recommendation), suscribed to the topics, takes the data from the Weather and Hotel providers and calculates the island with the best climate for the prediction, creates a data base (SQlite) with the best five hotels of the island with the best climate and the dates when their prices are the cheapest, average, and most expensive. This is the final data that is going to be consumed by the client.

---

## Resources Used

### Development Environment

- **IntelliJ IDEA:** This project is developed using IntelliJ IDEA as the integrated development environment, and it is written in the Java language. It is recommended to download and install the latest version of IntelliJ for an optimal development experience.

### Version Control

- **Git:** Git was used as the version control system. Make sure to have Git installed locally. You can download Git [here](https://git-scm.com/).

### Build and Dependency Management

- **Apache Maven:** This project utilizes Maven for dependency management and build processes. Ensure that Maven is installed. You can download Maven [here](https://maven.apache.org/).

### Message Broker and Communication

- **ActiveMQ:** is an open-source message broker that provides a reliable, scalable, and asynchronous communication platform. It is often used to implement messaging patterns in distributed systems. You can download ActiveMQ [here](https://activemq.apache.org/download.html).
   
## Other Implementations to Consider

Due to the API used to obtain hotel information(Xotelo) not providing accurate dates for hotels located on La Graciosa island, that location has been changed to another area on Gran Canaria island (Maspalomas).

Also, for short time periods, the Xotelo API does not gather sufficient data regarding the dates when hotels are more expensive, cheaper, or average. Therefore, when calculating the climate forecast for the locations, the user-provided dates are taken into account, but for hotel dates on the specific island, they are determined based on the next 5 days.

On the weather-provider module you will need to introduce the api and brokerUrl. On the hotel-provider module you will need to introduce the brokerUrl. On the business unit(island-hotel-recommendation module) you will need to put brokerURL and the Jdbc url to specify the location of the database. And on the event store builder module you will ned to put outputDirectory and brokerURL, all of them in the same order. This variables will be used as arguments by the main method. 

The steps to be executed are as follows:
  
1- Go to More Actions on the Main Class

![Captura de pantalla 2023-12-08 a las 23 55 21](https://github.com/Nestpr/project1/assets/145444799/77b029cd-65de-464a-a779-44de7a893302)

2- Select Edit

3- Put the arguments with a separation between each value.

![Captura de pantalla 2023-12-08 a las 23 55 49](https://github.com/Nestpr/project1/assets/145444799/c1768017-e0e7-4197-b004-ff440a8a0338)

## How to Run the Program

1- Start ActiveMQ on the terminal following these steps:

1.a - Navigate to the ActiveMQ Installation Directory and use this command to start it:

-On Mac/Unix based systems

   ```bash
   cd /path/to/activemq
   ```
  ```bash
   ./bin/activemq console
   ```
-On Windows
   ```bash
   cd C:\path\to\activemq
   ```
  ```bash
   bin\activemq.bat console
   ```
To shut down both process you need to press control + C on the terminal.

1. Navigate to the Main class of the weather provider module:

2. Execute the Main class:

![Captura de pantalla 2023-11-17 a las 23 41 49](https://github.com/Nestpr/project1/assets/145444799/11356b21-168c-4e34-9bca-92e6547e3483)

3. Navigate to the Main class of the hotel provider module.

4. Execute the Main class:

![Captura de pantalla 2023-11-17 a las 23 41 49](https://github.com/Nestpr/project1/assets/145444799/11356b21-168c-4e34-9bca-92e6547e3483)

5. Navigate to the Main class of the event store builder module.

6. Execute the Main class:

![Captura de pantalla 2023-11-17 a las 23 41 49](https://github.com/Nestpr/project1/assets/145444799/11356b21-168c-4e34-9bca-92e6547e3483)

7. Navigate to the Main class of the business unit(island-hotel-recommendation) module.

8. Enter the days(Maximum 5).

![Captura de pantalla 2024-01-09 a las 13 27 14](https://github.com/Nestpr/project1/assets/145444799/2fbee569-6add-45af-a536-9a537cc2abd4)

9. Execute the Main class:

![Captura de pantalla 2023-11-17 a las 23 41 49](https://github.com/Nestpr/project1/assets/145444799/11356b21-168c-4e34-9bca-92e6547e3483)

10. After the execution of all the modules, an SQlite data base will be on the JDBC Url that you put on the arguments, you can check the final data there. And also a folder with all the events as a Data Lake on the OutputDirectory that you put on the arguments.

11. To stop the program's execution, simply stop running the Main clasess of all modules.

# Used Architecture: Model-Control

This project follows the Model-Control design principle, a variant of the Model-View-Controller (MVC) design pattern. The Model-Control focuses on the separation of responsibilities between the data model and the controller, although in this case, there is no user interface.

### Principle Description

The Model-Control Design is based on dividing the application into two main components:

1. **Model:**
   Classes defining the structure of the data obtained from the API. These classes reflect the different data types handled by the application.
2. **Controller:**
   The controller is responsible for receiving user inputs, processing them, and taking appropriate actions.

## Class Diagram
![Captura de pantalla 2023-12-09 a las 13 12 42](https://github.com/Nestpr/project1/assets/145444799/b6bbf4b7-3bf4-41f2-9927-da09f262bf4f)
![Captura de pantalla 2023-12-09 a las 13 30 10](https://github.com/Nestpr/project1/assets/145444799/a66295d5-2b3e-48f1-b15c-951a02cd0c39)
![Captura de pantalla 2024-01-05 a las 16 51 08](https://github.com/Nestpr/project1/assets/145444799/75fcecb7-8afa-4712-88be-64e7ec5cd09b)
![Captura de pantalla 2024-01-09 a las 13 26 48](https://github.com/Nestpr/project1/assets/145444799/e112d5b0-7581-4f99-b730-f15659aa5344)

## How to execute the .Jar files

**Very important**

**It is necessary to run the JAR files in the order described below, as all modules must be executed in the background except for island-hotel-recommendation at the end to avoid terminal failures when entering data.**

1. Activate ActiveMQ on the terminal:

   1.a - Navigate to the ActiveMQ Installation Directory and use this command to start it in the background:

-On Mac/Unix based systems

   ```bash
   cd /path/to/activemq
   ```
  ```bash
   ./bin/activemq start
   ```
-On Windows
   ```bash
   cd C:\path\to\activemq
   ```
  ```bash
   bin\activemq.bat start
   ```
Just change start for stop to finish the ActiveMQ execution.

2. Execute weather-provider Jar on the terminal:

   2.a - Navigate to the weather-provider jar Directory and use this command to start it in the background:
   2.b - It is necessary to place the arguments in the order shown and append an '&' at the end for it to run in the background.
   
-On Mac/Unix based systems

   ```bash
   cd /path/to/weather-provider-1.0-SNAPSHOT.jar 
   ```
  ```bash
   java -jar weather-provider-1.0-SNAPSHOT.jar API BrokerUrl &
   ```
-On Windows
   ```bash
   cd C:\path\to\weather-provider-1.0-SNAPSHOT.jar 
   ```
  ```bash
   java -jar weather-provider-1.0-SNAPSHOT.jar API BrokerUrl &
   ```
3. Execute hotel-provider Jar on the terminal:

   3.a - Navigate to the hotel-provider jar Directory and use this command to start it in the background:
   3.b - It is necessary to place the arguments in the order shown and append an '&' at the end for it to run in the background.
   
-On Mac/Unix based systems

   ```bash
   cd /path/to/hotel-provider-1.0-SNAPSHOT.jar 
   ```
  ```bash
   java -jar hotel-provider-1.0-SNAPSHOT.jar BrokerUrl &
   ```
-On Windows
   ```bash
   cd C:\path\to\hotel-provider-1.0-SNAPSHOT.jar 
   ```
  ```bash
   java -jar hotel-provider-1.0-SNAPSHOT.jar BrokerUrl &
   ```
4. Execute data-lake Jar on the terminal:

   4.a - Navigate to the data-lake Jar Directory and use this command to start it in the background:
   4.b - It is necessary to place the arguments in the order shown and append an '&' at the end for it to run in the background.
   
-On Mac/Unix based systems

   ```bash
   cd /path/to/data-lake-1.0-SNAPSHOT.jar
   ```
  ```bash
   java -jar data-lake-1.0-SNAPSHOT.jar OutputDirectory BrokerUrl &
   ```
-On Windows
   ```bash
   cd C:\path\to\data-lake-1.0-SNAPSHOT.jar
   ```
  ```bash
   java -jar data-lake-1.0-SNAPSHOT.jar OutputDirectory BrokerUrl &
   ```

5. Execute island-hotel-recommendation Jar on the terminal:
   **This one cannot be run in the background because it does not allow entering data in the terminal.**

   5.a - Navigate to the island-hotel-recommendation jar Directory and use this command to start it in the background:
   5.b - It is necessary to place the arguments in the order shown
   
-On Mac/Unix based systems

   ```bash
   cd /path/to/island-hotel-recommendation-1.0-SNAPSHOT.jar 
   ```
  ```bash
   java -jar island-hotel-recommendation-1.0-SNAPSHOT.jar BrokerURL JdbcUrl
   ```
-On Windows
   ```bash
   cd C:\path\to\island-hotel-recommendation-1.0-SNAPSHOT.jar
   ```
  ```bash
   java -jar island-hotel-recommendation-1.0-SNAPSHOT.jar BrokerURL JdbcUrl
   ```

6. Finish all process using pkill -f.
   ```bash
   pkill -f "java -jar island-hotel-recommendation-1.0-SNAPSHOT.jar"
   pkill -f "java -jar event-store-builder-1.0-SNAPSHOT.jar"
   pkill -f "java -jar weather-provider-1.0-SNAPSHOT.jar"
   pkill -f "java -jar hotel-provider-1.0-SNAPSHOT.jar"
   ```
   
