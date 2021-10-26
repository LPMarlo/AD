package tema1.Ejercicio11;

import Ejemplos.EjemploSerializacion.Persona;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio11 {

    private static List<Libro> libros;
    public static void main(String[] args) {
        leerLibros();
        cargarLibros();
        convertirAXML();
    }

    private static void leerLibros() {
        libros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("ficheros\\libros.csv"))) {
            Libro libro;
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] libros_csv = linea.split(";");
                libro = new Libro(libros_csv[0], libros_csv[1], libros_csv[2], Integer.parseInt(libros_csv[3]));
                libros.add(libro);
                linea = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void cargarLibros() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ficheros\\libros.dat"))) {
            for (Libro libro : libros) {
                oos.writeObject(libro);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void convertirAXML() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ficheros\\libros.dat"));
        PrintWriter pw = new PrintWriter(new FileWriter("ficheros\\libros.xml" ))) {
            Libro libro;
            pw.println("<libros>");
            while (ois.available() == 0) {
                libro = (Libro) ois.readObject();
                pw.println(libro.toStringXML());
            }
        } catch (EOFException e) {
            System.out.println("Fin");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter("ficheros\\libros.xml", true))) {
            pw.append("<libros>");
        } catch (EOFException e) {
            System.out.println("Fin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
