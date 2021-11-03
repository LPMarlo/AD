package Ejemplos.Tema1.Ejercicio12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio12 {
    public static void main(String[] args) {
        //Datos Profesores
        int numProfesores = 0;
        int numProfMinDosModulos = 0;
        int numProfCeroModulos = 0;
        int numProfUnApellido = 0;

        //Leer archivo XML
        try (BufferedReader br = new BufferedReader(new FileReader("ficheros\\archivo.xml"))) {
            String linea;

            //Contadores por profesor
            int numModulos = 0;
            int numApellidos = 0;

            while ((linea = br.readLine()) != null) {
                if (linea.contains("</modulo>")) {
                    numModulos++; //Contador modulos
                } else if (linea.contains("</apellidos>")) {
                    numApellidos++; // Contador de apellidos
                } else if (linea.contains("</profesor>")) {
                    if (numModulos >= 2) {
                        numProfMinDosModulos++; //Añade +1 profesor con al menos dos modulos
                    } else if (numModulos == 0) {
                        numProfCeroModulos++; // Añade +1 profesor con 0 modulos
                    }

                    // Añade +1 profesor si tiene solo un apellido
                    if (numApellidos == 1) numProfUnApellido++;

                    // Contador profesores
                    numProfesores++;

                    // Valor a 0 para el siguiente profesor
                    numModulos = 0;
                    numApellidos = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Mostrar datos profesores
        System.out.println("Total profesores: " + numProfesores);
        System.out.println("Profesores que imparten al menos dos modulos: " + numProfMinDosModulos);
        System.out.println("Profesores no imparten ningun modulo: " + numProfCeroModulos);
        System.out.println("Profesores con un apellido: " + numProfUnApellido);
    }
}
