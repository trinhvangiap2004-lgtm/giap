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
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class OrderProducer {
    private static final String QUEUE_NAME = "order_queue";

    public static void sendOrder(Order order) throws Exception {
        try (Channel channel = RabbitMQConnection.getConnection().createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(order);
            out.flush();
            
            channel.basicPublish("", QUEUE_NAME, null, bos.toByteArray());
            System.out.println("[Producer] Order pushed to queue: " + order.getOrderId());
        }
    }
}
