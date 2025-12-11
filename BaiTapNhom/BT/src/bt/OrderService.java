/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bt;

/**
 *
 * @author ADMIN
 */

import java.util.UUID;

public class OrderService {

    public static void createOrder(String customer, String product, int qty) {
        try {
            String orderId = UUID.randomUUID().toString().substring(0, 8);
            Order order = new Order(orderId, customer, product, qty);

            // Push vào message queue (async)
            OrderProducer.sendOrder(order);

            System.out.println(">>> Order saved successfully!");
            System.out.println(">>> Response to user: Your order has been received.");

        } catch (Exception e) {
        }
    }
}
