/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bt;

/**
 *
 * @author ADMIN
 */

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) throws Exception {

        // Khởi động Worker ở thread khác
        new Thread(() -> {
            try {
                OrderConsumer.startWorker();
            } catch (Exception e) {}
        }).start();

        Scanner sc = new Scanner(System.in);

        while (true) {
    System.out.println("===== HỆ THỐNG ĐẶT HÀNG =====");

    System.out.print("Tên khách hàng: ");
    String customer = sc.nextLine();

    System.out.print("Mã sản phẩm: ");
    String product = sc.nextLine();

    System.out.print("Số lượng: ");
    int qty = Integer.parseInt(sc.nextLine());

    



            OrderService.createOrder(customer, product, qty);
        }
    }
}

