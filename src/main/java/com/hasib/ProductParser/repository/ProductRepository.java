package com.hasib.ProductParser.repository;

import com.hasib.ProductParser.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}