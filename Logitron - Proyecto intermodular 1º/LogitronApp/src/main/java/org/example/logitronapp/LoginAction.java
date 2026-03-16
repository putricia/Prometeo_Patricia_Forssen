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
            // ubicamos el archivo que vamos a leer
            File xml_personas = new File("src/main/resources/personas.xml");

            // creamos una "fabrica de lectores de xml"
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // creamos el lector de xml
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Parseamos el XML
            Document docPersonas = builder.parse(xml_personas);

            // buscamos todos los nodos "persona"
            NodeList personas = docPersonas.getElementsByTagName("persona");

            // definimos la variable de idPersona para poder llamarla fuera del metodo
            String idPersona = null;

            // iteramos todas las personas
            for (int i = 0; i < personas.getLength(); i++) {

                // convertimos el nodo en eun elemento XML
                Element persona = (Element) personas.item(i);

                // obtenemos el correo de ese elemento
                String correoXML = persona
                        .getElementsByTagName("correo")
                        .item(0) // el primer elemento dentro de el elemento "correo"
                        .getTextContent();

                // comparamos el correo del elemento con el que hace login ignoreando mayusculas y omitiendo espacios en blanco
                if (correoXML.equals(user.trim().toLowerCase())) {
                    // en caso de coincidir, devolvemos el id de la persona
                    idPersona = persona.getAttribute("id");
                    break;
                }

            }

            // si no se ha encontrado el correo, mensaje de login incorrecto
            if (idPersona == null) {
                return null;
            }

            // buscamos la clave del empleado que tiene el idPersona
            File xml_empleados = new File("src/main/resources/empleados.xml");

            Document docEmpleados = builder.parse(xml_empleados);

            NodeList empleados = docEmpleados.getElementsByTagName("empleado");

            for (int i = 0; i < empleados.getLength(); i++) {
                Element empleado = (Element) empleados.item(i);

                // ubicamos el id del elemento
                String idEmpleado = empleado.getAttribute("id");
                // ubicamos la clave de el elemento
                String claveXML = empleado
                        .getElementsByTagName("clave")
                        .item(0)
                        .getTextContent();

                // consultamos ademas el rol del empleado que accede
                String rolXML = empleado
                        .getElementsByTagName("rol")
                        .item(0)
                        .getTextContent();

                if (idEmpleado.equals(idPersona) && claveXML.equals(clave)) {
                    return rolXML;
                }

            }


        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
