package bt;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConnection {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("localhost");          // ✔ ĐÚNG
                factory.setPort(5672);                 // ✔ ĐÚNG
                factory.setUsername("guest");          // ✔ ĐÚNG
                factory.setPassword("guest");          // ✔ ĐÚNG

                factory.setAutomaticRecoveryEnabled(true);
                factory.setNetworkRecoveryInterval(3000);

                connection = factory.newConnection();
                System.out.println("Kết nối RabbitMQ thành công!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
