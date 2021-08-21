package com.example.rabbitmqandspring.controller;

import com.example.rabbitmqandspring.model.Person;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
