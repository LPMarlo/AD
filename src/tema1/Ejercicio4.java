package tema1;

import java.io.*;
import java.util.Scanner;

public class Ejercicio4 {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Nombre de archivo para escribir cadena en bytes:");
        String nombreArchivoEscritura = sc.nextLine();

        System.out.println("Escribe mensaje: ");
        String mensaje = sc.nextLine();

        escribirCadenaEnBytes(nombreArchivoEscritura, mensaje);

        System.out.println("Nombre archivo de bytes para leer en cadena:");
        String nombreArchivoLectura = sc.nextLine();

        leerBytesPorCadena(nombreArchivoLectura);
    }

    public static void escribirCadenaEnBytes(String nombreArchivo, String mensaje) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(nombreArchivo))) {
            bos.write(mensaje.getBytes());
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void leerBytesPorCadena(String nombreArchivo) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(nombreArchivo))) {
            byte[] b = bis.readAllBytes();
            String cad = new String(b, java.nio.charset.StandardCharsets.UTF_8);
            System.out.println(cad);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
