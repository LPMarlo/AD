package Tema1.Ejemplos.DOM_SAX.Ejercicio;

import org.w3c.dom.Document;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class EjercicioDOM {
    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse("ficheros\\resultados.xml");
            doc.getDocumentElement().normalize();

            Node raiz = doc.getDocumentElement();
            NodeList elementos = raiz.getChildNodes();

            for (int i = 0; i < elementos.getLength(); i++) {
                Node partido = elementos.item(i);
                if (partido.getNodeType() == Node.ELEMENT_NODE) {

                    //Nombres de los equipos
                    NodeList nombre = ((Element) partido).getElementsByTagName("equipo");
                    String nombreLocal = nombre.item(0).getTextContent();
                    String nombreVisitante = nombre.item(1).getTextContent();

                    //Goles de cada partido
                    NodeList goles = ((Element) partido).getElementsByTagName("goles");
                    int golesLocal = Integer.parseInt(goles.item(0).getTextContent().trim());
                    int golesVisitante = Integer.parseInt(goles.item(1).getTextContent().trim());

                    //Resultados
                    if (golesLocal>golesVisitante) {
                        System.out.print(nombreLocal + " - " + nombreVisitante + " 1\n");
                    } else if (golesLocal<golesVisitante) {
                            System.out.print(nombreLocal + " - " + nombreVisitante + " 2\n");
                    } else {
                        System.out.print(nombreLocal + " - " + nombreVisitante + " X\n");
                    }
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
}
