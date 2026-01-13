package com.example.bookstore.services.impl;

import com.example.bookstore.models.Customer;
import com.example.bookstore.models.Product;
import com.example.bookstore.models.Review;
import com.example.bookstore.models.dto.ReviewDTO;
import com.example.bookstore.repositories.CustomerRepository;
import com.example.bookstore.repositories.ProductRepository;
import com.example.bookstore.repositories.ReviewRepository;
import com.example.bookstore.services.ProductService;
import com.example.bookstore.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews =reviewRepository.findAll();
        return reviews.stream().map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Review> optionalReview = reviewRepository.findById(uuid);
        return optionalReview.map(review -> modelMapper.map(review, ReviewDTO.class)).orElse(null);
    }

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = modelMapper.map(reviewDTO, Review.class);
        UUID customer_Id=UUID.fromString(reviewDTO.getCustomerId());
        UUID product_Id=UUID.fromString(reviewDTO.getProductId());
        Product product = productRepository.findById(product_Id).orElse(null);
        Customer customer = customerRepository.findById(customer_Id).orElse(null);
        review.setComment(reviewDTO.getComment());
        review.setCustomer(customer);
        review.setProduct(product);
        Review saved = reviewRepository.save(review);
        return modelMapper.map(saved, ReviewDTO.class);
    }

    @Override
    public ReviewDTO updateReview(ReviewDTO reviewDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Review existingReview = reviewRepository.findById(uuid).orElse(null);
        existingReview.setComment(reviewDTO.getComment());

        return modelMapper.map(reviewRepository.save(existingReview), ReviewDTO.class);
    }

    @Override
    public void deleteReview(String id) {
    UUID uuid = UUID.fromString(id);
    Review existingReview = reviewRepository.findById(uuid).orElse(null);
    reviewRepository.delete(existingReview);
    }
}
