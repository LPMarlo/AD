package Tema1.Ejemplos;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio06 {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Introduce el numero de ficheros a crear: ");
        int num = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < num; i++) {
            try {
                System.out.println("Nombre del fichero [" + (i+1) + "]:");
                String nombre = sc.nextLine();

                File file = new File(nombre);
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    System.out.println("El fichero ya existe.");
                    i--;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
