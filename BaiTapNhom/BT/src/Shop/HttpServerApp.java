package Shop;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.Executors;

public class HttpServerApp {

    private static final Gson gson = new Gson();
    private static final CartService cartService = new CartService();
    private static final ChatService chatService = new ChatService();

    public static void main(String[] args) throws Exception {
        System.out.println("Kết nối RabbitMQ thành công!");
        System.out.println("Shop server running at http://localhost:8080");

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Serve index.html
        server.createContext("/", (HttpExchange ex) -> {
            File f = new File("src/web/index.html");
            if (!f.exists()) { send(ex, 404, "File not found"); return; }
            byte[] content = java.nio.file.Files.readAllBytes(f.toPath());
            ex.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
            ex.sendResponseHeaders(200, content.length);
            try (OutputStream os = ex.getResponseBody()) { os.write(content); }
        });

        // Get products
        server.createContext("/products", (ex) -> {
            Product[] products = {
                    new Product("p1","Laptop ASUS",15000000),
                    new Product("p2","Điện thoại iPhone",20000000),
                    new Product("p3","Tai nghe Sony",2000000),
                    new Product("p4","Bàn phím cơ",1500000)
            };
            sendJson(ex, 200, gson.toJson(products));
        });

        // Add to cart
        server.createContext("/cart/add", (ex) -> handleAddToCart(ex));

        // Remove 1 quantity from cart
        server.createContext("/cart/remove", (ex) -> handleRemoveFromCart(ex));

        // Get cart
        server.createContext("/cart", (ex) -> handleGetCart(ex));

        // Chat
        server.createContext("/chat", (ex) -> handleChat(ex));

        // Checkout
        server.createContext("/checkout", (ex) -> handleCheckout(ex));

        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
    }

    private static void handleAddToCart(HttpExchange ex) throws IOException {
        if(!"POST".equalsIgnoreCase(ex.getRequestMethod())) { send(ex,405,""); return; }
        String body = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        JsonObject obj = gson.fromJson(body, JsonObject.class);
        String userId = obj.get("userId").getAsString();
        String productId = obj.get("productId").getAsString();
        String name = obj.get("name").getAsString();
        double price = obj.get("price").getAsDouble();
        int quantity = obj.get("quantity").getAsInt();
        CartItem item = new CartItem(new Product(productId,name,price), quantity);
        cartService.addItem(userId,item);
        send(ex,200,"added");
    }

    private static void handleRemoveFromCart(HttpExchange ex) throws IOException {
        if(!"POST".equalsIgnoreCase(ex.getRequestMethod())) { send(ex,405,""); return; }
        String body = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        JsonObject obj = gson.fromJson(body, JsonObject.class);
        String userId = obj.get("userId").getAsString();
        String productId = obj.get("productId").getAsString();
        cartService.removeOneItem(userId, productId);
        send(ex,200,"removed");
    }

    private static void handleGetCart(HttpExchange ex) throws IOException {
        String query = ex.getRequestURI().getQuery();
        String userId = getQueryParam(query,"userId");
        if(userId==null){ send(ex,400,"userId required"); return; }
        Map<String, CartItem> cart = cartService.getCart(userId);
        sendJson(ex,200,gson.toJson(cart));
    }

    private static void handleChat(HttpExchange ex) throws IOException {
        if(!"POST".equalsIgnoreCase(ex.getRequestMethod())) { send(ex,405,""); return; }
        String body = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        JsonObject obj = gson.fromJson(body, JsonObject.class);
        String msg = obj.get("message").getAsString();
        chatService.sendMessage(msg);
        send(ex,200,"sent");
    }

    private static void handleCheckout(HttpExchange ex) throws IOException {
        if(!"POST".equalsIgnoreCase(ex.getRequestMethod())) { send(ex,405,""); return; }
        String body = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        JsonObject obj = gson.fromJson(body, JsonObject.class);
        String userId = obj.get("userId").getAsString();
        Map<String, CartItem> cart = cartService.getCart(userId);
        if(cart.isEmpty()){ send(ex,400,"Giỏ hàng trống"); return; }

        // Gửi đơn hàng lên RabbitMQ
        try {
            com.rabbitmq.client.Channel channel = bt.RabbitMQConnection.getConnection().createChannel();
            channel.queueDeclare("order_queue", false, false, false, null);
            channel.basicPublish("", "order_queue", null, gson.toJson(cart).getBytes());
            channel.close();
        } catch(Exception e){ e.printStackTrace(); }

        cartService.clearCart(userId);
        send(ex,200,"checkout success");
    }

    private static void send(HttpExchange ex,int status,String msg) throws IOException{
        byte[] b=msg.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().add("Content-Type","text/plain; charset=utf-8");
        ex.sendResponseHeaders(status,b.length);
        try(OutputStream os=ex.getResponseBody()){ os.write(b);}
    }

    private static void sendJson(HttpExchange ex,int status,String json) throws IOException{
        byte[] b=json.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().add("Content-Type","application/json; charset=utf-8");
        ex.sendResponseHeaders(status,b.length);
        try(OutputStream os=ex.getResponseBody()){ os.write(b);}
    }

    private static String getQueryParam(String q,String name){
        if(q==null) return null;
        for(String part:q.split("&")){
            String[] kv=part.split("=");
            if(kv.length==2 && kv[0].equals(name)) return kv[1];
        }
        return null;
    }
}
