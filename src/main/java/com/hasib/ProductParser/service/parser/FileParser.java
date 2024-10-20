package com.hasib.ProductParser.service.parser;


import com.hasib.ProductParser.model.Product;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FileParser {
    List<Product> parseFile(InputStream inputStream) throws IOException, CsvValidationException;
}
