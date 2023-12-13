package com.edu.productservice.repository;

import com.edu.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{'name': ?0}")
    <S extends Product> Optional<S> findByName(String name);
}
