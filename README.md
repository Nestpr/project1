---
output:
  pdf_document: default
  html_document: default
---

# Práctice 2: Incorporation of data into the system architecture.

### Name: Néstor Ortega Pérez
### Subject: Desarrollo de Aplicaciones para Ciencia de Datos
### Course: 2º
### University: ULPGC
### Degree: Ciencia e Ingeniería de Datos
### Escuela de Ingeniería Informática

---

This weather prediction system consists of three main components: the Prediction Provider, the Broker (using ActiveMQ), and the Event Store Builder.

The Prediction Provider acquires meteorological data from the 8 Canary Islands, generates events in JSON format with detailed information, and sends them to the Broker through the prediction.Weather topic.

The Broker, implemented using ActiveMQ, serves as an intermediary for communication between components. It receives meteorological events from the Prediction Provider and publishes them on the prediction.Weather topic, allowing other modules to subscribe and consume this information.

The Event Store Builder, subscribed to the prediction.Weather topic, is responsible for temporarily storing these events in an organized directory structure. 

---

## Resources Used

### Development Environment

- **IntelliJ IDEA:** This project is developed using IntelliJ IDEA as the integrated development environment, and it is written in the Java language. It is recommended to download and install the latest version of IntelliJ for an optimal development experience.

### Version Control

- **Git:** Git was used as the version control system. Make sure to have Git installed locally. You can download Git [here](https://git-scm.com/).

### Build and Dependency Management

- **Apache Maven:** This project utilizes Maven for dependency management and build processes. Ensure that Maven is installed. You can download Maven [here](https://maven.apache.org/).
   
## Other Implementations to Consider

On the weather-provider module you will need to introduce the api, brokerUrl and topicName. And on the event store builder module you will ned to put outputDirectory, brokerURL, topicName, clientId and a subscriptionName. This variables will be used as arguments by the main method. 

The steps to be executed are as follows:

1- Go to More Actions on the Main Class

![Captura de pantalla 2023-12-08 a las 23 55 20](https://github.com/Nestpr/project1/assets/145444799/d0db1850-0852-4ca1-941e-b5e7428328d5)
![Captura de pantalla 2023-12-08 a las 23 55 21](https://github.com/Nestpr/project1/assets/145444799/77b029cd-65de-464a-a779-44de7a893302)

2- Select Edit

3- Put the arguments with a separation between each value.

![Captura de pantalla 2023-12-08 a las 23 55 49](https://github.com/Nestpr/project1/assets/145444799/04aeecf0-1c7b-4e96-90b1-bf1dce6fbd96)

  
## How to Run the Program

1. Navigate to the Main class of the weather-provider module.

2. Choose the days and the time for the forecast in the generateInstantListAtHour method:

![Captura de pantalla 2023-11-17 a las 19 11 57](https://github.com/Nestpr/project1/assets/145444799/d38d80dd-a04f-4523-a5de-ccca0a085fc1)

3. Execute the Main class:

![Captura de pantalla 2023-11-17 a las 23 41 49](https://github.com/Nestpr/project1/assets/145444799/11356b21-168c-4e34-9bca-92e6547e3483)

4. Navigate to the Main class of the event store builder module.

5. Execute the Main class:

![Captura de pantalla 2023-11-17 a las 23 41 49](https://github.com/Nestpr/project1/assets/145444799/11356b21-168c-4e34-9bca-92e6547e3483)

6. To stop the program's execution, simply stop running the program.


# Used Architecture: Model-Control

This project follows the Model-Control design principle, a variant of the Model-View-Controller (MVC) design pattern. The Model-Control focuses on the separation of responsibilities between the data model and the controller, although in this case, there is no user interface.

### Principle Description

The Model-Control Design is based on dividing the application into two main components:

1. **Model:**
   Classes defining the structure of the data obtained from the API. These classes reflect the different data types handled by the application.
2. **Controller:**
   The controller is responsible for receiving user inputs, processing them, and taking appropriate actions.
## Class Diagram

![Diagrama de Clases]


