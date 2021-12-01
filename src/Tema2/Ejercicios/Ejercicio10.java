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

        // Conexión
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pruebaconexionbd", "root", "");

            int opt = 0;
            while (opt != 7) {
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

    /**
     * Muestra el menú principal.
     */
    private static void showMenu() {
        System.out.println("""
                1. Insertar alumno.
                2. Insertar módulo.
                3. Matricular alumno en módulo.
                4. Listar los módulos de un alumno.
                5. Borrar matrícula.
                6. Listado de alumnos y matrículas.
                7. Salir
                """);
    }

    /**
     * Solicita la opción y dependiendo de esta ejecuta un método u otro.
     * @param con (Conexion)
     * @return opt (Opcion elegida)
     */
    private static int menu(Connection con) {
        System.out.println("Opción: ");
        int opt = Integer.parseInt(sc.nextLine());

        switch (opt) {
            case 1 -> insertarAlumno(con);
            case 2 -> insertarModulo(con);
            case 3 -> matricularAlumnoEnModulo(con);
            case 4 -> listaModulosDeAlumno(con);
            case 5 -> borrarMatricula(con);
            case 6 -> listaAlumnosYModulos(con);
            case 7 -> System.out.println("Ha salido con éxito.");
            default -> System.out.println("Opción incorrecta.");
        }
        return opt;
    }

    /**
     *
     * @param con (Conexion)
     */
    private static void listaAlumnosYModulos(Connection con) {
        try {
            //Comprueba que el ya no hay un registro
            String sql = "SELECT CONCAT(a.apellido1, a.apellido2, ', ', a.nombre) as alumno, modulo.descripcion FROM pruebaconexionbd.alumnos a LEFT OUTER JOIN pruebaconexionbd.matricula m on a.id = m.ID LEFT JOIN pruebaconexionbd.modulo_profesional modulo on modulo.CODIGO=m.codigo ORDER BY a.id"; //TODO
            Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery(sql);

            boolean hayFila = false;
            //int contador = 0;
            while (rs.next()) {
                if (rs.getString(2) != null) {
                    System.out.println("Alumno: " + rs.getString(1) + " | Módulo: " + rs.getString(2));
                } else if (rs.getString(1) != null) {
                    System.out.println("Alumno: " + rs.getString(1) + " | Ningún módulo matriculado");
                }
                //contador++;
                hayFila =true;
            }
            //System.out.println(contador);
            if (!hayFila) System.out.println("No se han encontrado resultados.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void matricularAlumnoEnModulo(Connection con) {

        // Datos
        System.out.println("Número escolar: ");
        int id = Integer.parseInt(sc.nextLine()) / 2;

        System.out.println("Código módulo: ");
        String codigo = sc.nextLine();

        try {
            // Comprueba que el modulo y el alumno existe
            if (comprobarCodigoModulo(codigo, con) && comprobarIdAlumno(id, con)) {
                //Comprueba que el ya no hay un registro
                String sql = "SELECT id FROM pruebaconexionbd.matricula WHERE id=? AND codigo='" + codigo + "'";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    sql = "INSERT INTO pruebaconexionbd.matricula(id, codigo) VALUES('" + id + "', '" + codigo + "')";
                    Statement st = con.createStatement();
                    st.executeUpdate(sql);
                    st.close();
                } else {
                    System.out.println("El alumno ya está matriculado en el módulo seleccionado.");
                }
                ps.close();
            } else {
                System.out.println("Error. No existe un código o un número escolar compatible con los datos introducidos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listaModulosDeAlumno(Connection con) {

        // Datos
        System.out.println("Número escolar: ");
        int id = Integer.parseInt(sc.nextLine()) / 2; // Al dividir entre dos se obtiene el ID del alumno

        try {
            if (comprobarIdAlumno(id, con)) {
                String sql = "SELECT descripcion FROM pruebaconexionbd.modulo_profesional JOIN pruebaconexionbd.matricula m on modulo_profesional.CODIGO = m.CODIGO JOIN pruebaconexionbd.alumnos a on a.id = m.ID WHERE a.id = " + id + "";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);

                System.out.println("Modulos:");
                while (rs.next()) {
                    System.out.println("- " + rs.getString(1));
                }
            } else {
                System.out.println("Error. No existe el número escolar introducido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void borrarMatricula(Connection con) {

        // Datos
        System.out.println("Número escolar: ");
        int id = Integer.parseInt(sc.nextLine()) / 2; // Al dividir entre dos se obtiene el ID del alumno

        System.out.println("Código módulo: ");
        String codigo = sc.nextLine();

        try {
            if (comprobarIdAlumno(id, con) && comprobarCodigoModulo(codigo, con)) {
                String sql = "DELETE FROM pruebaconexionbd.matricula WHERE CODIGO = '" + codigo + "' AND id = " + id + ";";
                Statement st = con.createStatement();
                st.executeUpdate(sql);
            } else {
                System.out.println("Error. No existe un código o un número escolar compatible con los datos introducidos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertarModulo(Connection con) {

        // Datos
        System.out.println("Código: ");
        String codigo = sc.nextLine();

        System.out.println("Descripción: ");
        String descripcion = sc.nextLine();

        try {
            if (comprobarCodigoModulo(codigo, con)) {
                System.out.println("Error. Un módulo con ese código ya existe.");
            } else {
                String sql = "INSERT INTO  pruebaconexionbd.modulo_profesional(CODIGO, DESCRIPCION) VALUES('" + codigo + "','" + descripcion + "');";
                Statement st2 = con.createStatement();
                st2.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertarAlumno(Connection con) {

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

            String sql = "INSERT INTO  pruebaconexionbd.alumnos(nombre, apellido1, apellido2, email, edad) VALUES('" + nombre + "','" + apellido1 + "','" + apellido2 + "','" + email + "'," + edad + ")";
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean comprobarIdAlumno(int id, Connection con) throws SQLException {
        String sql = "SELECT id FROM pruebaconexionbd.alumnos WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public static boolean comprobarCodigoModulo(String codigo, Connection con) throws SQLException {
        String sql = "SELECT CODIGO FROM pruebaconexionbd.modulo_profesional WHERE CODIGO=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, codigo);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
