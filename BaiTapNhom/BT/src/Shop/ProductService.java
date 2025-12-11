/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Shop;
import java.util.*;

public class ProductService {
    private static final Map<String, Product> products = new LinkedHashMap<>();
    static {
        products.put("p1", new Product("p1","Laptop Dell",1500));
        products.put("p2", new Product("p2","Chuột Logitech",30));
        products.put("p3", new Product("p3","Bàn phím cơ",80));
        products.put("p4", new Product("p4","Tai nghe Sony",120));
    }
    public static Collection<Product> getProducts() { return products.values(); }
    public static Product getProduct(String id) { return products.get(id); }
}
