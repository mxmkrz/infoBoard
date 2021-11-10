package com.t_systems.t_medical_center_system;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import java.util.concurrent.TimeoutException;

@Slf4j
@Named
@ApplicationScoped
public class Consumer implements Serializable {
    private static final String QUEUE_NAME = "myrabbitmq.queue";

    @Inject
    private WebSocketPush webSocketPush;

    private Connection connection;
    private Channel channel;


    public void consume() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Receive message");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body, StandardCharsets.UTF_8);
                log.info(" [x] Received '" + message + "'");
                System.out.println(message);
                webSocketPush.sendMessage(message);
                log.info("webSocketPush " + message);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    public void stop() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}






