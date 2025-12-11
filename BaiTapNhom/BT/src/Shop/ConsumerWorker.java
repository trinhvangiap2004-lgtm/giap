/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Shop;

/**
 *
 * @author ADMIN
 */

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class ConsumerWorker {
    private static final String ORDER_QUEUE = "orders";
    private static final String CHAT_QUEUE = "chat";

    public static void start() throws Exception {
        Connection conn = RabbitMQConnection.getConnection();
        Channel channel = conn.createChannel();

        channel.queueDeclare(ORDER_QUEUE, true, false, false, null);
        channel.queueDeclare(CHAT_QUEUE, true, false, false, null);

        // Xử lý đơn hàng
        channel.basicConsume(ORDER_QUEUE, true, (consumerTag, msg) -> {
            String message = new String(msg.getBody(), StandardCharsets.UTF_8);
            System.out.println("🛒 Xử lý đơn hàng: " + message);
            // TODO: gửi email, cập nhật kho, log
        }, consumerTag -> {});

        // Xử lý chat
        channel.basicConsume(CHAT_QUEUE, true, (consumerTag, msg) -> {
            String message = new String(msg.getBody(), StandardCharsets.UTF_8);
            System.out.println("💬 Tin nhắn mới: " + message);
            // TODO: hiển thị lên dashboard admin
        }, consumerTag -> {});
    }
}
