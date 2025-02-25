package com.project.shopapp.service;

import com.project.shopapp.dto.request.ProductDTO;
import com.project.shopapp.dto.request.ProductImageDTO;
import com.project.shopapp.model.Product;
import com.project.shopapp.model.ProductImage;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO productDTO);

    Product getProductById(Long id);

    List<Product> getAllProducts(int page, int limit);

    void updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);

    boolean existsByName(String name);

    ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO);
}
