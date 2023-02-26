# ClearScore-Backend Technical Testhomework

## Overview
This repository contains my implementation a microservice collating financial products from a small selection of partners.

I decided to build the microservice in Scala 2.13 using libraries from Scala Typelevel ecosystem, which is adopted by Clearscore in building microservices:
- http4s for the http library;
- cats-effect to handle side-effects in the microservice;
- circe for JSON parsing;
- refined to validate input data in accordance with the requirements of the Swagger documentation

I also adopted MUnit to perform unit testing, whereas I used Postman to test whether the HTTP routes.

## Architecture
The diagram below provides a high-level overview of the architecture of the Cardscores microservice.
![My Image](architecture_schematic.jpg)

The HTTP layer exposes the /creditCards endpoint (defined in CardscoresRoutes module) which consumes user's inputs sent via POST request in JSON format and returns the the aggregated data from partner APIs.

The service layer contains the CardScoreService which implements the business logic. It obtains the parsed user requests obtained via CardscoresRoutes and sends POST requests to the partner HTTP APIs. 
It consequently computes the sorting scores for the obtained responses and returns the aggregated and sorted cardScores for each card obtained from partner APIs to be exposed via /creditCards endpoint.

The application is built using sbt and is running on host and port defined in the Config object.


Programming Style 
I have attempted to adopt purely functional paradigm to arrange the code, model the data domain and implement business logic. I have attempted to adopt tagless final pattern in Scala to organise the code responsibilities between the following modules:
Modules
ClearScoreRoutes - contains the /creditCards endpoint
CreditCardsService- contains the  implementation of the business logic for the /creditCards API endpoint, also defines the http4s Client (Ember) sending requests to the partner APIs
ClearScoreServer - contains the implementation of the http4s server (Ember)
Config - extracts the configurable parameters from the environment variables
Domain- defines the data models for the entities involved in the application (i.e. CardsResponse, ScoredCardsRequests etc.)

It is the first time I have adopted this pure functional programming pattern and enjoyed learning how to abtract the effects using cats-effect library. 

Validation 
By using the refined library at compile-time I have enabled returning the 400 Bad Request for illegal parameters provided in request to /creditCards for name (empty string), salary (negative integers or credit score (outside the  [0-700] range).

Configuration
I have enabled the 

Testing
Unit Testing
To run unit tests, please tun sbt test

Testing HTTP Routes
Though I have attempted to run HTTP route testing, I decided to use a method I am more familiar with due to the limited time contraints. I tested the routes using Postman on the running application to test whether the microservice returns correct responses for a range of legal and illegal user inputs.
All the tests and responses are contained in the following Postman collection.

