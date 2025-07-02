package com.eazybytes.accounts.service.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountsControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    // Create
    @Test
    void testCreateAndFetchProduct() throws Exception {
        // Create
        String name = "Test Product";
        String body = "{\n" +
                "    \"name\": \"Madan Reddy\",\n" +
                "    \"email\": \"tutor@eazybytes\",\n" +
                "    \"mobileNumber\": \"4354437687\"\n" +
                "  }";
        String response = mockMvc.perform(post("/api/create").content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Fetch
        mockMvc.perform((get("/api/fetch")).param("mobileNumber", "4354437687"))
                .andExpect(status().isOk());
    }
}
