package Tema1.Ejemplos;

import java.util.StringTokenizer;

public class Ejemplo {
	public static void main(String[] args) {
		String cadena = "ad.ad.asd.gfdg.dfg.cvbncvb.cvb";
		StringTokenizer tokens = new StringTokenizer(cadena,".");

		while (tokens.hasMoreTokens()) {
			System.out.print(tokens.nextToken() + " ");
		}
	}
}
