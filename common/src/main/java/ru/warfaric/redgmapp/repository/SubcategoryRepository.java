package ru.warfaric.redgmapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.warfaric.redgmapp.entity.SubcategoryEntity;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Long> {
    List<SubcategoryEntity> findAllByCategoryId(Long categoryId);

    @EntityGraph(attributePaths = "products")
    Optional<SubcategoryEntity> findWithProductsById(Long id);
}
