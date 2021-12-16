package Tema2.Ejercicios;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

public class DOM {

    public static void main(String[] args) {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new File("D:\\IdeaProjects\\AD\\ficheros\\fichero.xml"));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e.getMessage());
        }

        Node node = document.getDocumentElement();
        printXML(node, 0);
    }

    private static void printXML(Node node, int indent) {
        System.out.println(); // Salto de línea del nuevo nodo

        // Sangría correspondiente
        for (int i = 0; i < indent; i++) {
            System.out.print("\t");
        }

        // Nombre del Nodo
        System.out.print(node.getNodeName());

        // Atributos
        if (node.hasAttributes()) {
            NamedNodeMap nnm = node.getAttributes();
            for (int i = 0; i < nnm.getLength(); i++) {
                System.out.print(" (" + nnm.item(i).getNodeName() + ": " + nnm.item(i).getNodeValue() + ")");
            }
        }

        // Termina el nombre del Nodo
        System.out.print(": ");

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {

            // Solo hijos directos
            if (node.getChildNodes().item(i).getParentNode().isSameNode(node)) {

                // Tipo elemento
                if (node.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                    printXML(node.getChildNodes().item(i), indent + 1);
                }

                // Tipo texto
                else if (node.getChildNodes().item(i).getNodeType() == Node.TEXT_NODE) {
                    System.out.print(node.getChildNodes().item(i).getNodeValue().trim());
                }
            }
        }
    }
}
