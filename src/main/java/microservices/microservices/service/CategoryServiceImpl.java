package microservices.microservices.service;

import microservices.microservices.model.Category;
import microservices.microservices.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    // Constructor injection
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Create a new category
    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Update an existing category
    @Override
    public Category updateCategory(String id, Category category) {
        return categoryRepository.findById(id)
                .map(existing -> {
                    existing.setName(category.getName());
                    existing.setSlug(category.getSlug());
                    existing.setStatus(category.getStatus());
                    // ParentId removed as not used
                    return categoryRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    // Delete a category by id
    @Override
    public void deleteCategory(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    // Get a single category by id
    @Override
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    // Get all categories
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
