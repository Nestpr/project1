---
output:
  pdf_document: default
  html_document: default
---

# Final Practice: Hotel recommendations for an island based on its climate.

### Name: Néstor Ortega Pérez
### Subject: Desarrollo de Aplicaciones para Ciencia de Datos
### Course: 2º
### University: ULPGC
### Degree: Ciencia e Ingeniería de Datos
### Escuela de Ingeniería Informática

---
This application is presented as a utilitarian tool designed to assist users in choosing accommodations in the Canary Islands, based on weather forecasts for the number of days they choose (Maximum: 5 days). The application calculates the island with the best weather forecast and recommends 5 of its top-rated hotels according to TripAdvisor, along with the dates when their prices are the cheapest, average, and most expensive. This enables the user to book a hotel based on their daily costs.

This system consists of five main components: the Prediction Provider, the Hotel Provider, the Broker (using ActiveMQ), and the Event Store Builder(Data Lake) and the Business-Unit.

The Prediction Provider acquires every 6 hours meteorological data from the 8 Canary Islands, generates events in JSON format with detailed information, and sends them to the Broker through the topic.

The Hotel Provider acquires every 6 hours the data from the five best hotels of each island along with the dates when their prices are the cheapest, average, and most expensive. It generates events in JSON format with detailed information, and sends them to the Broker through the topic.

The Broker, implemented using ActiveMQ, serves as an intermediary for communication between components. It receives meteorological and hotel events from the Prediction and Hotel Providers and publishes them on the topic, allowing other modules to subscribe and consume this information.

The Event Store Builder(Data Lake), subscribed to the topic, is responsible for storing these events in an organized directory structure. 

The Business Unit, suscribed to the topic, takes the data from the Prediction and Hotel providers and calculates the island with the best climate for the prediction, creates a data base (SQlite) with the best five hotels of the island with the best climate and the dates when their prices are the cheapest, average, and most expensive. This is the final data that is going to be consumed by the client.


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

Due to the API used to obtain hotel information not providing accurate dates for hotels located on La Graciosa island, that location has been changed to another area on Gran Canaria island (Maspalomas).

On the weather-provider module you will need to introduce the api, brokerUrl and topicName. On the hotel-provider module you will need to introduce brokerUrl and topicName. On the business unit module you will ned to put brokerURL, topicName, clientId, subscriptionName and the Jdbc url to specify the location of the database. And on the event store builder module you will ned to put outputDirectory, brokerURL, topicName, clientId and a subscriptionName, all of them in the same order. This variables will be used as arguments by the main method. 

The steps to be executed are as follows:
  
1- Go to More Actions on the Main Class

![Captura de pantalla 2023-12-08 a las 23 55 20](https://github.com/Nestpr/project1/assets/145444799/d0db1850-0852-4ca1-941e-b5e7428328d5)
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

**Very Important**

It is necessary to run the modules in the order listed in the following explanation for the proper functioning of the application.

1. Navigate to the Main class of the business unit(island-hotel-recommendation) module.

2. Execute the Main class:

![Captura de pantalla 2023-11-17 a las 23 41 49](https://github.com/Nestpr/project1/assets/145444799/11356b21-168c-4e34-9bca-92e6547e3483)

3. Navigate to the Main class of the event store builder module.

4. Execute the Main class:

![Captura de pantalla 2023-11-17 a las 23 41 49](https://github.com/Nestpr/project1/assets/145444799/11356b21-168c-4e34-9bca-92e6547e3483)

5. Navigate to the Main class of the weather provider module:

6. Write the days for the forecast(Maximum 5).

![Captura de pantalla 2024-01-03 a las 23 51 06](https://github.com/Nestpr/project1/assets/145444799/041b900e-97aa-4da6-8ec1-6b10d014e73c)

7. Execute the Main class:

![Captura de pantalla 2023-11-17 a las 23 41 49](https://github.com/Nestpr/project1/assets/145444799/11356b21-168c-4e34-9bca-92e6547e3483)


8. Navigate to the Main class of the hotel provider module.

9. Write again the days for the forecast(Maximum 5) **It is important to enter the same number as in the weather provider to avoid errors**

![Captura de pantalla 2024-01-03 a las 23 51 21](https://github.com/Nestpr/project1/assets/145444799/d3a689e3-5ce5-4dcb-8c0e-4d6c2d645f43)

10. Execute the Main class:

![Captura de pantalla 2023-11-17 a las 23 41 49](https://github.com/Nestpr/project1/assets/145444799/11356b21-168c-4e34-9bca-92e6547e3483)

11. After the execution of all the modules, a SQlite data base will be on the JDBC Url that you put on the arguments, you can check the final data there.

12. To stop the program's execution, simply stop running the Main clasess of both modules.

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
![Captura de pantalla 2024-01-05 a las 16 56 43](https://github.com/Nestpr/project1/assets/145444799/1050fc8f-4d4a-48b9-b6fc-b8a751229860)

## How to ejecute the .Jar files

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

2. Execute Weather provider Jar on the terminal:

   1.a - Navigate to the Weather Provider jar Directory and use this command to start it in the background:
   1.b - It is necessary to place the arguments in the order shown and append an '&' at the end for it to run in the background.
   
-On Mac/Unix based systems

   ```bash
   cd /path/to/weather-provider.jar
   ```
  ```bash
   java -jar weather-provider-1.0-SNAPSHOT.jar API brokerUrl YourTopicName &
   ```
-On Windows
   ```bash
   cd C:\path\to\weather-provider.jar
   ```
  ```bash
   java -jar weather-provider-1.0-SNAPSHOT.jar API brokerUrl YourTopicName &
   ```

3. Execute Event Store Builder Jar on the terminal:

   1.a - Navigate to the Event Store Builder Jar Directory and use this command to start it in the background:
   1.b - It is necessary to place the arguments in the order shown.
   
-On Mac/Unix based systems

   ```bash
   cd /path/to/event-store-builder.jar
   ```
  ```bash
   java -jar event-store-builder-1.0-SNAPSHOT.jar outputDirectory brokerUrl YourTopicName YourClientId YourSubscriptionName
   ```
-On Windows
   ```bash
   cd C:\path\to\event-store-builder.jar
   ```
  ```bash
   java -jar event-store-builder-1.0-SNAPSHOT.jar outputDirectory brokerUrl YourTopicName YourClientId YourSubscriptionName
   ```
4. Finish both process using pkill -f.
   ```bash
   pkill -f "java -jar weather-provider-1.0-SNAPSHOT.jar"
   pkill -f "java -jar event-store-builder-1.0-SNAPSHOT.jar"
   ```
   
