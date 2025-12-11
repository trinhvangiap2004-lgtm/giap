/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Shop;

/**
 *
 * @author ADMIN
 */

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {
    private static final String ORDER_QUEUE = "orders";
    private static final String CHAT_QUEUE = "chat";

    public static void sendOrder(String msg) {
        sendMessage(ORDER_QUEUE, msg);
    }

    public static void sendChat(String msg) {
        sendMessage(CHAT_QUEUE, msg);
    }

    private static void sendMessage(String queue, String msg) {
        try (Connection conn = RabbitMQConnection.getConnection();
             Channel channel = conn.createChannel()) {
            channel.queueDeclare(queue, true, false, false, null);
            channel.basicPublish("", queue, null, msg.getBytes("UTF-8"));
            System.out.println("📤 Sent to " + queue + ": " + msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
