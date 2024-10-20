package com.hasib.ProductParser.model.enums;

import lombok.Getter;
import org.apache.coyote.BadRequestException;

import java.util.Arrays;

@Getter
public enum FileType {
    XLSX(".xlsx"),
    CSV(".csv");

    private final String extension;

    FileType(String extension) {
        this.extension = extension;
    }

    public static FileType getByExtension(String fileExtension) throws BadRequestException {
        return Arrays.stream(FileType.values())
                .filter(type -> type.getExtension().equalsIgnoreCase(fileExtension))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Unsupported file type: " + fileExtension));
    }
}
