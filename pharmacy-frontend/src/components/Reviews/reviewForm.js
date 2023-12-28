import React, { useState, useEffect } from 'react';
import './reviewForm.css'; // Import the CSS file
import '@fortawesome/fontawesome-free/css/all.min.css';
import reviewService from '../../services/ReviewService.js';
// import CustomAlert from './Alert/CustomAlert';


const ReviewForm = (info) => {
  const [feedback, setFeedback] = useState({
    rating: '0',
    comment: '',
    productSN: info.productSN,
    userId: info.userId
  });

  const fetchReviewsForProduct = async (productSN) => {
    try {
      const reviews = await reviewService.getReviewsForProduct(info.productSN);
      setReviews(reviews);
      console.log('Fetched reviews:', reviews);
    } catch (error) {
      console.error('Error fetching reviews:', error);
    }
  };
  
  const handleReviewAdded = () => {
    // Call the function to fetch reviews after a new review is added
    fetchReviewsForProduct(info.productSN);
  };

  const [isModalOpen, setModalOpen] = useState(false);
  const [reviews, setReviews] = useState([]);
  // const [customAlert, setCustomAlert] = useState(null);
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFeedback((prevFeedback) => ({ ...prevFeedback, [name]: value }));
  };

  const openModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };

  const handleStarClick = (selectedRating) => {
    setFeedback((prevFeedback) => ({
        ...prevFeedback,
        rating: prevFeedback.rating === selectedRating ? selectedRating - 1 : selectedRating,
      }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Submitted feedback:', feedback);
    if(feedback.rating === '0' && feedback.comment ===''){
      alert("The review is empty! Can't review");
    } else {
      try {
        await reviewService.saveReview(feedback);
        alert('Review saved successfully!');
        feedback.comment = '';
        feedback.rating = '';
        closeModal();

        handleReviewAdded();
      } catch (error) {
        alert('Error saving review');
      }
    }
  };

  return (
    <div className="add-review-container">
      {!isModalOpen && <button onClick={openModal}>Open Feedback Form</button>}
      {isModalOpen && (
        <div className="modal-overlay">
          <div className="modal-content">
            <div className="feedback-form">
              <h2>Provide Your Feedback</h2>
              <form onSubmit={handleSubmit}>
                <div className="star-rating">
                  <label>Rating:</label>
                  <div>
                    {[...Array(5)].map((_, index) => (
                      <span
                        key={index}
                        className={`star ${index + 1 <= feedback.rating ? 'active' : ''}`}
                        onClick={() => handleStarClick(index + 1)}
                      >
                        <i className="fas fa-star"></i>
                      </span>
                    ))}
                  </div>
                </div>

                <div>
                  <label htmlFor="comment">Comment:</label>
                  <textarea
                    id="comment"
                    name="comment"
                    value={feedback.comment}
                    onChange={handleInputChange}
                  />
                </div>

                <button type="submit">Submit Feedback</button>
              </form>
            </div>
            <button onClick={closeModal}>Close Modal</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default ReviewForm;