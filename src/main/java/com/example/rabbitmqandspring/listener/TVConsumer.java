package com.example.rabbitmqandspring.listener;

import com.example.rabbitmqandspring.model.Person;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

@Service
public class TVConsumer {

//    @RabbitListener(queues = {"TV-q"})
//    public void getMessageTVQ(Person person){
//        System.out.println("TV-q message received with person: " + person.getName());
//    }

    @RabbitListener(queues = {"TV-q"})
    public void getMessageACQ(byte[] message) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message);
        ObjectInput objectInput = new ObjectInputStream(byteArrayInputStream);

        Person person = (Person) objectInput.readObject();
        objectInput.close();
        byteArrayInputStream.close();

        System.out.println("TV-q message received with name: " + person.getName());
    }
}
