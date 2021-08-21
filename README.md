# SpringAndRabbitMQDemo

A Spring Boot demonstration with RabbitMQ. 

## Direct, topic and fanout exchange

When a GET request is sent to `http://localhost:8080/api/v1/test/Jimmy`, a message with the 
Serializable object Person is sent to RabbitMQ, with the name "Jimmy" and a response is sent to the browser.

## Headers exchange

As above, but with a message sent directly to "AC-q", using `http://localhost:8080/api/v1/test/header/Jane`.