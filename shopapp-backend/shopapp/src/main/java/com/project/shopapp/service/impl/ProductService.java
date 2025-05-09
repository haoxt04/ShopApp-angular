package com.project.shopapp.service.impl;
import com.project.shopapp.dto.request.ProductDTO;
import com.project.shopapp.dto.request.ProductImageDTO;
import com.project.shopapp.dto.response.ProductDetailResponse;
import com.project.shopapp.dto.response.ProductListResponse;
import com.project.shopapp.exception.InvalidParamException;
import com.project.shopapp.exception.ResourceNotFoundException;
import com.project.shopapp.model.Category;
import com.project.shopapp.model.Product;
import com.project.shopapp.model.ProductImage;
import com.project.shopapp.repository.CategoryRepository;
import com.project.shopapp.repository.ProductImageRepository;
import com.project.shopapp.repository.ProductRepository;
import com.project.shopapp.service.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) {
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
        return product;
    }

    @Override
    public ProductDetailResponse getProduct(Long id) {
        Product product = getProductById(id);
        return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .thumbnail(product.getThumbnail())
                .categoryId(product.getCategory().getId())
                .build();
    }

    public Product getProductWithId(Long id) {
        Optional<Product> optionalProduct = productRepository.getDetailProduct(id);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new ResourceNotFoundException("Cannot find product with id =" + id);
    }


    @Override
    public ProductListResponse getAllProducts(String keyword, Long cateId, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Sort.by("id").ascending();

        Page<Product> productsPage = productRepository.searchProducts(cateId, keyword, pageable);

        List<ProductDetailResponse> productList = productsPage.stream().map(product -> ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build()).toList();
        return ProductListResponse.builder()
                .products(productList)
                .totalPages(productsPage.getTotalPages())
                .build();
    }

    @Override
    @Transactional
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
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
        log.info("product deleted, proId =  {}", id);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) {
        Product existsProduct = getProductById(productId);

        ProductImage newProductImage = ProductImage.builder()
                .product(existsProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        // không cho insert quá 5 ảnh
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException("Number of image must be less than " + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }

    @Override
    public List<Product> findProductByIds(List<Long> productIds) {
        return productRepository.findProductByIds(productIds);
    }

    private Product getProductById(Long userId) {
        return productRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("product not found"));
    }
}
