package com.hasib.ProductParser.service.parser;


import com.hasib.ProductParser.model.Product;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVParser implements FileParser {

    @Override
    public List<Product> parseFile(InputStream inputStream) throws IOException, CsvValidationException {
        List<Product> products = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] values;
            boolean isFirstRow = true;

            while ((values = csvReader.readNext()) != null) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }

                Product product = Product.builder()
                        .sku(values[0])
                        .title(values[1])
                        .price(Double.parseDouble(values[2]))
                        .quantity(Integer.parseInt(values[3]))
                        .description(values[4])
                        .build();

                products.add(product);
            }
        }

        return products;
    }
}
