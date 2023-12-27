package com.example.pharmacysystem.repository;

import com.example.pharmacysystem.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>  {
    List<Review> findByProductSN(String productSN);

    Review save(Review review);
}