package com.example.rabbitmqandspring.listener;

import com.example.rabbitmqandspring.model.Person;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

@Service
public class MobileConsumer {

    // a more generic use case is to pass the byte array; one could pass a Person object instead but only if the only
    // data type passed is of type Person; for multiple listeners, ensure they are all using the same argument type

//    @RabbitListener(queues = {"Mobile-q"})
//    public void getMessageMobileQ(Person person){
//        System.out.println("Mobile-q message received with person: " + person.getName());
//    }

    @RabbitListener(queues = {"Mobile-q"})
    public void getMessageACQ(byte[] message) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message);
        ObjectInput objectInput = new ObjectInputStream(byteArrayInputStream);

        Person person = (Person) objectInput.readObject();
        objectInput.close();
        byteArrayInputStream.close();

        System.out.println("AC-q message received with name: " + person.getName());
    }
}
