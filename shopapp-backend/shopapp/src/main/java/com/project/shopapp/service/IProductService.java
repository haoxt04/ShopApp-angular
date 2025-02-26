package com.project.shopapp.service;

import com.project.shopapp.dto.request.ProductDTO;
import com.project.shopapp.model.Product;

import java.util.List;

public interface IProductService {
    Long createProduct(ProductDTO productDTO);

    Product getProductById(Long id);

    List<Product> getAllProducts(int page, int limit);

    void updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);

    boolean existsByName(String name);
}
