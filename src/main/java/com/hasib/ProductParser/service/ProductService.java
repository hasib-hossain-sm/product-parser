package com.hasib.ProductParser.service;

import com.hasib.ProductParser.dto.ProductUploadResponseDto;
import com.hasib.ProductParser.model.Product;
import com.hasib.ProductParser.model.enums.FileType;
import com.hasib.ProductParser.repository.ProductRepository;
import com.hasib.ProductParser.service.parser.FileParser;
import com.hasib.ProductParser.service.parser.FileParserFactory;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileParserFactory fileParserFactory;

    public ProductUploadResponseDto processFile(MultipartFile file) throws IOException, CsvValidationException {
        String filename = file.getOriginalFilename();

        assert filename != null;

        FileType fileType = FileType.getByExtension(filename.substring(filename.lastIndexOf(".")));
        FileParser parser = fileParserFactory.getParser(fileType);

        if (parser == null) {
            throw new NullPointerException("Parser not found");
        }

        InputStream inputStream = file.getInputStream();

        List<Product> newProducts = parser.parseFile(inputStream);

        return processProducts(newProducts);
    }

    @Transactional
    private ProductUploadResponseDto processProducts(List<Product> newProducts) {
        List<Product> oldProducts = productRepository.findAll();

        HashMap<String, Product> productHashMap = new HashMap<>();

        for (Product product : oldProducts) {
            productHashMap.put(product.getSku(), product);
        }

        List<Product> newlyAddedProducts = new ArrayList<>();
        List<Product> unchangedProducts = new ArrayList<>();
        List<Product> updatedProducts = new ArrayList<>();

        for (Product newProduct : newProducts) {
            Product oldProduct = productHashMap.get(newProduct.getSku());
            if (oldProduct == null) {
                newlyAddedProducts.add(newProduct);
                continue;
            }

            if (newProduct.isSame(oldProduct)) {
                unchangedProducts.add(oldProduct);
            } else {
                updatedProducts.add(oldProduct.getUpdatedProduct(newProduct));
            }

            productHashMap.remove(newProduct.getSku());
        }

        List<Product> deletedProducts = new ArrayList<>(productHashMap.values());

        saveOrUpdateProducts(Stream.concat(newlyAddedProducts.stream(), updatedProducts.stream()).toList());
        deleteProducts(deletedProducts);

        return ProductUploadResponseDto.builder()
                .updatedRows(updatedProducts.size() + deletedProducts.size())
                .unchangedRows(unchangedProducts.size())
                .newlyAddedRows(newlyAddedProducts.size())
                .build();
    }

    private void deleteProducts(List<Product> products) {
        productRepository.deleteAllByIdInBatch(products.stream().map(Product::getId).collect(Collectors.toList()));
    }

    private void saveOrUpdateProducts(List<Product> products) {
        productRepository.saveAll(products);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
