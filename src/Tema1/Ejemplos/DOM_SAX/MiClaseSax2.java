package Tema1.Ejemplos.DOM_SAX;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MiClaseSax2 extends DefaultHandler {

	private String etiquetaActual;
	private int edadMayor;
	private String nombreDelMayor;
	private String nombreActual;
	

	@Override
	public void startDocument() throws SAXException {
		edadMayor=0;
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("La edad mayor de todas es " + edadMayor + " y la persona de mayor edad es " + nombreDelMayor);
	}

	@Override
	public void startElement(String uri, String localName, String nombreEtiqueta, Attributes atributos)
			throws SAXException {

		etiquetaActual = nombreEtiqueta;
//		System.out.println("Empieza el elemento " + nombreEtiqueta);
//		for (int j = 0; j < atributos.getLength(); j++) {
//			
//			System.out.println("\t"+ atributos.getQName(j)+ " " + atributos.getValue(j) );
//		}
	}

	@Override
	public void endElement(String uri, String localName, String nombreEtiqueta) throws SAXException {
//		System.out.println("Etiqueta de fin " + nombreEtiqueta);

		etiquetaActual = "";
	}

	@Override
	public void characters(char[] info, int inicio, int fin) throws SAXException {

		String contenido = new String(info, inicio, fin);
		contenido = contenido.replaceAll("[\t\n]", "").trim(); // eliminamos los saltos de linea, tabuladores....
		int edad;
		
		if (contenido.length() > 0) {
			switch (etiquetaActual) {
				case "edad" -> {
					edad = Integer.parseInt(contenido);
					if (edad > edadMayor) {
						edadMayor = edad;
						nombreDelMayor = nombreActual;
					}
				}
				case "nombre" -> {
					nombreActual = contenido;
				}
			}
			
				

		}

	}

}
