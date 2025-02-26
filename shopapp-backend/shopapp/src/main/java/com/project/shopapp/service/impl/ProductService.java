package com.project.shopapp.service.impl;

import com.project.shopapp.dto.request.ProductDTO;
import com.project.shopapp.exception.ResourceNotFoundException;
import com.project.shopapp.model.Category;
import com.project.shopapp.model.Product;
import com.project.shopapp.repository.CategoryRepository;
import com.project.shopapp.repository.ProductRepository;
import com.project.shopapp.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Long createProduct(ProductDTO productDTO) {
        Category existsCate = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(()
                -> new ResourceNotFoundException("cate id of product not found"));
        Product product = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .category(existsCate)
                .build();
        productRepository.save(product);
        log.info("product has saved");
        return product.getId();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product not found"));
    }

    @Override
    public List<Product> getAllProducts(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Product> products = productRepository.findAll(pageable);
        return products.stream().map(product -> Product.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .category(product.getCategory())
                .build()).toList();
    }

    @Override
    public void updateProduct(Long id, ProductDTO productDTO) {
        Product product = getProductById(id);
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setThumbnail(productDTO.getThumbnail());
        product.setDescription(productDTO.getDescription());
        productRepository.save(product);
        log.info("product updated , proId = {}", id);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
        log.info("product deleted, proId =  {}", id);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
}
