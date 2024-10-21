package com.hasib.ProductParser.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChangeHistory extends Product {

    private List<Product> histories;

    public ChangeHistory(Product product, List<Product> histories){
        this.histories = histories;
        this.setId(product.getId());
        this.setDescription(product.getDescription());
        this.setQuantity(product.getQuantity());
        this.setPrice(product.getPrice());
        this.setSku(product.getSku());
        this.setTitle(product.getTitle());
        this.setCreatedDate(product.getCreatedDate());
        this.setLastModifiedDate(product.getLastModifiedDate());
    }
}
