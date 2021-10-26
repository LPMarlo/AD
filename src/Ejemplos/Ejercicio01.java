package Ejemplos;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio01 {
	public static void main(String[] args) {
		imprimirFicheroPorLineas("HelloWorld");
	}
	
	private static void imprimirFicheroPorLineas(String nombreFichero ) {
		String linea;
		try (FileReader flujoLectura = new FileReader(nombreFichero);
				BufferedReader filtroLectura = new BufferedReader(flujoLectura);){
			linea = filtroLectura.readLine();
			while (linea!=null) {
				System.out.println(linea);
				linea=filtroLectura.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("No existe el fichero "+ nombreFichero);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
