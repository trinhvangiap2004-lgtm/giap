package Shop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CartService {
    private final Map<String, Map<String, CartItem>> carts = new ConcurrentHashMap<>();

    public Map<String, CartItem> getCart(String userId){
        return carts.getOrDefault(userId, new ConcurrentHashMap<>());
    }

    public void addItem(String userId, CartItem item){
        carts.computeIfAbsent(userId,k->new ConcurrentHashMap<>());
        Map<String, CartItem> cart = carts.get(userId);
        if(cart.containsKey(item.product.id)){
            cart.get(item.product.id).quantity += item.quantity;
        } else {
            cart.put(item.product.id, item);
        }
    }

    public void removeOneItem(String userId, String productId){
        Map<String, CartItem> cart = carts.get(userId);
        if(cart!=null){
            if(cart.containsKey(productId)){
                CartItem it = cart.get(productId);
                it.quantity--;
                if(it.quantity<=0) cart.remove(productId);
            }
        }
    }

    public void clearCart(String userId){
        Map<String, CartItem> cart = carts.get(userId);
        if(cart!=null) cart.clear();
    }
}
