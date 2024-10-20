package com.hasib.ProductParser.unitTest.service.parser;

import com.hasib.ProductParser.model.enums.FileType;
import com.hasib.ProductParser.service.parser.CSVParser;
import com.hasib.ProductParser.service.parser.FileParser;
import com.hasib.ProductParser.service.parser.FileParserFactory;
import com.hasib.ProductParser.service.parser.XLSXParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FileParserFactoryTest {

    @Mock
    private CSVParser csvParser;

    @Mock
    private XLSXParser xlsxParser;

    @InjectMocks
    private FileParserFactory fileParserFactory;

    @Test
    public void FileParserFactory_GetParser_ReturnsCSVParser() {

        FileParser parser = fileParserFactory.getParser(FileType.CSV);

        Assertions.assertEquals(csvParser, parser, "Should return the CSVParser for CSV file type");
    }

    @Test
    public void FileParserFactory_GetParser_ReturnsXLSXParser() {

        FileParser parser = fileParserFactory.getParser(FileType.XLSX);

        Assertions.assertEquals(xlsxParser, parser, "Should return the XLSXParser for XLSX file type");
    }

    @Test
    void FileParserFactory_GetParser_ReturnsNull() {

        FileParser parser = fileParserFactory.getParser(null);

        Assertions.assertNull(parser, "Should return null for an unsupported file type or null");
    }
}
