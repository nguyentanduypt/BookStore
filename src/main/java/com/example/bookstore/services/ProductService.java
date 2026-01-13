package com.example.bookstore.services;

import com.example.bookstore.models.Pagination;
import com.example.bookstore.models.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    Pagination<ProductDTO> getAllProducts(int page, int size);
    ProductDTO getProductById(String id);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(ProductDTO productDTO, String id);
    void deleteProduct(String id);
    ProductDTO updateTotalRating (String productId);
    Pagination<ProductDTO> getProductsByCategoryName(String categoryName, int page, int size);
    List<ProductDTO> getProductByName(String name);
    List<ProductDTO> getLatestProducts();
    List<ProductDTO> getOldestProducts();
}
