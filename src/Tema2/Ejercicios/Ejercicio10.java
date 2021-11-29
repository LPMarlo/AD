package Tema2.Ejercicios;

import java.sql.*;
import java.util.Scanner;

public class Ejercicio10 {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opt = 0;
        while (opt != 6) {
            showMenu();
            opt = menu();
        }
    }

    private static void showMenu() {
        System.out.println("1. Insertar alumno.\n" +
                "2. Insertar módulo.\n" +
                "3. Matricular alumno en módulo.\n" +
                "4. Listar los módulos de un alumno.\n" +
                "5. Borrar matrícula.\n" +
                "6. Salir");
    }

    private static int menu() {
        System.out.println("Opción: ");
        int opt = Integer.parseInt(sc.nextLine());

        switch (opt) {
            case 1:
                insertAlumno();
                break;
            case 2:
                insertModulo();
                break;
            case 3:
                addModuloAAlumno();
                break;
            case 4:
                listModulosAlumno();
                break;
            case 5:
                deleteMatricula();
                break;
            case 6:
                System.out.println("Ha salido con éxito.");
                break;
            default:
                System.out.println("Opción incorrecta.");
                break;
        }
        return opt;
    }

    private static void addModuloAAlumno() {
    }

    private static void listModulosAlumno() {
    }

    private static void deleteMatricula() {
    }

    private static void insertModulo() {
    }

    private static void insertAlumno() {
        int NUMESC = 0;

        System.out.println("Nombre: ");
        String nombre = sc.nextLine();

        System.out.println("Apellido 1: ");
        String apellido1 = sc.nextLine();

        System.out.println("Apellido 2: ");
        String apellido2 = sc.nextLine();

        System.out.println("Email: ");
        String email = sc.nextLine();

        System.out.println("Edad: ");
        int edad = Integer.parseInt(sc.nextLine());

        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pruebaconexionbd", "root", "");

            String sql = "INSERT INTO  pruebaconexionbd.alumnos(nombre, apellido1, apellido2, email, edad) VALUES('"+nombre+"','"+apellido1+"','"+apellido2+"','"+email+"',"+edad+");";
            Statement pst = con.createStatement();
            pst.executeUpdate(sql);
            System.out.println();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
