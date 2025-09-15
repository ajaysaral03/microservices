package microservices.microservices.service.impl;

import microservices.microservices.model.Cart;
import microservices.microservices.repository.CartRepository;
import microservices.microservices.service.CartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart addToCart(String userId, String productId, int quantity) {
        Cart existingCart = cartRepository.findByUserIdAndProductId(userId, productId);

        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            return cartRepository.save(existingCart);
        }

        Cart newCart = Cart.builder()
                .userId(userId)
                .productId(productId)
                .quantity(quantity > 0 ? quantity : 1)
                .createdAt(LocalDateTime.now())
                .build();

        return cartRepository.save(newCart);
    }

    @Override
    public List<Cart> getUserCart(String userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public void removeFromCart(String cartId) {
        cartRepository.deleteById(cartId);
    }
}
