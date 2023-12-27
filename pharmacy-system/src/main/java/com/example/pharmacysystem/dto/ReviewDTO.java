package com.example.pharmacysystem.dto;

import com.example.pharmacysystem.model.Review;
import com.example.pharmacysystem.model.User;

import java.sql.Date;
public class ReviewDTO {
    private int reviewId;
    private int userId;
    private String productSN;
    private Review.Rate rating;
    private String comment;
    private Date reviewDate;
    private String username; // Additional attribute from the User class

    public ReviewDTO() {
    }

    public ReviewDTO(int reviewId, int userId, String productSN, Review.Rate rating, String comment, Date reviewDate, String username) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.productSN = productSN;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.username = username;
    }

    public int getReviewId() {
        return reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public String getProductSN() {
        return productSN;
    }

    public Review.Rate getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public String getUsername() {
        return username;
    }
}
