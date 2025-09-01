package microservices.microservices.repository;

import microservices.microservices.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    // No extra methods needed, MongoRepository gives all CRUD methods
}
