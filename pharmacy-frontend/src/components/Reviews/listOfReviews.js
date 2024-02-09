import React, { useState, useEffect } from 'react';
import './listOfReviews.css';
import reviewService from '../../services/ReviewService.js';

const StarRating = ({ rating }) => {
  if (rating === 'ZERO_STAR') {
    rating = 0;
  } else if (rating === 'ONE_STAR') {
    rating = 1;
  } else if (rating === 'TWO_STARS') {
    rating = 2;
  } else if (rating === 'THREE_STARS') {
    rating = 3;
  } else if (rating === 'FOUR_STARS') {
    rating = 4;
  } else if (rating === 'FIVE_STARS') {
    rating = 5;
  }

  return (
    <div>
      {[...Array(5)].map((_, index) => (
        <span
          key={index}
          className={`const-star-rating ${index + 1 <= rating ? 'active' : ''}`}
        >
          <i className="fas fa-star"></i>
        </span>
      ))}
    </div>
  );
};

const ListofReviews = ({ productSN }) => {

  const [reviews, setReviews] = useState([]);

    const fetchReviews = async () => {
      try {
        const productReviews = await reviewService.getReviewsForProduct(productSN);
        setReviews(productReviews);
      } catch (error) {
        console.error('Error fetching reviews:', error);
      }
    };

    fetchReviews();


  return (
    <div className="review-container">
      {reviews.map((review) => (
        <div key={review.id} className="reviewList">
          <StarRating rating={review.rating} />
          <div className="review-content">
            <h5 className="comment-heading">Comment:</h5>
            <p className="comment">{review.comment}</p>
            <h5 className="user-heading">User:</h5>
            <p className="user">{review.username}</p>
          </div>
        </div>
      ))}
    </div>
  );
};

export default ListofReviews;