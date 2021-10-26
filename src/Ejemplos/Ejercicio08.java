package Ejemplos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio08 {
    public static void main(String[] args) {

        String linea;
        File file;

        try (BufferedReader br = new BufferedReader(new FileReader("fichero.txt"))) {

            linea = br.readLine();
            while (linea != null) {
                file = new File(linea);

                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    System.out.println("El fichero ya existe.");
                }

                linea = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
