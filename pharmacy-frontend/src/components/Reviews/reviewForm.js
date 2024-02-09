import React, { useState, useEffect } from 'react';
import './reviewForm.css';
import '@fortawesome/fontawesome-free/css/all.min.css';
import reviewService from '../../services/ReviewService.js';
import CustomAlert from '../Alert/CustomAlert.js';
import ListofReviews from './listOfReviews.js';

const ReviewForm = (info) => {
  const [feedback, setFeedback] = useState({
    rating: '0',
    comment: '',
    productSN: info.productSN,
    userId: info.userId,
  });

  const [isModalOpen, setModalOpen] = useState(false);
  const [customAlert, setCustomAlert] = useState(null);

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

    if (feedback.rating === '0' && feedback.comment === '') {
      showAlert("The review is empty! Can't review");
    } else {
      try {
        await reviewService.saveReview(feedback);
        showAlert('Review saved successfully!');
        setFeedback({ rating: '0', comment: '', productSN: info.productSN, userId: info.userId });
        closeModal();
        handleReviewAdded();
      } catch (error) {
        showAlert('Error saving review');
      }
    }
  };

  const handleReviewAdded = async () => {
    try {
      const reviews = await reviewService.getReviewsForProduct(info.productSN);
      ListofReviews(info.productSN);
    } catch (error) {
      console.error('Error fetching reviews:', error);
    }
  };

  const showAlert = (message) => {
    setCustomAlert(<CustomAlert message={message} onClose={() => setCustomAlert(null)} />);
  };

  return (
    <div className="add-review-container">
      <button onClick={openModal}>Open Review Form</button>
      {isModalOpen && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
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
          </div>
        </div>
      )}

      {customAlert}
    </div>
  );
};

export default ReviewForm;
