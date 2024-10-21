package com.hasib.ProductParser.controller;


import com.hasib.ProductParser.dto.ApiResponse;
import com.hasib.ProductParser.dto.ProductUploadResponseDto;
import com.hasib.ProductParser.model.ChangeHistory;
import com.hasib.ProductParser.model.Product;
import com.hasib.ProductParser.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ProductUploadResponseDto>> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        ProductUploadResponseDto responseDto = productService.processFile(file);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "File uploaded successfully!",
                        HttpStatus.OK.value(),
                        responseDto
                )
        );
    }

    @GetMapping("/change-histories")
    public ResponseEntity<ApiResponse<List<ChangeHistory>>> getChangeHistories() {

        List<ChangeHistory> changeHistories = productService.getChangeHistories();
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Product change histories retrieved successfully.",
                        HttpStatus.OK.value(),
                        changeHistories
                )
        );
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {

        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "All Products retrieved successfully.",
                        HttpStatus.OK.value(),
                        products
                )
        );
    }
}
