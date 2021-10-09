package tema1;

import java.io.*;
import java.util.Arrays;

public class Ejercicio4 {
    public static void main(String[] args) {

        BufferedOutputStream bos = null;
        String cadena = "Hello World";
        BufferedInputStream bis;


        try {

            bos = new BufferedOutputStream(new FileOutputStream("out.dat"));
            bis = new BufferedInputStream(new FileInputStream("out.dat"));

            bos.write(cadena.getBytes());
            System.out.println(Arrays.toString(bis.readAllBytes()));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
/*
    private static void leerArchivo() {
        BufferedInputStream bis = null;
        BufferedWriter bw = null;
        BufferedReader br = null;
        try {

            String linea;
            bis = new BufferedInputStream(new FileInputStream("in.dat"));
            br = new BufferedReader(new FileReader("out.dat"));
            bw = new BufferedWriter(new FileWriter("out.dat"));

            System.out.println(new String(bis.readAllBytes()));


        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
 */
}
