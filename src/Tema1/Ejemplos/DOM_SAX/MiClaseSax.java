package Tema1.Ejemplos.DOM_SAX;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MiClaseSax extends DefaultHandler {

	private String etiquetaActual;
	private BufferedWriter bf;

	@Override
	public void startDocument() throws SAXException {
		Path ruta = Paths.get("empleados.txt");
		try {
			bf = Files.newBufferedWriter(ruta);
		} catch (IOException e) {
			throw new SAXException("No se pudo crear el fichero");
		}
	}

	@Override
	public void endDocument() throws SAXException {
		try {
			bf.close();
		} catch (IOException e) {
			throw new SAXException("ERROR AL ESCRIBIR EN EL FICHERO");
		}
	}

	@Override
	public void startElement(String uri, String localName, String nombreEtiqueta, Attributes atributos)
			throws SAXException {

		etiquetaActual = nombreEtiqueta;
		System.out.println("Empieza el elemento " + nombreEtiqueta);
		for (int j = 0; j < atributos.getLength(); j++) {
			
			System.out.println("\t"+ atributos.getQName(j)+ " " + atributos.getValue(j) );
		}
	}

	@Override
	public void endElement(String uri, String localName, String nombreEtiqueta) throws SAXException {
		System.out.println("Etiqueta de fin " + nombreEtiqueta);

		if (nombreEtiqueta.equals("empleado")) {
			System.out.println();
			try {
				bf.newLine();
			} catch (IOException e) {
				throw new SAXException("ERROR AL ESCRIBIR EN EL FICHERO");
			}
		}
		etiquetaActual = "";
	}

	@Override
	public void characters(char[] info, int inicio, int fin) throws SAXException {

		String contenido = new String(info, inicio, fin);
		contenido = contenido.replaceAll("[\t\n]", "").trim(); // eliminamos los saltos de linea, tabuladores....

		if (contenido.length() > 0) {

			try {

				switch (etiquetaActual) {
				case "nombre":
					System.out.print(contenido + " ");
					bf.write(contenido + " ");
					break;
				case "apellido":
					System.out.print(contenido + " ");
					bf.write(contenido + " ");
					break;
				}
			} catch (IOException e) {
				throw new SAXException("ERROR AL ESCRIBIR EN EL FICHERO");
			}

		}

	}

}
