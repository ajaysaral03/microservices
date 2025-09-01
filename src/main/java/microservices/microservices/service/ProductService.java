package microservices.microservices.service;

import microservices.microservices.dto.ProductDto;
import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto dto);
    ProductDto updateProduct(String id, ProductDto dto);
    void deleteProduct(String id);
    ProductDto getProductById(String id);
    List<ProductDto> getAllProducts();
}
