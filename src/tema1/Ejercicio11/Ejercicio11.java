package tema1.Ejercicio11;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio11 {

    private static List<Libro> librosCSV;
    private static List<Libro> librosOBJ;

    public static void main(String[] args) {
        leerLibrosCSV();
        generarOBJ();

        leerLibrosOBJ();
        generarCSV();
    }

    private static void leerLibrosOBJ() {
        librosOBJ = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ficheros\\libros.obj"))) {
            while (true) {
                librosOBJ.add((Libro) ois.readObject());
            }
        } catch (EOFException ignored) {

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void generarCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("ficheros\\newLibros.csv"))) {
            for (Libro libro : librosOBJ) {
                pw.write(libro.toString());
                System.out.print(libro);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void leerLibrosCSV() {
        librosCSV = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("ficheros\\libros.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] libros_csv = linea.split(";");
                String[] datosAutor = libros_csv[2].split( " ");
                Autor autor;
                
                if (datosAutor.length>1) {
                    String[] apellidos = new String[datosAutor.length-1];
                    if (datosAutor.length==2) {
                        apellidos[0] = datosAutor[apellidos.length];
                    } else {
                        apellidos[0] = datosAutor[apellidos.length];
                        apellidos[1] = datosAutor[apellidos.length-1];
                    }
                    autor = new Autor(datosAutor[0], apellidos);
                } else {
                    autor = new Autor(datosAutor[0]);
                }

                librosCSV.add(new Libro(libros_csv[0], libros_csv[1], autor, Integer.parseInt(libros_csv[3])));
            }
        } catch (IOException | LibroException e) {
            e.printStackTrace();
        }
    }

    private static void generarOBJ() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ficheros\\libros.obj"))) {
            for (Libro libro : librosCSV) {
                oos.writeObject(libro);
                System.out.print(libro.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}