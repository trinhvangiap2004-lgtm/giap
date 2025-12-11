/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bt;

/**
 *
 * @author ADMIN
 */

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class OrderConsumer {

    private static final String QUEUE_NAME = "order_queue";

    public static void startWorker() throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        System.out.println("[Worker] Đang chờ đơn hàng...");

        DeliverCallback callback = (consumerTag, message) -> {
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(message.getBody());
                ObjectInputStream in = new ObjectInputStream(bis);
                Order order = (Order) in.readObject();

                System.out.println("\n===== WORKER XỬ LÝ =====");
                System.out.println("Mã đơn: " + order.getOrderId());
                System.out.println("Khách: " + order.getCustomerName());
                System.out.println("Sản phẩm: " + order.getProductId());
                System.out.println("Số lượng: " + order.getQuantity());
                System.out.println("-> Gửi email...");
                System.out.println("-> Cập nhật kho...");
                System.out.println("-> Ghi log...");
                System.out.println("=========================\n");

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {});
    }
}
