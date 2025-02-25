package com.project.shopapp.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Table(name = "roles")
@Data
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}
