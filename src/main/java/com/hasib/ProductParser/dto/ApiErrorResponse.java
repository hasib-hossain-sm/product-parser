package com.hasib.ProductParser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiErrorResponse {

    private String error;
    private String details;
    private int statusCode;
}
