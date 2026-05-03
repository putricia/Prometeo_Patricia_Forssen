package org.example.logitronapp.repository;

import org.example.logitronapp.model.Articulo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class ArticulosRepository extends XmlRepository {

    private static final String path = "/articulos.xml";

    public List<Articulo> findAll() {

        List<Articulo> articulos = new ArrayList<>();

        Document doc = loadDocument(path);
        NodeList lista = doc.getElementsByTagName("articulo");

        for (int i = 0; i < lista.getLength(); i++) {

            Element el = (Element) lista.item(i);

            int id = Integer.parseInt(el.getAttribute("id"));
            String nombre = getTagValue(el,"nombre");
            int uds_stock = Integer.parseInt(getTagValue(el,"uds_stock"));
            double pCompra = Double.parseDouble(getTagValue(el,"precio_compra"));
            double pVenta = Double.parseDouble(getTagValue(el,"precio_venta"));
            boolean porPeso = Boolean.parseBoolean(getTagValue(el,"por_peso"));

            articulos.add(new Articulo(id, nombre, uds_stock, pCompra, pVenta, porPeso));

        }

        return articulos;
    }

    private String getTagValue(Element element, String tag) {
        return element.getElementsByTagName(tag).item(0).getTextContent();
    }

}
