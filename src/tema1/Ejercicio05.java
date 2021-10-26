package tema1;

import java.io.*;
import java.util.Scanner;

// Autor: Marlo Lebrón Pareja
public class Ejercicio05 {

    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Introduce nombre de imagen: ");
        String img = sc.nextLine();

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(img));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("copia-"+img))) {
            bos.write(bis.readAllBytes());
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
