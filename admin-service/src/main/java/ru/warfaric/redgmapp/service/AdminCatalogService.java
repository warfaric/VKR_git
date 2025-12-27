package ru.warfaric.redgmapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.warfaric.redgmapp.dto.admin.CategoryForm;
import ru.warfaric.redgmapp.dto.admin.ProductForm;
import ru.warfaric.redgmapp.dto.admin.SubcategoryForm;
import ru.warfaric.redgmapp.entity.CategoryEntity;
import ru.warfaric.redgmapp.entity.ProductEntity;
import ru.warfaric.redgmapp.entity.SubcategoryEntity;
import ru.warfaric.redgmapp.repository.CategoryRepository;
import ru.warfaric.redgmapp.repository.ProductRepository;
import ru.warfaric.redgmapp.repository.SubcategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCatalogService {

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<CategoryEntity> listCategories() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public CategoryEntity createCategory(CategoryForm form) {
        CategoryEntity category = new CategoryEntity();
        category.setName(form.getName());
        return categoryRepository.save(category);
    }

    public CategoryEntity updateCategory(Long id, CategoryForm form) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Категория не найдена"));
        category.setName(form.getName());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<SubcategoryEntity> listSubcategories(Long categoryId) {
        return subcategoryRepository.findAllByCategoryId(categoryId);
    }

    public SubcategoryEntity createSubcategory(SubcategoryForm form) {
        CategoryEntity category = categoryRepository.findById(form.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Категория не найдена"));
        SubcategoryEntity subcategory = new SubcategoryEntity();
        subcategory.setName(form.getName());
        subcategory.setCategory(category);
        return subcategoryRepository.save(subcategory);
    }

    public SubcategoryEntity updateSubcategory(Long id, SubcategoryForm form) {
        SubcategoryEntity subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Подкаталог не найден"));
        if (!subcategory.getCategory().getId().equals(form.getCategoryId())) {
            CategoryEntity category = categoryRepository.findById(form.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Категория не найдена"));
            subcategory.setCategory(category);
        }
        subcategory.setName(form.getName());
        return subcategoryRepository.save(subcategory);
    }

    public void deleteSubcategory(Long id) {
        subcategoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> listProducts(Long subcategoryId) {
        return productRepository.findAllBySubcategoryId(subcategoryId);
    }

    public ProductEntity createProduct(ProductForm form) {
        SubcategoryEntity subcategory = subcategoryRepository.findById(form.getSubcategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Подкаталог не найден"));
        ProductEntity product = new ProductEntity();
        product.setName(form.getName());
        product.setPrice(form.getPrice());
        product.setWeight(form.getWeight());
        product.setSubcategory(subcategory);
        return productRepository.save(product);
    }

    public ProductEntity updateProduct(Long id, ProductForm form) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден"));
        if (!product.getSubcategory().getId().equals(form.getSubcategoryId())) {
            SubcategoryEntity subcategory = subcategoryRepository.findById(form.getSubcategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Подкаталог не найден"));
            product.setSubcategory(subcategory);
        }
        product.setName(form.getName());
        product.setWeight(form.getWeight());
        product.setPrice(form.getPrice());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public CategoryEntity getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Категория не найдена"));
    }

    @Transactional(readOnly = true)
    public SubcategoryEntity getSubcategory(Long id) {
        return subcategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Подкаталог не найден"));
    }

    @Transactional(readOnly = true)
    public ProductEntity getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден"));
    }
}
