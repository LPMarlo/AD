package Ejemplos.Tema1;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Ejercicio13 {
    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("ficheros\\archivo.xml");
            Node raiz = doc.getDocumentElement();

            NodeList elementos = raiz.getChildNodes();
            for (int i = 0; i < elementos.getLength(); i++) {
                Node nodo = elementos.item(i);
                //if (nodo.getNodeType()==Node.ELEMENT_NODE) System.out.println(nodo);
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }



    }
}
