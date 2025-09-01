package microservices.microservices.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDto {
    private String id;
    private String categoryId;
    private String categoryName;
    private String subCategoryId;
    private String subCategoryName;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String image;
    private String status;

}
