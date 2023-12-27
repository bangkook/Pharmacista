package com.example.pharmacysystem.controllers;

import com.example.pharmacysystem.model.Review;
import com.example.pharmacysystem.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ReviewService reviewService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void saveReview_Success() throws Exception {
        // Mock data
        Date reviewDate = Date.valueOf(LocalDate.now());
        Review review = new Review(1, "123456789123456858", Review.Rate.FOUR_STARS, "Very good product!", reviewDate);

        // Mock the service behavior
        when(reviewService.saveReview(any(Review.class))).thenReturn(review);

        // Performing the request
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/reviews/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isOk())
                .andReturn();

        // Verifying the response
        MockHttpServletResponse response = res.getResponse();
        assertEquals("Review saved Successfully!", response.getContentAsString());

        // Verifying that the service method was called
        verify(reviewService, times(1)).saveReview(any(Review.class));
    }

    @Test
    public void saveReview_Failure() throws Exception {
        // Mock data
        Date reviewDate = Date.valueOf(LocalDate.now());
        Review review = new Review(1, "123456789123456858", Review.Rate.FOUR_STARS, "Very good product!", reviewDate);

        // Mock the service behavior to simulate failure
        when(reviewService.saveReview(any(Review.class))).thenThrow(new RuntimeException("Failed to save review"));

        // Performing the request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/reviews/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isInternalServerError())
                .andReturn();

        // Verifying the response
        MockHttpServletResponse response = result.getResponse();
        assertEquals("Couldn't save review", response.getContentAsString());

        // Verifying that the service method was called
        verify(reviewService, times(1)).saveReview(any(Review.class));
    }

    private static List<Review> getReviewsByProductSN(String productSN, Date reviewDate, String anotherProductSN) {
        Review r1 = new Review(1, productSN, Review.Rate.FOUR_STARS, "Very good product!", reviewDate);
        Review r2 = new Review(2, anotherProductSN, Review.Rate.FOUR_STARS, "Very good product!", reviewDate);
        Review r3 = new Review(100, productSN, Review.Rate.FOUR_STARS, "Very good product!", reviewDate);
        Review r4 = new Review(54, anotherProductSN, Review.Rate.FOUR_STARS, "Very good product!", reviewDate);
        List<Review> reviewList = Arrays.asList(r1, r3);
        return reviewList;
    }

    @Test
    public void getReviewsForProduct_Success() throws Exception {
        // Mock data
        String productSN = "123456789123456858";
        String anotherProductSN = "147258369asdfghjk1";
        Date reviewDate = Date.valueOf(LocalDate.now());
        List<Review> reviewList = getReviewsByProductSN(productSN, reviewDate, anotherProductSN);

        // Mock the service behavior
        when(reviewService.getReviewsForProduct(productSN)).thenReturn(reviewList);

        // Performing the request
        MvcResult result = mockMvc.perform(get("/reviews/product/{productSN}", productSN))
                .andExpect(status().isOk())
                .andReturn();

        // Verifying the response
        MockHttpServletResponse response = result.getResponse();
        List<Review> resultReviews = Arrays.asList(
                objectMapper.readValue(response.getContentAsString(), Review[].class));

        // Verifying that the service method was called
        verify(reviewService, times(1)).getReviewsForProduct(productSN);

        // Asserting the result
        assertEquals(reviewList.size(), resultReviews.size());

        // Asserting each attribute of the first review in the result
        assertEquals(reviewList.get(0).getUserId(), resultReviews.get(0).getUserId());
        assertEquals(reviewList.get(0).getProductSN(), resultReviews.get(0).getProductSN());
        assertEquals(reviewList.get(0).getRating(), resultReviews.get(0).getRating());
        assertEquals(reviewList.get(0).getComment(), resultReviews.get(0).getComment());
        assertEquals(reviewList.get(0).getReviewDate().toString(), resultReviews.get(0).getReviewDate().toString());

        // Asserting each attribute of the second review in the result
        assertEquals(reviewList.get(1).getUserId(), resultReviews.get(1).getUserId());
        assertEquals(reviewList.get(1).getProductSN(), resultReviews.get(1).getProductSN());
        assertEquals(reviewList.get(1).getRating(), resultReviews.get(1).getRating());
        assertEquals(reviewList.get(1).getComment(), resultReviews.get(1).getComment());
        assertEquals(reviewList.get(1).getReviewDate().toString(), resultReviews.get(1).getReviewDate().toString());
    }

    @Test
    public void getReviewsForProduct_NoReviews() throws Exception {
        // Mock data
        String productSN = "123456789123456858";

        // Mock the service behavior to simulate not found
        when(reviewService.getReviewsForProduct(productSN)).thenReturn(Collections.emptyList());

        // Performing the request
        MvcResult result = mockMvc.perform(get("/reviews/product/{productSN}", productSN))
                .andExpect(status().isOk())
                .andReturn();

        // Verifying the response
        MockHttpServletResponse response = result.getResponse();
        List<Review> resultReviews = Arrays.asList(objectMapper.readValue(response.getContentAsString(), Review[].class));
        assertTrue(resultReviews.isEmpty());

        // Verifying that the service method was called
        verify(reviewService, times(1)).getReviewsForProduct(productSN);
    }
}
