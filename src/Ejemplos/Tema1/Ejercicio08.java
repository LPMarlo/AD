package Ejemplos.Tema1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio08 {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("ficheros\\fichero.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                File file = new File("ficheros\\"+linea);

                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    System.out.println("El fichero ya existe.");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
