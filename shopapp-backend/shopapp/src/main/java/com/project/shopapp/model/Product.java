package com.project.shopapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private float price;
    private String thumbnail;
    private String description;
    @Column(name = "created_at")
    private Date createAt;
    @Column(name = "updated_at")
    private Date updateAt;

    @ManyToOne
    @Column(name = "category_id")
    private Category categoryId;
}
