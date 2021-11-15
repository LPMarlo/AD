package Tema1.Ejemplos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ejercicio02 {
	public static void main(String[] args) {
		imprimirFicheroPorLineas("origen.txt");
	}
	
	private static void imprimirFicheroPorLineas(String nombreFichero ) {
		String linea;
		try (BufferedReader filtroLectura = new BufferedReader(new FileReader(nombreFichero));
				BufferedWriter filtroEscritura = new BufferedWriter(new FileWriter("destino.txt"))){
			
			linea = filtroLectura.readLine();
			while (linea!=null) {
				filtroEscritura.write(linea);
				filtroEscritura.newLine();
				linea=filtroLectura.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("No existe el fichero "+ nombreFichero);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
