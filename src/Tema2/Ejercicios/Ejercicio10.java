package Tema2.Ejercicios;

import java.sql.*;
import java.util.Scanner;

/*
A partir de la tabla ALUMNOS crear las dos tablas facilitadas en este script.
Incluir los módulos que faltan y añadir una columna a ALUMNOS que sea NUMESC (número escolarización, int(20)) que será clave única de valor igual que el ID*2.
Se pide implementar un programa que muestre al usuario el siguiente menú.

Seleccione la opción a realizar:
1. Insertar alumno.
2. Insertar módulo.
3. Matricular alumno en módulo.
4. Listar los módulos de un alumno.
5. Borrar matrícula.
6. Listado de alumnos y matrículas.
7. Salir

Para la primera y segunda opción, solicitará los datos del alumno/módulo por teclado y lo insertará, controlando las excepciones (duplicidades).
Para la tercera solicitará el numesc y el codigo del módulo profesional.
Para Listar los módulos pertenecientes a una matrícula  o para borrar los módulos en los que está matriculado un alumno solicitará el numesc.
Para listar todos los alumnos y matrículas se implementará la select necesaria y se recorrerán todas las filas. NO se hará una select dentro de otra.
 */
public class Ejercicio10 {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pruebaconexionbd", "root", "");
            int opt = 0;
            while (opt != 6) {
                showMenu();
                opt = menu(con);
            }
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

    private static void showMenu() {
        System.out.println("""
                1. Insertar alumno.
                2. Insertar módulo.
                3. Matricular alumno en módulo.
                4. Listar los módulos de un alumno.
                5. Borrar matrícula.
                6. Salir
                """);
    }

    private static int menu(Connection con) {
        System.out.println("Opción: ");
        int opt = Integer.parseInt(sc.nextLine());

        switch (opt) {
            case 1 -> insertAlumno(con);
            case 2 -> insertModulo(con);
            case 3 -> addModuloAAlumno(con);
            case 4 -> listModulosAlumno(con);
            case 5 -> deleteMatricula(con);
            case 6 -> System.out.println("Ha salido con éxito.");
            default -> System.out.println("Opción incorrecta.");
        }
        return opt;
    }

    private static void addModuloAAlumno(Connection con) {

        // Datos
        System.out.println("Número Escolar: ");
        int id = Integer.parseInt(sc.nextLine())/2;

        System.out.println("Código: ");
        String codigo = sc.nextLine();

        try {
            //Consulta codigo matricula
            String sql = "SELECT CODIGO FROM pruebaconexionbd.modulo_profesional WHERE CODIGO=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            //Consulta id alumno
            sql = "SELECT id FROM pruebaconexionbd.alumnos WHERE id=?";
            PreparedStatement ps2 = con.prepareStatement(sql);
            ps2.setInt(1, id);
            ResultSet rs2 = ps2.executeQuery();

            // Comprueba que el modulo y el alumno existe
            if (rs.next() && rs2.next()) {
                sql = "INSERT INTO pruebaconexionbd.matricula(id, codigo) VALUES('"+id+"', '"+codigo+"')";
                Statement st = con.createStatement();
                st.executeUpdate(sql);
            }
            else {
                System.out.println("Error. No existe un código o un número escolar compatibles con los datos introducidos.");
            }

            ps2.close();
            ps.close();
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

    private static void listModulosAlumno(Connection con) {
    }

    private static void deleteMatricula(Connection con) {
    }

    private static void insertModulo(Connection con) {

        // Datos
        System.out.println("Código: ");
        String codigo = sc.nextLine();

        System.out.println("Descripción: ");
        String descripcion = sc.nextLine();

        try {
            //Consulta con PreparedStatement
            String sql = "SELECT CODIGO FROM pruebaconexionbd.modulo_profesional WHERE CODIGO=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            // INSERT
            if (rs.next()) {
                System.out.println("Error. Un módulo con ese código ya existe.");
            }
            else {
                sql = "INSERT INTO  pruebaconexionbd.modulo_profesional(CODIGO, DESCRIPCION) VALUES('" + codigo + "','" + descripcion + "');";
                Statement st2 = con.createStatement();
                st2.executeUpdate(sql);
                st2.close();
            }

            ps.close();
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

    private static void insertAlumno(Connection con) {

        // Datos
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

        try {

            String sql = "INSERT INTO  pruebaconexionbd.alumnos(nombre, apellido1, apellido2, email, edad) VALUES('"+nombre+"','"+apellido1+"','"+apellido2+"','"+email+"',"+edad+")";
            Statement st = con.createStatement();
            st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            // Obtener ID
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            String id = rs.getString(1);

            // Update NUMESC
            sql = "UPDATE pruebaconexionbd.alumnos SET NUMESC=id*2 WHERE id=" + id + ";";
            st = con.createStatement();
            int affectedRows = st.executeUpdate(sql);

            if (affectedRows == 0) System.out.println("Creating user failed, no rows affected.");

            st.close();
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
