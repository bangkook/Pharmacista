import React, { useState, useEffect } from 'react';
import './listOfReviews.css';
import reviewService from '../../services/ReviewService.js';

const StarRating = ({ rating }) => {
    if(rating === 'ZERO_STAR'){
        rating = 0;
    }else if(rating === 'ONE_STAR'){
        rating = 1;
    }else if(rating === 'TWO_STARS'){
        rating = 2;
    }else if(rating === 'THREE_STARS'){
        rating = 3;
    }else if(rating === 'FOUR_STARS'){
        rating = 4;
    }else if(rating === 'FIVE_STARS'){
        rating = 5;
    }  
    console.log(`rating = ${rating}`);
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

    useEffect(() => {
        const fetchReviews = async () => {
          try {
            console.log(`productSn = ${productSN}`)
            const productReviews = await reviewService.getReviewsForProduct(productSN);
            setReviews(productReviews);
          } catch (error) {
            console.error('Error fetching reviews:', error);
          }
        };
    
        fetchReviews();
      }, [productSN]);
    
    return (
    <div className="review-container">
        <h1>Product Reviews</h1>
        {reviews.map((review) => (
        <div key = {review.id} className="reviewList">
            <h5>
                Comment:
                <p>{review.comment}</p>
            </h5>
            <h5>
            Rating:
                <p><StarRating className= "const-stars" rating={review.rating} /></p>
            </h5>
            <h5>
                User: 
                <p>
                    {review.userId} {review.username}
                </p>
            </h5> 
            {/* This will be changed */}
        </div>
        ))}
    </div>
    );
};
    
export default ListofReviews;