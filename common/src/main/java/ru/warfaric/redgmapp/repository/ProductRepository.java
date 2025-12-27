package ru.warfaric.redgmapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.warfaric.redgmapp.entity.ProductEntity;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllBySubcategoryId(Long subcategoryId);
}

