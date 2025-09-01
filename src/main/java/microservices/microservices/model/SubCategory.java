package microservices.microservices.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "subcategories")
public class SubCategory {
    @Id
    private String id;

    @DBRef
    private Category category;

    private String name;
    private String slug;
    private String location;
    private String page;
    private Integer status; // 1 = active, 0 = inactive
}
