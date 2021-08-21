package com.example.rabbitmqandspring.listener;

import com.example.rabbitmqandspring.model.Person;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    // a more generic use case is to pass the byte array; one could pass a Person object instead but only if the only
    // data type passed is of type Person

    @RabbitListener(queues = {"Mobile-q"})
    public void getMessageMobileQ(Person person){
        System.out.println("Message received with person: " + person.getName());
    }
}
