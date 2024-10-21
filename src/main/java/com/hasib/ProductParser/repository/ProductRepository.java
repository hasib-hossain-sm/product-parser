package com.hasib.ProductParser.repository;

import com.hasib.ProductParser.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.UUID;

public interface ProductRepository extends RevisionRepository<Product, UUID, Integer>, JpaRepository<Product, UUID> {

    Product findBySku(String sku);
}