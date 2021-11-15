package Tema1.Examen;

import java.io.*;

public class Ejercicio1 {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("ficheros\\supermercado.csv"));
             PrintWriter pwClientes = new PrintWriter(new FileWriter("ficheros\\clientes.csv"));
             PrintWriter pwProductos = new PrintWriter(new FileWriter("ficheros\\productos.csv"));
             PrintWriter pwPedidos = new PrintWriter(new FileWriter("ficheros\\pedidos.csv"));) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] SLinea = linea.split(";");
                if (SLinea[0].equals("PR")) { //Caso Producto
                    pwProductos.println(SLinea[1] + ";" + SLinea[2] + ";" + SLinea[3]);
                } else if (SLinea[0].equals("CL")) { // Caso Cliente
                    pwClientes.println(SLinea[1] + ";" + SLinea[2] + ";" + SLinea[3] + ";" + SLinea[4]);
                } else if (SLinea[0].equals("PE")) { // Caso Pedido
                    pwPedidos.println(SLinea[1] + ";" + SLinea[2] + ";" + SLinea[3] + ";" + SLinea[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
