package microservices.microservices.repository;

import microservices.microservices.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    // Find carts by userId
    List<Cart> findByUserId(String userId);

    // Find cart by userId and productId (useful for checking duplicates)
    Cart findByUserIdAndProductId(String userId, String productId);
}
