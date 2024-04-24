package com.example.productservice;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    //This line initializes a static MongoDBContainer instance with the specified Docker image version (mongo:4.4.2). MongoDBContainer is provided by Testcontainers and is used to manage a MongoDB Docker container during testing.

    //mock servlet environment
    @Autowired
    private MockMvc mockMvc; //removed the error by adding @AutoConfigureMockMvc in line 16

    //convert productRequest into String
    @Autowired
    private ObjectMapper objectMapper;

    //check if the data is really saved to the databse
    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource //a Testcontainers annotation that allows dynamically setting properties for the application context during testing.
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
    @Test
    void shouldCreateProduct() throws Exception {

        ProductRequest productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, productRepository.findAll().size()); // it's going to be 1 bc we save  1 product


    }

    private ProductRequest getProductRequest() {

        return ProductRequest.builder()
                .name("iphone13")
                .description("iphone 13")
                .price(BigDecimal.valueOf(1200))
                .build();
    }

}
