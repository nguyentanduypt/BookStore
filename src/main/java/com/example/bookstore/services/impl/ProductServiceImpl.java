package com.example.bookstore.services.impl;

import com.example.bookstore.enums.ProductStatus;
import com.example.bookstore.models.Category;
import com.example.bookstore.models.Pagination;
import com.example.bookstore.models.Product;
import com.example.bookstore.models.dto.ProductDTO;
import com.example.bookstore.repositories.CategoryRepository;
import com.example.bookstore.repositories.ProductRepository;
import com.example.bookstore.repositories.ReviewRepository;
import com.example.bookstore.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;


    @Override
    public Pagination<ProductDTO> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPages = productRepository.findAll(pageable);

        List<ProductDTO> productDTOS = productPages.getContent().stream()
                .map(course -> modelMapper.map(course, ProductDTO.class) )
                .collect(Collectors.toList());

        Pagination<ProductDTO> pagination = new Pagination<>();
        pagination.setSize(productPages.getNumber());
        pagination.setSize(productPages.getSize());
        pagination.setTotalPages(productPages.getTotalPages());
        pagination.setTotalElements(productPages.getTotalElements());
        pagination.setContent(productDTOS);

        return pagination;
    }

    @Override
    public ProductDTO getProductById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Product> optionalProduct = productRepository.findById(uuid);
        ProductDTO productDTO = optionalProduct.map(product -> modelMapper.map(product, ProductDTO.class)).orElse(null);

        return productDTO;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class); // Ánh xạ cơ bản


        Category category = categoryRepository.findByName(productDTO.getCategoryName());
        // Ánh xạ từng trường thủ công
        product.setTitle(productDTO.getTitle());
        product.setCategory(category);
        product.setAuthor(productDTO.getAuthor());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setImage(productDTO.getImage());
        // Đặt các trường mặc định và trạng thái
        product.setStatus(ProductStatus.ACTIVE); // Giả sử luôn ACTIVE khi tạo mới
        product.setCreatedTime(Instant.now());
        product.setUpdatedTime(Instant.now());
        // updatedBy nếu có, cần một cơ chế để lấy ID người dùng hiện tại
        product.setTotalReviews(0);
        product.setAverageRating(0.0);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Product existingProduct = productRepository.findById(uuid).orElse(null);
        if (existingProduct == null) {
            return null;
        }
        existingProduct.setTitle(productDTO.getTitle());
        existingProduct.setAuthor(productDTO.getAuthor());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setImage(productDTO.getImage());

        // Cập nhật Category nếu có categoryName
        Category category = categoryRepository.findByName(productDTO.getCategoryName());
        // Cập nhật trạng thái
        if (productDTO.getStatus() != null) {
            existingProduct.setStatus(ProductStatus.valueOf(productDTO.getStatus()));
        }
        existingProduct.setUpdatedTime(Instant.now());
        existingProduct.setTotalReviews(productDTO.getTotalReviews());
        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public void deleteProduct(String id) {
        UUID uuid = UUID.fromString(id);
        Product existingCourse = productRepository.findById(uuid).orElse(null);
        productRepository.delete(existingCourse);
    }

    @Override
    public ProductDTO updateTotalRating(String productId) {

        UUID productUUID = UUID.fromString(productId);
        Product product = productRepository.findById(productUUID).orElse(null);
        int totalRating = reviewRepository.sumRatingByProductId(productUUID);
        int totalReviews = reviewRepository.countReviewsByProductId(productUUID);
        product.setTotalReviews(totalReviews);
        product.setAverageRating(totalReviews == 0 ? 0.0 : (double) totalRating / totalReviews);
        productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public Pagination<ProductDTO> getProductsByCategoryName(String categoryName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findProductByCategoryName(categoryName, pageable);

        List<ProductDTO> productDTOS = productPage.getContent().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class) )
                .collect(Collectors.toList());

        Pagination<ProductDTO> pagination = new Pagination<>();
        pagination.setSize(productPage.getNumber());
        pagination.setSize(productPage.getSize());
        pagination.setTotalPages(productPage.getTotalPages());
        pagination.setTotalElements(productPage.getTotalElements());
        pagination.setContent(productDTOS);

        return pagination;
    }

    @Override
    public List<ProductDTO> getProductByName(String name) {
        List<Product> courseList = productRepository.findProductByName(name);
        return courseList.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class) )
                .collect(Collectors.toList());
    }


    @Override
    public List<ProductDTO> getLatestProducts() {
        List<Product> productList = productRepository.findAll(Sort.by("createdTime").descending());
        return productList.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class) )
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getOldestProducts() {
        List<Product> productList = productRepository.findAll(Sort.by("createdTime").ascending());
        return productList.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class) )
                .collect(Collectors.toList());
    }
}
