package Tema1.Examen;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Ejercicio4 {
    public static void main(String[] args) {
        String rutaArchivoXML = "ficheros\\info_cliente.xml";
        pintaInfoCliente(rutaArchivoXML);
    }
    public static void pintaInfoCliente(String rutaArchivoXML) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(rutaArchivoXML));

            NodeList datos = document.getChildNodes();
            for (int i = 0; i < datos.getLength(); i++) {
                Node dato = datos.item(i);
                    System.out.println("Cliente:");
                    NodeList cliente = ((Element) dato).getElementsByTagName("cliente");
                    NodeList nombre = ((Element) cliente.item(0)).getElementsByTagName("nombre");
                    System.out.println("\t" + nombre.item(0).getTextContent());

                    NodeList apellido1 = ((Element) cliente.item(0)).getElementsByTagName("apellido1");
                    System.out.println("\t" + apellido1.item(0).getTextContent());

                    NodeList apellido2 = ((Element) cliente.item(0)).getElementsByTagName("apellido2");
                    System.out.println("\t" + apellido2.item(0).getTextContent());

                    System.out.print("Pedido:");
                    NodeList idpedido = ((Element) dato).getElementsByTagName("idPedido");
                    System.out.println("\t" + idpedido.item(0).getTextContent());

                    System.out.print("\tProductos:");
                    NodeList nombres = ((Element) dato).getElementsByTagName("producto");
                    NodeList precios = ((Element) dato).getElementsByTagName("precio");
                    for (int j = 0; j < nombres.getLength() && j < precios.getLength(); j++) {
                        System.out.print(((Element) nombres.item(j)).getElementsByTagName("nombre").item(0).getTextContent() + ", ");
                    }

            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

    }
}
