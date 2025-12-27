package ru.warfaric.redgmapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product_images")
public class ProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    // Путь или URL к изображению
    @Column(nullable = false, length = 1024)
    private String url;

    // Порядок отображения в карусели
    @Column(name = "sort_order")
    private Integer sortOrder;
}

