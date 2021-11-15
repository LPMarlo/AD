package Tema1.Ejemplos.DOM_SAX.Ejercicio;

import Tema1.Ejemplos.DOM_SAX.MiClaseSax2;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class EjercicioSAX {
    public static void main(String[] args) {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = parserFactory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler(new MiClaseSax2());
            reader.parse(new InputSource("ficheros\\resultados.xml"));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }
}
