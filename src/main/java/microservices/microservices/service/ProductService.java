package microservices.microservices.service;

import microservices.microservices.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    // Create Product with file
    ProductDto createProductWithFile(ProductDto dto, MultipartFile file);

    // Update Product with optional file
    ProductDto updateProductWithFile(String id, ProductDto dto, MultipartFile file);

    // Delete Product
    void deleteProduct(String id);

    // Get single Product
    ProductDto getProductById(String id);

    // Get all Products
    List<ProductDto> getAllProducts();
    long getProductsCount();
}
