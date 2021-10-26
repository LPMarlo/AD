package Ejemplos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ejercicio09 {
    public static void main(String[] args) {
        String linea;
        File file;

        try (BufferedReader br = new BufferedReader(new FileReader("coches.txt"))) {

            linea = br.readLine();
            while (linea != null) {
                String[] datosCoche = linea.split(";");
                LocalDate fecha = LocalDate.of(Integer.parseInt(datosCoche[2].substring(4, 8)), Integer.parseInt(datosCoche[2].substring(2, 4)), Integer.parseInt(datosCoche[2].substring(0, 2)));
                Coche nuevoCoche = new Coche(datosCoche[0],datosCoche[1], fecha,Integer.parseInt(datosCoche[3]),Integer.parseInt(datosCoche[4]));
                System.out.println(nuevoCoche.toString());
                linea = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
