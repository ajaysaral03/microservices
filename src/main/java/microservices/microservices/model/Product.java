package microservices.microservices.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "products")  // MongoDB collection
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    private String id;   // MongoDB IDs are Strings (ObjectId)

    @Field("category_id")
    private String categoryId;   // Store category reference ID as String

    @Field("subcategory_id")
    private String subCategoryId;

    private String name;
    private String description;
    private Double price;
    private Integer stock;

    private String image;

    private Status status = Status.active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Status {
        active, inactive
    }
}
