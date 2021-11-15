package Tema1.Examen;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.lang.annotation.Documented;

public class Ejercicio3 {
    public static void main(String[] args) {
        try (BufferedReader brClientes = new BufferedReader(new FileReader("ficheros\\clientes.csv"))) {
            String linea;
            while ((linea = brClientes.readLine()) != null) {
                String[] info = linea.split(";");
                generaXML(info[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void generaXML(String idCliente) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       // DocumentBuilder builder = factory.newDocumentBuilder();
    }
}
