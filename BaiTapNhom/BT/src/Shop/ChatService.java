package Shop;

public class ChatService {
    public void sendMessage(String msg){
        try{
            com.rabbitmq.client.Channel channel = bt.RabbitMQConnection.getConnection().createChannel();
            channel.queueDeclare("chat_queue",false,false,false,null);
            channel.basicPublish("", "chat_queue", null, msg.getBytes());
            channel.close();
        }catch(Exception e){ e.printStackTrace(); }
    }
}
