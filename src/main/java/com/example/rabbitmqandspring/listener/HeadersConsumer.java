package com.example.rabbitmqandspring.listener;

import com.example.rabbitmqandspring.model.Person;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

@Service
public class HeadersConsumer {

    @RabbitListener(queues = {"AC-q"})
    public void getMessageACQ(byte[] message) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message);
        ObjectInput objectInput = new ObjectInputStream(byteArrayInputStream);

        Person person = (Person) objectInput.readObject();
        objectInput.close();
        byteArrayInputStream.close();

        System.out.println("AC-q message received with name: " + person.getName());
    }
}
