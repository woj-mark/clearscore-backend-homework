# ClearScore-Backend Technical Test

## Overview
This repository contains my implementation a microservice collating financial products from a small selection of partners.
I decided to build the microservice in Scala 2.13 using libraries from Scala Typelevel ecosystem, which are adopted by ClearScore in building microservices:
- **http4s** for the http library;
- **cats-effect** to handle side-effects in the microservice;
- **circe** for to convert Scala case classes into a JSON string and vice versa (de/serialisation) by defining JSON encoders and decoders;
- **refined** to validate input data in accordance with the requirements of the Swagger documentation (at compile-time).

I adopted TDD throughout- I used **MUnit** to perform unit testing as well **Postman** to test the HTTP routes. The application is built using **sbt** (1.8).


## Architecture and Design
The diagram below provides a high-level overview of the architecture of the Cardscores microservice.

 ![image](https://github.com/woj-mark/clearscore-backend-homework/blob/main/cardscores/architecture_schematic.JPG)

### HTTP Layer
The HTTP layer exposes the `/creditCards` endpoint (defined in `CardscoresRoutes` module) which consumes user's inputs sent via HTTP POST request in JSON format and returns the the aggregated data from partner APIs. 

### Service Layer
The service layer contains the `CardScoreService` which implements the business logic. It obtains the parsed user requests' object entities obtained via `CardscoresRoutes`. It consequently uses the data parsed from user requests to generate and send two HTTP POST requests to the partner HTTP APIs  (using http4s Ember client). It  parses the responses from the partner APIs from the JSON into user response object entities. Finally, it computes the sorting scores for the response object entities and builds custom response which returned to the user via the `/creditCards` endpoint.


## Endpoints
The endpoints provided by the app are as follows:
| METHOD | URL    | DESCRIPTION    |
| ----- | --- | --- |
| POST | /creditScores   | Returns: `200` with list of collected cards, `400` if inputs are invalid/illegal or `500` if one of the partners is not available/down (please see Limitations paragraph for further comments)|

## Engineering Approach 
I started by extracting the requirements provided in the task specification and Swagger documentation for the creditCards and partners' microservices. I developed a high-level [design](Requirements&Design/Untitled-2023-02-19-1637.excalidraw.png) of the microservice before I started wriring code

I adopted functional programming paradigm to arrange the code, model the data domain and implement business logic using algebraic data types. I have attempted to use tagless final pattern in Scala to organise the code responsibilities between the following modules:
- `ClearScoreRoutes` - contains the /creditCards endpoint. The routes are linked to the business logic through an instances of HttpRoutes which uses a partial function to match an incoming HTTP request and produce an HTTP response with a side effect.
-`CreditCardsService`- contains the  implementation of the business logic for the /creditCards API endpoint, also defines the http4s Client (Ember) sending requests to the partner APIs. 
- `ClearScoreServer` - contains the implementation of the http4s server (Ember) which also acts as a Client (Ember) to the partner APIs. In the  server implementation (`Main.scala`), I'm using cats-effect `IO` monad to delay the evaluation of the "impure" effects till the "end of the world". I'm also using http4s (client and server) loggers for logging requests and responses.
- `Config` - extracts the configurable parameters from the environment variables
- `Domain`- defines the data models for the entities involved in the application (i.e. CardsResponse, ScoredCardsRequests etc.)

It is the first time I have attempted to adopt the Tagless Final programming pattern and I have enjoyed learning how to abtract the effects using cats-effect library. 

## Validation 
By using the `refined` library at compile-time I have enabled returning the `400` Bad Request for illegal parameters provided in request to `/creditCards` for name (empty string), salary (negative integers or credit score (outside the  [0-700] range).

## Configuration
As required in the task specification, I have enabled the `HTTP_PORT`, `CSCARDS_ENDPOINT` and `SCORECARDS_ENDPOINT` environment variables to be configures in the start.sh script.

I have also decided to enable the user to configure the following environment variables:
- `HOST_ADDRESS`- to confiure the IP address of the host; 
- `USER_AGENT_ID`-  to configure the ProductId name of the user agent header sent in the POST request to partner API services.


## Testing
### Unit Testing
To run unit tests, please tun `sbt test` from the root directory.

### Testing HTTP Routes
Though I have attempted to run HTTP route testing, I decided to use a method I am more familiar with due to the limited time contraints. I tested the routes using `Postman` on the running application to test whether the microservice returns correct responses for a range of legal and illegal user inputs (as defined in the Swagger documentation) . All the tests and responses are contained in the following Postman collection: [postman_collection](cardscores/creditCards_v3.postman_collection). Please refer to the following [link](https://learning.postman.com/docs/getting-started/importing-and-exporting-data/) to see how to import the collection to your Postman app.

## Engineering Assumptions
I have made a couple of assumptions, which I have document here:
- I identified that the returning cardScore are returned with precision to 3 significant figures (digits) as well as rounded using floor function. For example, equation gives a result of `0.21256244` and the creditCardsResponse shall return `0.212`. For this reason, I'm using floor rounding and return 3 significant figures to obtain the CreditScore. The documentation does not confirm whether this is a required behaviour, but it is inferred from the sample of results available in the specification.
- I identified that if one of the partner services fails, it would be still expected to see the result obtaiened from the running service (rather than returning `500` Status). I believe such fault-tolerant behaviour would be crucial  if the microservice is communicating with dozens of partners who are likely to fail very frequentlty. I have witnessed several failures of partners once visiting ClearScore office for an interview. However, I was not able to implement such behaviour in my application within the time constraints I had for this task. 

Limitations
I have developed the microservice in a time-contrained environment. I have identified a number of limitations in my implementation, which I would like to address and am suggesting improvements:
- The `/creditCards` API returns a `50`0 Response if one of the partner services is down. This is not fully fault tolerant behaviour. To address that, I would focus on improvement of the error management and handling, for example with `Either` or `EitherT` from `cats` library. 
- I am only performing  a basic compile-time validation on the inputs, forcing a `400` Response if inputs do not comply with the refined types of the name, creditScore and Salary. Nevertheless, to properly handle the validation of the HTTP requests and responses, I would perform runtime validation, i.e. using `Validated` from `cats` library, which would also allow me to accumulate errors.
- I have documented that the code for extraction of partner's endpoints from environment variables might require refactoring. Currently, it would always provide the URI specified in the documentation. I would be able to refactor this if more time was available, for instance,  using `Pureconfig` library.


## Deployment 
I would use **Github Actions** to perform CI/CD deployment of the microservice in the following steps:
- Firstly, I would start a pull request on GitHub and seek feedback and comments from fellow engineers. As part of the merging process (to Master/Production branch), the code would go through additional tests.
- Subsequently, I would complete steps to create a **Docker container**, which is an isolated runtime environment for the microservice. I would start with generation of a **Docker image** file which contains instructions for creating a container that can run on a Docker platform.
- Thirdly, I would select a virtual machine, such as  **Amazon Elastic Compute Cloud (EC2)**. I would publish the Docker image file to it and run it to build the Docker container.
- Consequently, I would perform **testing** of the cloud-deployed microservice. I would test whether the '/creditCards' endpoint is returning correct responses using the aferomentioned Postman test collection. Additionally, if a front-end application sending POST request to the microservice is within my organisation, I would aim to perform **integration testing**.
- In production, I would regularly **monitor the real-time performance** of the microservice by inspecting the log file. 


