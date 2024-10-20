package com.hasib.ProductParser.service.parser;


import com.hasib.ProductParser.model.Product;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class XLSXParser implements FileParser {

    @Override
    public List<Product> parseFile(InputStream inputStream) {
        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException ex) {
            throw new EmptyFileException();
        }

        Sheet sheet = workbook.getSheetAt(0);
        List<Product> products = new ArrayList<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            Product product = Product.builder()
                    .sku(row.getCell(0).getStringCellValue())
                    .title(row.getCell(1).getStringCellValue())
                    .price(row.getCell(2).getNumericCellValue())
                    .quantity((int) row.getCell(3).getNumericCellValue())
                    .description(row.getCell(4).getStringCellValue())
                    .build();

            products.add(product);
        }

        return products;

    }
}
