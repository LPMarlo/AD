package Tema1.Ejemplos.DOM_SAX;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class RecorridoRecursivo {

	private static final String FICHERO_XML = "archivo.xml";

	public static void main(String[] args) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document arbol = builder.parse(new File(FICHERO_XML));
			Node raiz = arbol.getFirstChild(); // obtiene el primer nodo, raiz
												// del arbol

			System.out.println(raiz.getNodeName());
			mostrarRecursivo(raiz, 1);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void mostrarRecursivo(Node raiz, int ntabulaciones) {

		Node n;

		NodeList listaNodos = raiz.getChildNodes(); // obtiene una lista de nodos hijo del raiz

		for (int i = 0; i < listaNodos.getLength(); i++) { // recorremos la lista de nodos

			n = listaNodos.item(i); // obtiene el nodo i-esimo de la lista

			if (n.getNodeType() == Node.ELEMENT_NODE) {

				escribirTabulaciones(ntabulaciones);
				System.out.println(n.getNodeName());
				mostrarRecursivo(n, ntabulaciones + 1); // llamada recursivamente a mostrar todo lo que n tiene por
														// debajo
			} else {
				if (n.getNodeType() == Node.TEXT_NODE) {
					mostrarTexto(n, ntabulaciones);
				}

			}
		}

	}

	private static void escribirTabulaciones(int ntabulaciones) {
		for (int i = 1; i <= ntabulaciones; i++) {
			System.out.print("\t");
		}

	}

	private static void mostrarTexto(Node n, int ntabulaciones) {
		String texto;
		texto = n.getNodeValue();
		texto = texto.replaceAll("[ \t\n]", "");
		if (texto.length() > 0) {
			escribirTabulaciones(ntabulaciones);
			System.out.println(n.getNodeValue());
		}
	}

}
