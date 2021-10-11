package Ejemplos;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Ejemplo {
	public static void main(String[] args) {
		BufferedInputStream bin = null;
		final int TAM = 1024 * 16;
		
		try {
			bin = new BufferedInputStream(new FileInputStream("img.jpg"));
			int cantidadBytesLeidos = 0;
			byte[] buffer = new byte[TAM];
			
			int i = 1;
			
			while ((cantidadBytesLeidos = bin.read(buffer, 0, TAM)) != -1) {
				System.out.println("Leidos " + cantidadBytesLeidos + " Bytes en la iteracion " + i++ + "-�sima");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bin != null) {
				try {
					bin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
