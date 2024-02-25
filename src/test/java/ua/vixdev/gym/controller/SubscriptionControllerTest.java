package ua.vixdev.gym.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.vixdev.gym.dto.SubscriptionDto;
import ua.vixdev.gym.service.SubscriptionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SubscriptionController.class)
public class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SubscriptionService subscriptionService;

    @InjectMocks
    private SubscriptionController subscriptionController;

    @Test
    public void getAllSubscriptions_ReturnsListOfSubscriptions() throws Exception {
        // Arrange
        List<SubscriptionDto> subscriptions = new ArrayList<>();
        subscriptions.add(new SubscriptionDto(1L, "Description 1", true, 10.00, "image1.jpg", LocalDateTime.now(), LocalDateTime.now(), null, null, false, null, null, "Text 1", "Title 1", 1, "Doe"));
        subscriptions.add(new SubscriptionDto(2L, "Description 2", true, 20.00, "image2.jpg", LocalDateTime.now(), LocalDateTime.now(), null, null, false, null, null, "Text 2", "Title 2", 2, "Smith"));

        doReturn(subscriptions).when(subscriptionService).getAllSubscriptions();

        // Act & Assert
        mockMvc.perform(get("/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[1].description").value("Description 2"));
    }

    @Test
    public void createSubscription_ReturnsCreatedSubscription() throws Exception {
        // Arrange
        SubscriptionDto requestDto = new SubscriptionDto(null, "New Subscription", true, 30.00, "new_image.jpg", LocalDateTime.now(), LocalDateTime.now(), null, null, false, null, null, "New Text", "New Title", 3, "Johnson");
        SubscriptionDto createdSubscription = new SubscriptionDto(1L, "New Subscription", true, 30.00, "new_image.jpg", LocalDateTime.now(), LocalDateTime.now(), null, null, false, null, null, "New Text", "New Title", 3, "Johnson");

        doReturn(createdSubscription).when(subscriptionService).createSubscription(requestDto);

        // Act & Assert
        mockMvc.perform(post("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("New Subscription"));
    }

    @Test
    public void updateSubscription_WhenSubscriptionExists_ReturnsUpdatedSubscription() throws Exception {
        // Arrange
        Long id = 1L;
        SubscriptionDto requestDto = new SubscriptionDto(id, "Updated Subscription", true, 40.00, "updated_image.jpg", LocalDateTime.now(), LocalDateTime.now(), null, null, false, null, null, "Updated Text", "Updated Title", 4, "Brown");
        SubscriptionDto updatedSubscription = new SubscriptionDto(id, "Updated Subscription", true, 40.00, "updated_image.jpg", LocalDateTime.now(), LocalDateTime.now(), null, null, false, null, null, "Updated Text", "Updated Title", 4, "Brown");

        doReturn(Optional.of(updatedSubscription)).when(subscriptionService).updateSubscription(id, requestDto);

        // Act & Assert
        mockMvc.perform(put("/subscriptions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated Subscription"));
    }

    @Test
    public void deleteSubscription_WhenSubscriptionExists_ReturnsNoContent() throws Exception {
        // Arrange
        Long id = 1L;
        doReturn(true).when(subscriptionService).deleteSubscription(id);

        // Act & Assert
        mockMvc.perform(delete("/subscriptions/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteSubscription_WhenSubscriptionDoesNotExist_ReturnsNotFound() throws Exception {
        // Arrange
        Long id = 1L;
        doReturn(false).when(subscriptionService).deleteSubscription(id);

        // Act & Assert
        mockMvc.perform(delete("/subscriptions/{id}", id))
                .andExpect(status().isNotFound());
    }
}
