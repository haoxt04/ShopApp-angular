package com.project.shopapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity
@Table(name = "products")
@Data
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private float price;
    private String thumbnail;
    private String description;

    @ManyToOne

    @JoinColumn(name = "category_id")
    private Category category;
}
