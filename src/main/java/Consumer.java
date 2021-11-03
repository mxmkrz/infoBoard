


import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Message;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import java.util.concurrent.TimeoutException;

@Slf4j
@Named
@ApplicationScoped
public class Consumer implements Serializable {

    //    private final String USERNAME = "guest";
//    private final String PASSWORD = "guest";
//    private final String VIRTUALHOST = "/";
//    private final String HOST = "localhost";
//    private final Integer PORT = 5672;
    private static final String QUEUE_NAME = "myrabbitmq.queue";




    @Inject
    private  WebSocketPush webSocketPush;



    public void consume() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Receive message");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                String message = new String(body, StandardCharsets.UTF_8);
                log.info(" [x] Received '" + message + "'");
                if (message.contains("update")) {
                    System.out.println(message);
                    webSocketPush.sendMessage(message);

                    log.info("webSocketPush " + message);
                }
//                boolean autoAck = false;
//                GetResponse response = channel.basicGet(QUEUE_NAME, true);
//                log.info(Arrays.toString(response.getBody()) + "response");

            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    //    public void stop() throws IOException, TimeoutException {
//        channel.close();
//        connection.close();
//    }
//    }
//
//
//    }
//    public static void main(String[] args) throws IOException, TimeoutException {
//        Consumer consumer = new Consumer();
//        consumer.consume();
//    }
////        consumer.stop();
//
//
//    }
}






