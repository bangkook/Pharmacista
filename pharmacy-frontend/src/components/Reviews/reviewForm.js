import React, { useState } from 'react';
import './reviewForm.css'; // Import the CSS file
import '@fortawesome/fontawesome-free/css/all.min.css';
import reviewService from '../../services/ReviewService.js';
 

const ReviewForm = (productSN, userId) => {
  const [feedback, setFeedback] = useState({
    rating: '0',
    comment: '',
    productSN: productSN,
    userId: userId
  });

  const [isModalOpen, setModalOpen] = useState(false);

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
      } catch (error) {
        alert('Error saving review:', error);
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