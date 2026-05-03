package org.example.logitronapp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class LoginAction {

    public static String validarLogin(String user, String clave) {
        try {
            File xmlPersonas = new File("src/main/resources/personas.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document docPersonas = builder.parse(xmlPersonas);

            docPersonas.getDocumentElement().normalize();

            NodeList personas = docPersonas.getElementsByTagName("persona");

            for (int i = 0; i < personas.getLength(); i++) {
                Element persona = (Element) personas.item(i);

                String correoXML = persona
                        .getElementsByTagName("correo")
                        .item(0)
                        .getTextContent()
                        .trim()
                        .toLowerCase();

                String claveXML = persona
                        .getElementsByTagName("clave")
                        .item(0)
                        .getTextContent()
                        .trim();

                String rolIdXML = persona
                        .getElementsByTagName("rolId")
                        .item(0)
                        .getTextContent()
                        .trim();

                if (correoXML.equals(user.trim().toLowerCase()) && claveXML.equals(clave.trim())) {

                    return rolIdXML;
                }
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}