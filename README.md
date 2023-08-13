## Weather App Prediction Service
## Setup
To run the SWA Prediction Service, build the Docker image and run the container by executing the following commands:
   ```bash
   mvn clean install 
   docker build -t=YOUR_CONT_NAME .
   docker run YOUR_CONT_NAME
   ```
## About
The SWA Prediction  service for retrieving data from Backing Service and creating the Weather Prediction for the 
required city. Once the service is up and running, you can access the API documentation to explore the available endpoints and their
functionalities. The Open API Swagger documentation can be accessed at http://localhost:8081/swagger-ui/index.html)
## Authors

- Vadym Ostapovych - ostapva@fel.cvut.cz
- Tomáš Hauser - hauseto2@fel.cvut.cz


