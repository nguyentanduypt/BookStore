package com.example.bookstore.controllers;


import com.example.bookstore.models.APIResponse;
import com.example.bookstore.models.Pagination;
import com.example.bookstore.models.dto.ProductDTO;
import com.example.bookstore.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

//xem product theo category

//lấy tất cả books
    @GetMapping
    public ResponseEntity<APIResponse<Pagination<ProductDTO>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pagination<ProductDTO> products = productService.getAllProducts(page, size);
        APIResponse<Pagination<ProductDTO>> response = new APIResponse<>(
                "success",
                "Products retrieved successfully",
                products,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

//    //lấy tất cả products theo categoryName
    @GetMapping("/categoryName")
    public ResponseEntity<APIResponse<Pagination<ProductDTO>>> getProductsByCategoryName(
            @RequestParam String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pagination<ProductDTO> products = productService.getProductsByCategoryName(categoryName,page, size);
        APIResponse<Pagination<ProductDTO>> response = new APIResponse<>(
                "success",
                "Products retrieved successfully",
                products,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

//lấy product theo name
@GetMapping("/search")
public ResponseEntity<APIResponse<List<ProductDTO>>> searchProduct (@RequestParam String name){
    List<ProductDTO> productDTOList = productService.getProductByName(name);
    APIResponse<List<ProductDTO>> response = new APIResponse<>(
            "success",
            "Product retrieved successfully",
            productDTOList,
            null,
            LocalDateTime.now()
    );
    return ResponseEntity.status(200).body(response);
}
//lấy product theo Id
@GetMapping("/{id}")
public ResponseEntity<APIResponse<ProductDTO>> getProductById(@PathVariable String id) {
    ProductDTO product = productService.getProductById(id);
    APIResponse<ProductDTO> response = new APIResponse<>(
            "success",
            "Product retrieved successfully",
            product,
            null,
            LocalDateTime.now()
    );
    return ResponseEntity.ok(response);
}
//tạo book
@PostMapping
public ResponseEntity<APIResponse<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
    ProductDTO created = productService.createProduct(productDTO);
    APIResponse<ProductDTO> response = new APIResponse<>(
            "success",
            "product created successfully",
            created,
            null,
            LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ProductDTO>> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) {
        ProductDTO updated = productService.updateProduct(productDTO, id);
        APIResponse<ProductDTO> response = new APIResponse<>(
                "success",
                "Product updated successfully",
                updated,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "Product deleted successfully",
                null,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
    //lấy product mới nhất
    @GetMapping("/latest")
    public ResponseEntity<APIResponse<List<ProductDTO>>> getLatestProducts() {
        List<ProductDTO> product = productService.getLatestProducts();
        APIResponse<List<ProductDTO>> response = new APIResponse<>(
                "success",
                "Product retrieved successfully",
                product,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //lấy product cũ nhất
    @GetMapping("/oldest")
    public ResponseEntity<APIResponse<List<ProductDTO>>> getOldestProducts() {
        List<ProductDTO> product = productService.getOldestProducts();
        APIResponse<List<ProductDTO>> response = new APIResponse<>(
                "success",
                "Product retrieved successfully",
                product,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

}
