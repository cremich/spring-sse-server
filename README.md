# Spring MVC Streaming and SSE Request Processing
[![Build Status](https://travis-ci.org/cremich/spring-sse-server.svg?branch=master)](https://travis-ci.org/cremich/spring-sse-server)
[![codecov](https://codecov.io/gh/cremich/spring-sse-server/branch/master/graph/badge.svg)](https://codecov.io/gh/cremich/spring-sse-server)

A [Server-Sent-Event](https://streamdata.io/blog/server-sent-events/) implementation based on the SseEmitter from 
Spring. The server provides a rest api to create a new event stream and subscribe to the event data.

## Prerequisites
* JDK 11
* Maven 3.6.0

Currently there are not many prerequisites to run this sample. Please ensure you have a JDK 11 installed. Maven is installed via
the `mvnw` maven-wrapper. Node.js and npm are installed via the `frontend-maven-plugin` during the [local installation](#local-installation).   

## Project Setup

## Local installation
To install all required dependencies simply run a 

    ./mvnw clean install
  
from the project root. This will install the backend as well as the frontend dependencies including node.js (v10.15.2) 
via maven. The `frontend-maven-plugin` is used to handle a local installation of the required node and npm version as well as
installing all frontend dependencies via maven.

## Spring Boot Backend Module
### Start the Server
You can launch the server by executing 

    ./mvnw -f ./backend/pom.xml spring-boot:run

The server application will be available on [http://localhost:8080](http://localhost:8080).

### Package your application
In order to create an executable jar you can run 
    
    ./mvnw clean package
    
The resulting jar can be found in `./backend/target/backend-VERSION.jar`. This file contains the
spring-boot server as well as the built frontend files by using the `maven-resources-plugin`. 
The maven package command triggers a built of the frontend application and copies the frontend built
into the `backend/src/main/java/resources/public` folder to deliver the frontend application via the
spring-boot server.

### The REST Api
| Method        | Path               | Description  |
| ------------- |:-------------------| :-------------------|
| POST          | /streams           | Create a new stream |
| GET           | /streams/{id]/data | Get the data from a given stream|

#### Create a new stream
To create a new stream you need to send a POST request to the `/streams` endpoint. The following sample shows
a valid request and the corresponding response based on [httpie](https://httpie.org) and 
[libcurl](https://curl.haxx.se/libcurl/).

```bash
 $ http POST :8080/streams
   HTTP/1.1 200 
   Content-Type: application/json;charset=UTF-8
   Date: Fri, 15 Mar 2019 20:38:51 GMT
   Transfer-Encoding: chunked

  {
      "id": "c36032a6-6c4d-4cdd-a45b-0977cc410ebc"
  }
```

```bash
 $ curl -i -X POST http://localhost:8080/streams
   HTTP/1.1 200 
   Content-Type: application/json;charset=UTF-8
   Transfer-Encoding: chunked
   Date: Fri, 15 Mar 2019 20:51:44 GMT
   
   {"id":"671705ad-cda1-4450-8d34-215097e89e4c"}  
```

#### Get the stream data
The server-side event stream syntax is simple. Set the "Content-Type" header to "text/event-stream" and connect to the 
event stream via a GET request to `/streams/{id}/data`.

```bash
 $ http GET :8080/streams/c36032a6-6c4d-4cdd-a45b-0977cc410ebc/data "Accept: text/event-stream" --stream
   HTTP/1.1 200 
   Content-Type: text/event-stream;charset=UTF-8
   Date: Fri, 15 Mar 2019 20:39:08 GMT
   Transfer-Encoding: chunked
  
   data:2
   event:server sent count event
```

```bash
 $ curl -i -H "Accept: text/event-stream"  http://localhost:8080/streams/af56a3a9-b83d-4319-bb25-220d84841980/data
  HTTP/1.1 200 
  Content-Type: text/event-stream;charset=UTF-8
  Transfer-Encoding: chunked
  Date: Fri, 15 Mar 2019 20:59:08 GMT
  
  data:4
  event:server sent count event

```
### Vue.js Frontend Module
The frontend module contains a vue.js web-application. The web-application can be started standalone or can be delivered by
the spring-boot-server.

#### Project setup
To install all frontend dependencies please run  

    npm --prefix frontend install

from your project root folder.

#### Compiles and hot-reloads for development
You can run a local webpack server by executing

    npm --prefix frontend run serve

from the project root folder. 

This gives you a faster feedback during development and provides you with an instant browser reload whenever you change the source
files in the frontend module. Keep in mind, that the server application is not automatically started. So please additionally start the 
[spring-boot server](#start-the-server).  

#### Compiles and minifies for production
Webpack is configured to write the `dist` contents to the `target` folder

    npm --prefix frontend run build
    
compiles and minfies the frontend application. The `dist` files can be copied to the `backend/src/main/java/resources/public` 
folder to deliver the frontend application via the spring-boot server. 

#### Run your tests
Jest is used for frontend testing. All unit tests are located in the folder `frontend/tests/unit`.
    
    npm --prefix frontend run test:unit

### Customize configuration
See [the vue.js configuration reference](https://cli.vuejs.org/config/).
