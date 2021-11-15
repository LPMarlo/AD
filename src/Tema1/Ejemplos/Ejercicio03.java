package Tema1.Ejemplos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio03 {
	
	private static Scanner teclado = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		String ficheroOrigen;
		String ficheroDestino;
		
		System.out.println("Introduce el archivo de origen: ");
		ficheroOrigen = teclado.nextLine();
		
		System.out.println("Introduce el archivo de destino: ");
		ficheroDestino = teclado.nextLine();
	}
	
	private static void imprimirFicheroPorLineas(String ficheroOrigen, String ficheroDestino) {
		String linea;
		try (BufferedReader filtroLectura = new BufferedReader(new FileReader(ficheroOrigen));
				BufferedWriter filtroEscritura = new BufferedWriter(new FileWriter(ficheroDestino))){
			
			linea = filtroLectura.readLine();
			while (linea!=null) {
				filtroEscritura.write(linea);
				filtroEscritura.newLine();
				linea=filtroLectura.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
