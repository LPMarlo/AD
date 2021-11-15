package Tema1.Ejemplos.DOM_SAX;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * 
 */

/**
 * @author JESUS
 *
 */
public class LeerXML_DOM {

	public static int numProfes = 0;
	public static int numProfesDosModulos = 0;
	public static int numProfesSinModulo = 0;
	public static int numProfesUnApellido = 0;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File("archivo.xml"));
			
			document.getDocumentElement().normalize();			
			Node raiz = document.getDocumentElement();
			
			System.out.println("Raiz: " + raiz.getNodeName());
			System.out.println("Total apellidos: " + ((Element) raiz).getElementsByTagName("apellidos").getLength());
			
			NodeList elementos = raiz.getChildNodes();
			
			for (int i=0; i<raiz.getChildNodes().getLength(); i++) {
				Node nodo = elementos.item(i);
				if (nodo.getNodeType()==Node.ELEMENT_NODE) {
					numProfes++;
					procesaProfe((Element) nodo);
				}
			}			
			
			System.out.println("Total profesores: " + numProfes);
			System.out.println("Profes que imparten al menos dos módulos: " + numProfesDosModulos);
			System.out.println("Profes que NO imparten módulos: " + numProfesSinModulo);
			System.out.println("Profes con un único apellido: " + numProfesUnApellido);
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
		}

	}
	
	public static void procesaProfe(Element profe) {
		System.out.println("Por cada profesor ... ");
		
		// Obtengo las etiquetas directamente
		int apellidos = profe.getElementsByTagName("apellidos").getLength();
		
		if (apellidos==1) {
			numProfesUnApellido++;
		}
		
		// U obtengo los hijos y los recorro.
		NodeList datosProfe = profe.getChildNodes();
		for (int j=0; j<datosProfe.getLength(); j++) {
			Node dato = datosProfe.item(j);
			if (dato.getNodeType()==Node.ELEMENT_NODE){
				
				if(dato.getNodeName().equals("imparte")) {
					cuentaModulos((Element) dato);
				}
			}
		}
	}
	
	public static void cuentaModulos(Element imparte) {
		int modulos = imparte.getElementsByTagName("modulo").getLength();
		if(modulos>=2) {
			numProfesDosModulos++;
		}else {
			if(modulos==0) {
				numProfesSinModulo++;
			}
		}
	}

}
