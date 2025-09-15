package microservices.microservices.service;

import microservices.microservices.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category updateCategory(String id, Category category);
    void deleteCategory(String id);
    Category getCategoryById(String id);
    List<Category> getAllCategories();
    long getCategoriesCount();
}
