package Ejemplos;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio7 {

    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Introduce el numero de ficheros a crear: ");
        int num = Integer.parseInt(sc.nextLine());

        File file2 = null;
        File file = null;
        for (int i = 0; i < num; i++) {
            System.out.println("Nombre del fichero [" + (i+1) + "]:");
            String nombre = sc.nextLine();

            if (i==0) {
                file = new File(nombre);
                file.mkdir();
            } else {
                file = new File(file2.getPath()+"/"+nombre);
                file.mkdir();
            }

            file2 = file;
        }
    }
}
