# Spring MVC Streaming and SSE Request Processing
A [Server-Sent-Event](https://streamdata.io/blog/server-sent-events/) implementation based on the SseEmitter from 
Spring. The server provides a rest api to create a new event stream and subscribe to the event data.

## REST Api
| Method        | Path               | Description  |
| ------------- |:-------------------| :-------------------|
| POST          | /streams           | Create a new stream |
| GET           | /streams/{id]/data | Get the stream data |

### Create a new stream
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

### Get the stream data
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
## Vue.js Frontend

### Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Run your tests
```
npm run test
```

### Lints and fixes files
```
npm run lint
```

### Run your unit tests
```
npm run test:unit
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
