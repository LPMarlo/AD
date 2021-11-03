package Ejemplos.Tema1.Ejercicio10;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainAlumnos {

    private static ArrayList<Alumno> alumnos;
    public static void main(String[] args) {
        SimpleDateFormat fecha1 = new SimpleDateFormat("ddMMyyyy");
        try {
            Date fMatricula1 = fecha1.parse("101010");
            Date fMatricula2 = fecha1.parse("111011");
            Date fMatricula3 = fecha1.parse("121012");
            Date fMatricula4 = fecha1.parse("131013");
            Date fMatricula5 = fecha1.parse("141014");

            List<ModuloProfesional> modulos = new ArrayList<>();
            modulos.add(new ModuloProfesional(150, "AD"));
            modulos.add(new ModuloProfesional(150, "SGE"));
            modulos.add(new ModuloProfesional(150, "DI"));
            modulos.add(new ModuloProfesional(150, "PMDM"));
            modulos.add(new ModuloProfesional(150, "EIE"));
            List<ModuloProfesional> modulosVacio = new ArrayList<>();

            alumnos = new ArrayList<>();
            alumnos.add(new Alumno("A", "B", "C", 25, fMatricula1, modulos));
            alumnos.add(new Alumno("D", "E", "F", 24, fMatricula2, modulos));
            alumnos.add(new Alumno("G", "H", "I", 23, fMatricula3, modulos));
            alumnos.add(new Alumno("J", "K", "L", 22, fMatricula4, modulosVacio));
            alumnos.add(new Alumno("M", "N", "O", 21, fMatricula5, modulosVacio));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        crearFichero();
        leerFichero();
        crearFicheroXML();
    }

    private static void crearFichero() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ficheros\\alumnos.ser"))) {
            for (Alumno alumno : alumnos) {
                oos.writeObject(alumno);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void leerFichero() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ficheros\\alumnos.ser"))) {
            while (true) {
                Alumno alumno = (Alumno) ois.readObject();
                System.out.println(alumno);
            }
        } catch (EOFException e) {
            System.out.println("Fin");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void crearFicheroXML() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ficheros\\alumnos.ser"));
             PrintWriter pw = new PrintWriter(new FileWriter("ficheros\\alumnos.xml" ))) {
            pw.println("<alumnos>");
            while (ois.available() == 0) {
                Alumno alumno = (Alumno) ois.readObject();
                pw.println(alumno.toStringXML());
            }
            pw.println("</alumnos>");
        } catch (EOFException e) {
            System.out.println("Fin");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
