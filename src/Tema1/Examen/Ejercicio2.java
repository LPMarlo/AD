package Tema1.Examen;

import java.io.*;

public class Ejercicio2 {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("ficheros\\clientes.csv"));
        ) {
            String linea;
            File dir = null;
            PrintWriter pw = null;

            while ((linea = br.readLine()) != null) {
                int i = 1;
                String[] info = linea.split(";");
                dir = new File(info[0]);
                if (!dir.isDirectory()) {
                    dir.mkdir();
                }

                String nombreCarta = info[0]+"\\"+info[1]+info[2]+info[3]+"_"+i+".txt";
                File carta = new File(nombreCarta);
                while (carta.exists()) {
                    i++;
                    nombreCarta = info[0]+"/"+info[1]+info[2]+info[3]+"_"+i+".txt";
                }
                generaCarta(info, nombreCarta,"ficheros\\carta_a_incluir.txt");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generaCarta(String[] info, String nombreCarta, String archivoCarta) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(nombreCarta)));
             BufferedReader br = new BufferedReader(new FileReader(new File(archivoCarta)));) {
            String linea;
            pw.println("Estimado/a " + info [1] + " " + info[2] + " " + info[3] +":");
            while ((linea = br.readLine()) != null) {
                pw.println(linea);
            }
            pw.println("Reciba un cordial saludo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
