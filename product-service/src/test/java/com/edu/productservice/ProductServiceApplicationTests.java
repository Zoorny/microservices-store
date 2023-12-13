package com.edu.productservice;

import com.edu.productservice.dto.ProductRequest;
import com.edu.productservice.model.Product;
import com.edu.productservice.repository.ProductRepository;
import com.edu.util.MongoDBTestContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceApplicationTests extends MongoDBTestContainer {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	ProductRepository productRepository;

	@Order(1)
	@DisplayName("Should create product")
	@Test
	void shouldCreateProduct() throws Exception {
		String productRequestString = objectMapper.writeValueAsString(getSingleProductRequest());
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
					.contentType(MediaType.APPLICATION_JSON)
					.content(productRequestString))
				.andExpect(status().isCreated());
		assertEquals(1, productRepository.findAll().size());
		assertTrue(productRepository.findByName("Nothing Phone").isPresent());
	}

	@Order(2)
	@Test
	@DisplayName("Should retrieve list of products")
	void shouldRetrieveAllProducts() throws Exception {
		productRepository.save(getSingleProduct());
		mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()", Matchers.is(2)))
				.andExpect(jsonPath("$[0].name", Matchers.is("Nothing Phone")))
				.andExpect(jsonPath("$[0].description", Matchers.is("Fancy one")))
				.andExpect(jsonPath("$[0].price", Matchers.is(600)))
				.andExpect(jsonPath("$[1].name", Matchers.is("Nothing Phone")))
				.andExpect(jsonPath("$[1].description", Matchers.is("Fancy one")))
				.andExpect(jsonPath("$[1].price", Matchers.is(600)));
	}

	private ProductRequest getSingleProductRequest() {
		return ProductRequest.builder()
				.name("Nothing Phone")
				.description("Fancy one")
				.price(BigDecimal.valueOf(600))
				.build();
	}

	private Product getSingleProduct() {
		return Product.builder()
				.name("Nothing Phone")
				.description("Fancy one")
				.price(BigDecimal.valueOf(600))
				.build();
	}

}
