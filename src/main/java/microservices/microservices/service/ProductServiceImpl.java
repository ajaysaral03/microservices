package microservices.microservices.service;

import lombok.RequiredArgsConstructor;
import microservices.microservices.dto.ProductDto;
import microservices.microservices.model.Product;
import microservices.microservices.model.Category;
import microservices.microservices.model.SubCategory;
import microservices.microservices.repository.ProductRepository;
import microservices.microservices.repository.CategoryRepository;
import microservices.microservices.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    // Convert Product to ProductDto with names
    private ProductDto toDto(Product product) {

        String categoryName = null;
        if (product.getCategoryId() != null) {
            categoryName = categoryRepository.findById(product.getCategoryId())
                    .map(Category::getName)
                    .orElse(null);
        }

        String subCategoryName = null;
        if (product.getSubCategoryId() != null) {
            subCategoryName = subCategoryRepository.findById(product.getSubCategoryId())
                    .map(SubCategory::getName)
                    .orElse(null);
        }

        return ProductDto.builder()
                .id(product.getId())
                .categoryId(product.getCategoryId())
                .categoryName(categoryName)        // add category name
                .subCategoryId(product.getSubCategoryId())
                .subCategoryName(subCategoryName)  // add subcategory name
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .image(product.getImage())
                .status(product.getStatus().name())
                .build();
    }

    // Convert ProductDto to Product entity
    private Product toEntity(ProductDto dto) {
        return Product.builder()
                .categoryId(dto.getCategoryId())
                .subCategoryId(dto.getSubCategoryId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .image(dto.getImage())
                .status(dto.getStatus() != null && dto.getStatus().equalsIgnoreCase("inactive") ?
                        Product.Status.inactive : Product.Status.active)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public ProductDto createProduct(ProductDto dto) {
        Product product = toEntity(dto);
        Product saved = productRepository.save(product);
        return toDto(saved);
    }

    @Override
    public ProductDto updateProduct(String id, ProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if (dto.getCategoryId() != null) product.setCategoryId(dto.getCategoryId());
        if (dto.getSubCategoryId() != null) product.setSubCategoryId(dto.getSubCategoryId());
        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        if (dto.getImage() != null) product.setImage(dto.getImage());
        if (dto.getStatus() != null)
            product.setStatus(dto.getStatus().equalsIgnoreCase("inactive") ?
                    Product.Status.inactive : Product.Status.active);

        product.setUpdatedAt(LocalDateTime.now());

        Product updated = productRepository.save(product);
        return toDto(updated);
    }

    @Override
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return toDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
