package utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class MqUtils {

    private Connection connection;
    private Channel channel;
    public void connectToRabbitMQ() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("host");
            factory.setUsername("rabbitmq");
            factory.setPassword("password");
            factory.setPort(5672);
            factory.setRequestedHeartbeat(5); // Optional for connection checks
            factory.setAutomaticRecoveryEnabled(true); // Optional for reconnections
            connection = factory.newConnection();
            channel = connection.createChannel();
            if (connection.isOpen()) {
                System.out.println("Connected to RabbitMQ");
            }
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && (connection.isOpen())) {
                channel.close();
                connection.close();
                System.out.println("Connection closed");
            }

        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void purgeQueue(String queueName) {
        try {
            channel.queuePurge(queueName);
            System.out.println("Queue purged");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
