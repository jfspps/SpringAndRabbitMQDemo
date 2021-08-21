# SpringAndRabbitMQDemo

A Spring Boot demonstration with RabbitMQ. When a GET request is sent to `http://localhost:8080/api/v1/test/Jimmy`, a message with the 
Serializable object Person is sent to RabbitMQ, with the name "Jimmy" and a response is sent to the browser.