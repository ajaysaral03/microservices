package microservices.microservices.service;

import microservices.microservices.model.Cart;

import java.util.List;

public interface CartService {

    Cart addToCart(String userId, String productId, int quantity);

    List<Cart> getUserCart(String userId);

    void removeFromCart(String cartId);
}
