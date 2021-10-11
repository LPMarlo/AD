package tema1;

import java.io.*;
import java.util.Arrays;

public class Ejercicio4 {
    public static void main(String[] args) {

        BufferedOutputStream bos = null;
        String cadena = "Hello World";
        BufferedInputStream bis = null;

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
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
