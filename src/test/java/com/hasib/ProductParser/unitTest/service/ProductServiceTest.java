package com.hasib.ProductParser.unitTest.service;


import com.hasib.ProductParser.dto.ProductUploadResponseDto;
import com.hasib.ProductParser.model.enums.FileType;
import com.hasib.ProductParser.repository.ProductRepository;
import com.hasib.ProductParser.service.ProductService;
import com.hasib.ProductParser.service.parser.FileParserFactory;
import com.hasib.ProductParser.service.parser.XLSXParser;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private FileParserFactory fileParserFactory;

    @InjectMocks
    private ProductService productService;

    @Test
    public void ProductService_ProcessFile_ReturnsProductUploadResponseDto() throws IOException, CsvValidationException {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        when(productRepository.saveAll(any())).thenReturn(new ArrayList<>());

        when(fileParserFactory.getParser(any(FileType.class))).thenReturn(new XLSXParser());

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "products.xlsx",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                Objects.requireNonNull(this.getClass().getClassLoader()
                        .getResourceAsStream("products.xlsx")).readAllBytes()
        );

        ProductUploadResponseDto responseDto = productService.processFile(file);
        Assertions.assertEquals(2, responseDto.getNewlyAddedRows());
        Assertions.assertEquals(0, responseDto.getUnchangedRows());
        Assertions.assertEquals(0, responseDto.getUpdatedRows());
    }
}
