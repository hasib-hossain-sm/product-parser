package com.hasib.ProductParser.controller;


import com.hasib.ProductParser.dto.ApiResponse;
import com.hasib.ProductParser.dto.ProductUploadResponseDto;
import com.hasib.ProductParser.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
}
