package com.hasib.ProductParser.service.parser;


import com.hasib.ProductParser.model.Product;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class XLSXParser implements FileParser {

    @Override
    public List<Product> parseFile(InputStream inputStream) {
        return null;

    }
}
