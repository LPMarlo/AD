package Tema2.Ejercicios.Examen;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author JESUS
 *
 */
public class TestConexiones {
	public static void main(String[] args) {
		Connection con = null;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Introduzca la conexión que desea testear:");
		System.out.println("1. Local");
		System.out.println("2. Externa");
		
		int conexion = Integer.parseInt(sc.nextLine());
		
		try {			
			con = obtenerConexion(conexion);
			System.out.println("Conexión testeada exitosamente.");
		} catch (SQLException e) {
			System.out.println("No se ha podido crear la conexión.");
			e.printStackTrace();
		}finally {
			if (con != null) {
				try {
					con.close();
					sc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	/**
	 * Recibe el tipo de conexión y devuelve una conexión hacia el destino indicado.
	 */
	public static Connection obtenerConexion(int numConexion) throws SQLException {

		// Lectura de archivo
		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File("conexiones\\datos_conexiones.xml"));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			System.out.println(e.getMessage());
		}

		// Los dos tipos de datos en una lista
		NodeList datos = document.getElementsByTagName("datos");

		// Obtener elemento de la lista
		Element local = (Element) datos.item(0);
		Element externa = (Element) datos.item(1);

		// Al tener los mismos elementos solo hay que diferenciar entre local y externa para obtener los datos
		Element host = null;
		Element puerto = null;
		Element nombreBD = null;
		Element usuario = null;
		Element password = null;
		switch (numConexion) {
			case 1 -> {
				host = (Element) local.getElementsByTagName("host").item(0);
				puerto = (Element) local.getElementsByTagName("puerto").item(0);
				nombreBD = (Element) local.getElementsByTagName("nombre_bd").item(0);
				usuario = (Element) local.getElementsByTagName("usuario").item(0);
				password = (Element) local.getElementsByTagName("password").item(0);
			}
			case 2 -> {
				host = (Element) externa.getElementsByTagName("host").item(0);
				puerto = (Element) externa.getElementsByTagName("puerto").item(0);
				nombreBD = (Element) externa.getElementsByTagName("nombre_bd").item(0);
				usuario = (Element) externa.getElementsByTagName("usuario").item(0);
				password = (Element) externa.getElementsByTagName("password").item(0);
			}
		}
		// Una vez obtenidos los elementos se obtiene el contenido con getTextContent()
		Connection c = DriverManager.getConnection("jdbc:mysql://"+host.getTextContent()+":"+puerto.getTextContent()+"/"+nombreBD.getTextContent()+"", usuario.getTextContent(), password.getTextContent());
		return c;
	}
	
	//////////////////////////////// INTRODUCE AQUÍ TU RESPUESTA.
	/**
	 * ¿Crees que aporta alguna ventaja registrar los datos de un documento? ¿Cuál? ¿Por qué?
	 * ¿Crees que tiene algún inconveniente? ¿Cuál? ¿Por qué?
	 */
}
