package com.hasib.ProductParser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductUploadResponseDto {
    private int unchangedRows;
    private int updatedRows;
    private int newlyAddedRows;
}
