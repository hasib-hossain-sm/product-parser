package com.hasib.ProductParser.service.parser;


import com.hasib.ProductParser.model.enums.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class FileParserFactory {
    private final Map<FileType, FileParser> parserMap = new EnumMap<>(FileType.class);

    @Autowired
    public FileParserFactory(CSVParser csvParser, XLSXParser xlsxParser) {
        parserMap.put(FileType.XLSX, xlsxParser);
        parserMap.put(FileType.CSV, csvParser);
    }

    public FileParser getParser(FileType fileType) {
        return parserMap.get(fileType);
    }
}
