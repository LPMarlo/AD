package Tema1.Ejemplos.DOM_SAX;
/*
	
		Realiza las siguientes tareas, a partir de la clase persona:
		
			a) Crea un fichero que almacene varios objetos persona.
			b) Tomando como base el fichero anterior, crea un documento XML usando DOM.
			c) Implementa una clase que permita leer el documento XML del apartado anterior.
*/

import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;

public class PrincipalEjemplo2DOM {

	private static final String FICHERO_OBJETOS = "personas.dat";
	private static final String FICHERO_XML = "personasDOM.xml";

	public static void main(String[] args)  {

		try {
			//creaFicheroDatosPersonas();
			creaFicheroXMLPersonas(); // a partir del fichero binario de objetos, crea un fichero xml
			System.out.println("Se ha creado el fichero " + FICHERO_XML);
		}catch ( IOException e) {
			e.printStackTrace();
		}

	}

	// Crea un fichero que almacene varios objetos persona.
	private static void creaFicheroDatosPersonas() throws IOException {
		ObjectOutputStream filtroSalida = new ObjectOutputStream(new FileOutputStream(FICHERO_OBJETOS));

		filtroSalida.writeObject(new Persona("Pedro", 53));
		filtroSalida.writeObject(new Persona("Juan", 26));
		filtroSalida.writeObject(new Persona("Maria", 32));
		filtroSalida.writeObject(new Persona("Laura", 19));

		filtroSalida.close();
	}

	// Tomando como base el fichero anterior, crea un documento XML usando DOM.
	private static void creaFicheroXMLPersonas() throws IOException {

		try {
			// Crea la cabecera del fichero XML en memoria:
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.getDOMImplementation().createDocument(null, "personas", null);
			document.setXmlVersion("1.0");
			annadirElementosAlArbol( document);

			// Vuelca el XML a un fichero XML:
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(new DOMSource(document), new StreamResult(FICHERO_XML));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void annadirElementosAlArbol(Document document) throws IOException, ClassNotFoundException {

		Persona persona;
		Element raiz = document.getDocumentElement();

		try (FileInputStream flujoEntrada = new FileInputStream(FICHERO_OBJETOS);
				ObjectInputStream filtroEntrada = new ObjectInputStream(flujoEntrada);) {
			// A�ade los objetos persona al XML:
			while (flujoEntrada.available() > 0) {
				persona = (Persona) filtroEntrada.readObject();

				Element elementoPersona = document.createElement("persona");
				raiz.appendChild(elementoPersona);

				annadirElementos(elementoPersona, document, "nombre", persona.getNombre());
				annadirElementos(elementoPersona, document, "edad", String.valueOf(persona.getEdad()));
			}
		}
	}

	// Annade dos hijos al elemento element, uno el primero de tipo Element y el
	// segundo Text
	private static void annadirElementos(Element persona, Document document, String datoEmple, String valor) {
		Element elem = document.createElement(datoEmple);
		persona.appendChild(elem);
		elem.setTextContent(valor);
	}

	

}
