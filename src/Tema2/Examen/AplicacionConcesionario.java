package Tema2.Examen;

import java.util.Scanner;

/**
 * @author JESUS
 *
 */
public class AplicacionConcesionario {

	/** Mostrar� el siguiente men� al usuario mientras �ste no decida salir.
	 * 
	 * 1. Registra nuevo veh�culo.
	 * 2. Registra nueva venta.
	 * 3. Listar ventas de una marca.
	 * 4. Calcular total de ventas de un empleado.
	 * 5. Informe de situaci�n.
	 * 6. Salir.
	 * @param args
	 */
	// 5%
	public static void main(String[] args) {
		GestorConcesionario gc = new GestorConcesionario();
		int opc = -1;
		do {
			opc = muestraMenu();			
		}while(opc!=6);
		System.out.println("�Hasta la pr�xima!");		
	}
	
	public static int muestraMenu(){
		Scanner sc = new Scanner(System.in);
		int opc = 0;
		System.out.println("Men�");
		System.out.println("1. Registra nuevo veh�culo.");
		System.out.println("2. Registra nueva venta.");
		System.out.println("3. Listar ventas de una marca.");
		System.out.println("4. Calcular total de ventas de un empleado.");
		System.out.println("5. Informe de situaci�n.");
		System.out.println("6. Salir.");
		
		opc = Integer.parseInt(sc.nextLine());
		return opc;
		
	}

}
