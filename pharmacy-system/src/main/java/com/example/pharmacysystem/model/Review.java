package com.example.pharmacysystem.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    private int userId;

    private String productSN;

    private Rate rating;

    private String comment;

    private Date reviewDate;

    public enum Rate {
        ZERO_STAR, ONE_STAR, TWO_STARS, THREE_STARS, FOUR_STARS, FIVE_STARS
    }


    public Review() {
    }

    public Review(int userId, String productSN, Rate rating, String comment, Date reviewDate) {
        this.userId = userId;
        this.productSN = productSN;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public int getUserId() {
        return userId;
    }

    public String getProductSN() {
        return productSN;
    }

    public Rate getRating() {
        return rating;
    }

    public void setRating(Rate rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getReviewDate() {
        return reviewDate;
    }


    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public int getReviewId() {
        return reviewId;
    }
}