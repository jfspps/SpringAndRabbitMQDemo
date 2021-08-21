package com.example.rabbitmqandspring.controller;

import com.example.rabbitmqandspring.model.Person;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/test/{name}")
    public String testApi(@PathVariable("name") String name){
        Person person = new Person(1L, name);

        // object sent to queue must be Serializable; send directly to queue
        rabbitTemplate.convertAndSend("Mobile-q", person);

        // to send to a specific queue via the direct exchange
        rabbitTemplate.convertAndSend("direct-exchange", "tv", person);

        // for fanout exchange
        rabbitTemplate.convertAndSend("fanout-exchange", "", person);

        // for topic exchange
        rabbitTemplate.convertAndSend("topic-exchange", "fuzzyness.ac", person);

        return "Request received from " + name;
    }

    // demo of header exchange; sends message to AC-q only
    @GetMapping("/test/header/{name}")
    public String testApi_header(@PathVariable("name") String name) throws IOException {
        Person person = new Person(1L, name);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput objectOutput = new ObjectOutputStream(byteArrayOutputStream);

        objectOutput.writeObject(person);
        objectOutput.flush();
        objectOutput.close();

        byte[] byteMessage = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();

        Message message = MessageBuilder.withBody(byteMessage)
                .setHeader("item1", "mobile")
                .setHeader("item2", "ac")
                .build();

        rabbitTemplate.send("headers-exchange", "", message);

        return "Request received from " + name;
    }
}
