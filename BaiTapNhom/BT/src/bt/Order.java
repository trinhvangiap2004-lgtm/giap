/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bt;

/**
 *
 * @author ADMIN
 */

import java.io.Serializable;

public class Order implements Serializable {
    private String orderId;
    private String customerName;
    private String productId;
    private int quantity;

    public Order(String orderId, String customerName, String productId, int quantity) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
}

