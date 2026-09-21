package com.example.productservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ProductControllerIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer =
            new MongoDBContainer("mongo:7.0");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static void configureMongoDB(
            DynamicPropertyRegistry registry) {

        registry.add(
            "spring.data.mongodb.uri",
            mongoDBContainer::getReplicaSetUrl
        );
    }

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void createProductTest() throws Exception {

        String productJson = """
                {
                    "name": "Test Laptop",
                    "description": "Test Product",
                    "price": 50000,
                    "quantity": 5
                }
                """;

        mockMvc.perform(
                post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Laptop"))
                .andExpect(jsonPath("$.price").value(50000.0))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    void getAllProductsTest() throws Exception {

        Product product = new Product(null, "Keyboard", "Mechanical Keyboard", 3500.0, 20);
        productRepository.save(product);

        mockMvc.perform(
                get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Keyboard"));
    }

    @Test
    void getProductByIdTest() throws Exception {

        Product product = productRepository.save(
                new Product(null, "Mouse", "Wireless Mouse", 1500.0, 30));

        mockMvc.perform(
                get("/api/products/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mouse"))
                .andExpect(jsonPath("$.price").value(1500.0));
    }

    @Test
    void updateProductTest() throws Exception {

        Product product = productRepository.save(
                new Product(null, "Old Laptop", "Old Specs", 40000.0, 2));

        String updateJson = """
                {
                    "name": "HP Victus",
                    "description": "Updated gaming laptop",
                    "price": 72000,
                    "quantity": 15
                }
                """;

        mockMvc.perform(
                put("/api/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("HP Victus"))
                .andExpect(jsonPath("$.price").value(72000.0))
                .andExpect(jsonPath("$.quantity").value(15));
    }

    @Test
    void deleteProductTest() throws Exception {

        Product product = productRepository.save(
                new Product(null, "To Be Deleted", "Disposable", 100.0, 1));

        mockMvc.perform(
                delete("/api/products/" + product.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(
                get("/api/products/" + product.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void validationFailureTest() throws Exception {

        String invalidJson = """
                {
                    "name": "",
                    "price": -100,
                    "quantity": -5
                }
                """;

        mockMvc.perform(
                post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
