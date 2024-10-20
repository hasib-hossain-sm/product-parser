package com.hasib.ProductParser.service.parser;


import com.hasib.ProductParser.model.Product;

import java.io.InputStream;
import java.util.List;

public interface FileParser {
    List<Product> parseFile(InputStream inputStream);
}
