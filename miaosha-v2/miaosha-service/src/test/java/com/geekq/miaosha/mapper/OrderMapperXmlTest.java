package com.geekq.miaosha.mapper;

import org.junit.jupiter.api.Test;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderMapperXmlTest {

    @Test
    public void overdueOrderQueryUsesAValidLessThanOrEqualOperator() throws Exception {
        Path mapper = Paths.get("src/main/java/com/geekq/miaosha/mapper/OrderMapper.xml");
        String xml = new String(Files.readAllBytes(mapper), StandardCharsets.UTF_8);

        assertFalse(xml.contains("$lt;="));

        String withoutDoctype = xml.replaceFirst("(?s)<!DOCTYPE.*?>", "");
        String mapperText = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(withoutDoctype.getBytes(StandardCharsets.UTF_8)))
                .getDocumentElement()
                .getTextContent();
        assertTrue(mapperText.contains("create_Date <=#{createDate}"));
    }
}
