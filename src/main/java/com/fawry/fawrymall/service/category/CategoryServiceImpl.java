package com.fawry.fawrymall.service.category;

import com.fawry.fawrymall.entity.Category;
import com.fawry.fawrymall.repository.CategoryRepository;
import com.fawry.fawrymall.util.MyLogger;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Category Not Found")
                );
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category category) {
        Category newCategory = categoryRepository.save(category);
        MyLogger.getInstance().info("Category " + newCategory.getName() + " created");
        return newCategory;
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category oldCategory = getCategoryById(id);
        oldCategory.setName(category.getName());
        MyLogger.getInstance().info("Category " + category.getName() + " updated to " + oldCategory.getName());
        return categoryRepository.save(oldCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
        MyLogger.getInstance().info("Category " + category.getName() + " deleted");
    }

}
