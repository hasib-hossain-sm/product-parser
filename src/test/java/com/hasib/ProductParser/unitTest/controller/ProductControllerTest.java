package com.hasib.ProductParser.unitTest.controller;

import com.hasib.ProductParser.controller.ProductController;
import com.hasib.ProductParser.dto.ProductUploadResponseDto;
import com.hasib.ProductParser.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
}
