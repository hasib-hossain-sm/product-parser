package com.hasib.ProductParser.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class ProductUploadResponseDto {
    private int unchangedRows;
    private int updatedRows;
    private int newlyAddedRows;
}
