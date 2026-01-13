package com.example.bookstore.repositories;

import com.example.bookstore.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    @Query("SELECT SUM(r.rating) FROM Review r WHERE r.product.id = :productId")
    int sumRatingByProductId(@Param("productId") UUID productId);

    int countReviewsByProductId(@Param("productId") UUID productId);

}
