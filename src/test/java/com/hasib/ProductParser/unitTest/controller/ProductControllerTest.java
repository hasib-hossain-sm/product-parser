package com.hasib.ProductParser.unitTest.controller;

import com.hasib.ProductParser.controller.ProductController;
import com.hasib.ProductParser.dto.ProductUploadResponseDto;
import com.hasib.ProductParser.model.ChangeHistory;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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

        ResultActions resultActions = mockMvc.perform(get("/api/products/"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.message").value("All Products retrieved successfully."),
                        MockMvcResultMatchers.jsonPath("$.statusCode").value(HttpStatus.OK.value()),
                        MockMvcResultMatchers.jsonPath("$.data", hasSize(2)),
                        MockMvcResultMatchers.jsonPath("$.data[0].sku").value("SKU1"),
                        MockMvcResultMatchers.jsonPath("$.data[1].sku").value("SKU2")
                );
    }

    @Test
    public void ProductController_getChangeHistories_ShouldReturnChangeHistories() throws Exception {
        Product oldProduct = new Product(UUID.randomUUID(), "SKU1", "Product 1", 10.0, 5, "Old Description", null, null);
        Product newProduct = new Product(UUID.randomUUID(), "SKU1", "Product 1", 15.0, 3, "New Description", null, null);

        ChangeHistory changeHistory1 = new ChangeHistory(newProduct, List.of(oldProduct, newProduct));
        List<ChangeHistory> changeHistories = List.of(changeHistory1);

        when(productService.getChangeHistories()).thenReturn(changeHistories);

        ResultActions resultActions = mockMvc.perform(get("/api/products/change-histories"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.message").value("Product change histories retrieved successfully."),
                        MockMvcResultMatchers.jsonPath("$.statusCode").value(HttpStatus.OK.value()),
                        MockMvcResultMatchers.jsonPath("$.data", hasSize(1)),
                        MockMvcResultMatchers.jsonPath("$.data[0].histories", hasSize(2)),
                        MockMvcResultMatchers.jsonPath("$.data[0].histories[0].price").value(10),
                        MockMvcResultMatchers.jsonPath("$.data[0].histories[1].price").value(15),
                        MockMvcResultMatchers.jsonPath("$.data[0].histories[0].quantity").value(5),
                        MockMvcResultMatchers.jsonPath("$.data[0].histories[1].quantity").value(3)
                );
    }

    @Test
    void ProductController_GetProductBySku_ShouldReturnProduct() throws Exception {

        String sku = "SKU123";
        Product product = new Product(UUID.randomUUID(), sku, "Product 1", 10.0, 5, "Description 1", null, null);

        when(productService.getProductBySku(sku)).thenReturn(product);

        ResultActions resultActions = mockMvc.perform(get("/{sku}", sku))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.message").value("Product retrieved successfully."),
                        MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.value()),
                        MockMvcResultMatchers.jsonPath("$.data.sku").value(sku),
                        MockMvcResultMatchers.jsonPath("$.data.title").value("Product 1"),
                        MockMvcResultMatchers.jsonPath("$.data.price").value(10.0),
                        MockMvcResultMatchers.jsonPath("$.data.quantity").value(5),
                        MockMvcResultMatchers.jsonPath("$.data.description").value("Description 1")
                );

    }
}
