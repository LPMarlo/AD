package Ejemplos.Tema1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Ejercicio05 {
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		System.out.println("Fichero a duplicar: ");
		String fichero = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(fichero));
				PrintWriter pw = new PrintWriter(new FileWriter(fichero))) {

			String linea;
			linea = br.readLine();
			while (linea!=null) {
				linea=br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("No existe el fichero "+ fichero);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
