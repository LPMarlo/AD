package Ejemplos.Tema1;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Ejercicio04 {
	public static void main(String[] args) {
		byte[] buffer = new byte[1024*32];
		Arrays.fill(buffer, Byte.parseByte("1"));
		BufferedOutputStream bos = null;
		
		try {
			bos = new BufferedOutputStream(new FileOutputStream("ejemplo_buffered.dat"));
			bos.write("Hola".getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		leerArchivo();
	}
	
	private static void leerArchivo() {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("ejemplo_buffered.dat")))){
			String linea;
			linea = br.readLine();
			while (linea!=null) {
				linea=br.readLine();
				System.out.println(linea);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
