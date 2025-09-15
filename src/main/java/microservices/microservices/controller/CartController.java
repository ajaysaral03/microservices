package microservices.microservices.controller;

import microservices.microservices.model.Cart;
import microservices.microservices.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin(origins = "*") // React ke liye CORS allow
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ✅ Add to cart (JSON body accept karega)
    @PostMapping("/add")
    public Cart addToCart(@RequestBody Cart cartRequest) {
        return cartService.addToCart(
                cartRequest.getUserId(),
                cartRequest.getProductId(),
                cartRequest.getQuantity()
        );
    }

    // ✅ Get user cart
    @GetMapping("/{userId}")
    public List<Cart> getUserCart(@PathVariable String userId) {
        return cartService.getUserCart(userId);
    }

    // ✅ Remove cart item
    @DeleteMapping("/{cartId}")
    public void removeFromCart(@PathVariable String cartId) {
        cartService.removeFromCart(cartId);
    }
}
