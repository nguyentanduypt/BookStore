package com.example.bookstore.repositories;

import com.example.bookstore.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    // Tìm sản phẩm theo tên (ví dụ từ Course)
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findProductByName(@Param("name") String name);
    //tim sp theo category
    @Query("SELECT p FROM Product p JOIN p.category cat WHERE cat.name = :categoryName")
    Page<Product> findProductByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);
}
