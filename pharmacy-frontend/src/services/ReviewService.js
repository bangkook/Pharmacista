import axios from 'axios';

const REVIEWS_BASE_URL = 'http://localhost:8088/reviews'; // Replace with your backend API base URL

const reviewService = {
  saveReview: async (review) => {
    try {
      const response = await axios.post(`${REVIEWS_BASE_URL}/save`, review);
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  getReviewsForProduct: async (productSN) => {
    try {
      const response = await axios.get(`${REVIEWS_BASE_URL}/product/${productSN}`);
      return response.data;
    } catch (error) {
      if (error.response && error.response.status === 404) {
        // Handle NOT_FOUND (404) error
        console.error('Reviews not found for product:', productSN);
        return [];  // Or return some default value
      } else {
        throw error;  // Rethrow other errors
      }
    }
  },
};

export default reviewService;