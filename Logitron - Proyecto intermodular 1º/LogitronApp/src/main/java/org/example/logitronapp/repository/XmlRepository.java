package org.example.logitronapp.repository;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public abstract class XmlRepository {
    protected Document loadDocument(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);

            if (is == null) {
                throw new RuntimeException("No se encontró el archivo: "+path);
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            return builder.parse(is);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
