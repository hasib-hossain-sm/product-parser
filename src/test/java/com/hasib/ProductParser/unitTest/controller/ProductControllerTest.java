package com.hasib.ProductParser.unitTest.controller;

import com.hasib.ProductParser.controller.ProductController;
import com.hasib.ProductParser.dto.ProductUploadResponseDto;
import com.hasib.ProductParser.model.Product;
import com.hasib.ProductParser.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void ProductController_UploadFile_ShouldUploadSuccessfully() throws Exception {

        ProductUploadResponseDto responseDto = ProductUploadResponseDto.builder()
                .newlyAddedRows(2)
                .updatedRows(0)
                .unchangedRows(0)
                .build();
        when(productService.processFile(any())).thenReturn(responseDto);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "products.xlsx",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "products".getBytes()
        );

        String jsonResponse = """
                {
                    "message": "File uploaded successfully!",
                    "statusCode": 200,
                    "data": {
                        "unchangedRows": 0,
                        "updatedRows": 0,
                        "newlyAddedRows": 2
                    }
                }
                """;
        ResultActions resultActions = mockMvc.perform(multipart("/api/products/upload").file(file))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        content().json(jsonResponse)
                );

    }

    @Test
    public void ProductController_GetAllProduct_ShouldReturnProductList() throws Exception {

        List<Product> products = List.of(
                new Product(UUID.randomUUID(), "SKU1", "Product 1", 10.0, 5, "Description 1", null, null),
                new Product(UUID.randomUUID(), "SKU2", "Product 2", 15.0, 3, "Description 2", null, null)
        );

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.message").value("All Products retrieved successfully."))
                .andExpect((ResultMatcher) jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect((ResultMatcher) jsonPath("$.data", hasSize(2)))
                .andExpect((ResultMatcher) jsonPath("$.data[0].sku").value("SKU1"))
                .andExpect((ResultMatcher) jsonPath("$.data[1].sku").value("SKU2"));
    }

}
