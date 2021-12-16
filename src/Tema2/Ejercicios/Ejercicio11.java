package Tema2.Ejercicios;

import java.sql.*;
import java.util.Scanner;

/*
Añadir a la nota matrícula los siguientes campos para permitir mantener un histórico de notas.
Para ello será necesario incluir en el campo matrícula los siguientes campos:
- curso: int(4)
- nota: integer(2,2)
Actualizar las matrículas para el curo 2020.

Añadir a la clase Ejercicio 10:
- Actualizar los métodos del Ejercicio10 anterior para que trabajen teniendo en cuenta esta circunstancia
(matricular alumno, borrar matrícula, listar alumnos).
- Un método para calificar una nota recibiendo curso, numesc, código de módulo y nota.
- Un método actualizarCursoMatricula que recibirá numesc y curso que rematriculará al alumno en el curso pasado
como parámetro de todos aquellos módulos que aún no haya superado.
 */
public class Ejercicio11 {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Conexion
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pruebaconexionbd", "root", "");

            int opt = 0;
            while (opt != 9) {
                showMenu();
                opt = menu(con);
            }
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error. Algo salió mal");
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
     * Muestra el menu principal por terminal.
     */
    private static void showMenu() {
        System.out.println("""
                1. Insertar alumno.
                2. Insertar modulo.
                3. Matricular alumno en modulo.
                4. Listar los modulos de un alumno.
                5. Borrar matricula.
                6. Listado de alumnos y matriculas.
                7. Calificar nota de alumno.
                8. Actualizar curso de matricula
                9. Salir
                """);
    }

    /**
     * Solicita la opcion y dependiendo de esta ejecuta un metodo u otro.
     * @param con (Conexion)
     * @return opt (Opcion elegida)
     */
    private static int menu(Connection con) {
        System.out.println("Opcion: ");
        int opt = Integer.parseInt(sc.nextLine());

        switch (opt) {
            case 1 -> insertarAlumno(con);
            case 2 -> insertarModulo(con);
            case 3 -> matricularAlumnoEnModulo(con);
            case 4 -> listaModulosDeAlumno(con);
            case 5 -> borrarMatricula(con);
            case 6 -> listaAlumnosYModulos(con);
            case 7 -> calificarNota(con);
            case 8 -> actualizarCursoMatricula(con);
            case 9 -> System.out.println("Ha salido con exito.");
            default -> System.out.println("Opcion incorrecta.");
        }
        return opt;
    }

    private static void actualizarCursoMatricula(Connection con) {
        System.out.println("Curso: ");
        int curso = Integer.parseInt(sc.nextLine());

        System.out.println("Numero escolar: ");
        int id = Integer.parseInt(sc.nextLine()) / 2;

        try {
            if (comprobarIdAlumno(id, con)) {

                // Comprobacion si el alumno ya esta matriculado en el modulo
                String sql = "SELECT id, codigo, curso, nota FROM pruebaconexionbd.matricula WHERE id=? and curso=? ";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ps.setInt(2, curso-1);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    if (rs.getInt(1) == id && rs.getInt(3) == curso && rs.getInt(4) < 5) {
                        sql = "INSERT INTO pruebaconexionbd.matricula(ID, CODIGO, curso) VALUES (?, ?, ?)";
                        PreparedStatement ps2 = con.prepareStatement(sql);
                        ps2.setInt(1, id);
                        ps2.setString(2, rs.getString(2));
                        ps2.setInt(3, curso);

                        ps2.executeUpdate();
                        ps2.close();
                    }
                }

                ps.close();

            } else {
                System.out.println("Error. No existe un codigo o un numero escolar compatible con los datos introducidos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void calificarNota(Connection con) {

        System.out.println("Curso: ");
        int curso = Integer.parseInt(sc.nextLine());

        System.out.println("Numero escolar: ");
        int id = Integer.parseInt(sc.nextLine()) / 2;

        System.out.println("Codigo modulo: ");
        String codigo = sc.nextLine();

        System.out.println("Nota: ");
        int nota = Integer.parseInt(sc.nextLine());

        try {
            if (comprobarCodigoModulo(codigo, con) && comprobarIdAlumno(id, con)) {

                // Comprobacion si el alumno ya esta matriculado en el modulo
                String sql = "UPDATE pruebaconexionbd.matricula SET curso=?, nota=? WHERE id=? and CODIGO=? ";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, curso);
                ps.setInt(2, nota);
                ps.setInt(3, id);
                ps.setString(4, codigo);

                ps.executeUpdate();
                ps.close();
            } else {
                System.out.println("Error. No existe un codigo o un numero escolar compatible con los datos introducidos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Listado de Alumnos mostrando los modulos que tienen, en caso de no tenerlo tambien se indica.
     * @param con (Conexion)
     */
    private static void listaAlumnosYModulos(Connection con) {
        try {

            // Consulta (ordenada por alumno)
            String sql = "SELECT CONCAT(a.apellido1, ', ', a.nombre) as alumno, modulo.descripcion, a.id, m.curso, m.nota FROM pruebaconexionbd.alumnos a LEFT OUTER JOIN pruebaconexionbd.matricula m on a.id = m.ID LEFT JOIN pruebaconexionbd.modulo_profesional modulo on modulo.CODIGO=m.codigo ORDER BY a.id"; //TODO

            Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery(sql);

            boolean hayFila = false;
            String id = "";

            while (rs.next()) {

                // Si Alumno tiene modulos
                if (rs.getString(2) != null) {
                    if (id.equals(rs.getString(3))){ // Si es el mismo alumno no se muestra el nombre
                        // Espacios equivalentes a "Alumno: " y nombre
                        System.out.println("        " + " ".repeat(rs.getString(1).length()) + " | Modulo: " + rs.getString(2) + " | Curso: " + rs.getString(4) + " | Nota: " + rs.getString(5));
                    } else {
                        System.out.println("Alumno: " + rs.getString(1) + " | Modulo: " + rs.getString(2) + " | Curso: " + rs.getString(4) + " | Nota: " + rs.getString(5));
                    }
                }

                // Si Alumno NO tiene modulos
                else if (rs.getString(1) != null) {
                    System.out.println("Alumno: " + rs.getString(1) + " | Ningun modulo matriculado");
                }

                id = rs.getString(3); // Se guarda el id para compararlo con el siguiente
                hayFila=true;
            }
            if (!hayFila) System.out.println("No se han encontrado resultados.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Añade un registro en la tabla matricula.
     * Se tienen en cuenta si existe el NUMESC y el codigo del modulo.
     * @param con (Conexion)
     */
    private static void matricularAlumnoEnModulo(Connection con) {

        // Datos
        System.out.println("Numero escolar: ");
        int id = Integer.parseInt(sc.nextLine()) / 2;

        System.out.println("Codigo modulo: ");
        String codigo = sc.nextLine();

        System.out.println("Curso: ");
        int curso = Integer.parseInt(sc.nextLine());

        // He supuesto que la matriculación es al principio del curso, por ese motivo no se pide la nota
        try {
            if (comprobarCodigoModulo(codigo, con) && comprobarIdAlumno(id, con)) {

                // Comprobacion si el alumno ya esta matriculado en el modulo
                String sql = "SELECT id FROM pruebaconexionbd.matricula WHERE id=? AND codigo=? AND curso =?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ps.setString(2, codigo);
                ps.setInt(3, curso);
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    sql = "INSERT INTO pruebaconexionbd.matricula(id, codigo, curso) VALUES(?, ?, ?)";
                    PreparedStatement ps2 = con.prepareStatement(sql);
                    ps.setInt(1, id);
                    ps.setString(2, codigo);
                    ps2.close();
                } else {
                    System.out.println("El alumno ya esta matriculado en el modulo seleccionado.");
                }
                ps.close();
            } else {
                System.out.println("Error. No existe un codigo o un numero escolar compatible con los datos introducidos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lista de los modulos de un alumno
     * @param con (Conexion)
     */
    private static void listaModulosDeAlumno(Connection con) {

        // Datos
        System.out.println("Numero escolar: ");
        int id = Integer.parseInt(sc.nextLine()) / 2; // Al dividir entre dos el numero escolar, se obtiene el ID del alumno

        try {
            if (comprobarIdAlumno(id, con)) {
                String sql = "SELECT descripcion FROM pruebaconexionbd.modulo_profesional JOIN pruebaconexionbd.matricula m on modulo_profesional.CODIGO = m.CODIGO JOIN pruebaconexionbd.alumnos a on a.id = m.ID WHERE a.id = ?";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                System.out.println("Modulos:");
                while (rs.next()) {
                    System.out.println("- " + rs.getString(1));
                }
                ps.close();
            } else {
                System.out.println("Error. No existe el numero escolar introducido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Borra la matricula de un alumno en un modulo.
     * @param con (Conexion)
     */
    private static void borrarMatricula(Connection con) {

        // Datos
        System.out.println("Numero escolar: ");
        int id = Integer.parseInt(sc.nextLine()) / 2; // Al dividir entre dos el numero escolar, se obtiene el ID del alumno

        System.out.println("Codigo modulo: ");
        String codigo = sc.nextLine();

        System.out.println("Curso: ");
        int curso = Integer.parseInt(sc.nextLine());

        try {
            if (comprobarIdAlumno(id, con) && comprobarCodigoModulo(codigo, con)) {
                String sql = "DELETE FROM pruebaconexionbd.matricula WHERE CODIGO = ? AND id = ? AND curso=?;";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ps.setString(2, codigo);
                ps.setInt(3, curso);

                ps.executeUpdate();
                System.out.println("Eliminado con éxito.");
                ps.close();
            } else {
                System.out.println("Error. No existe un codigo o un numero escolar compatible con los datos introducidos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Añade un modulo
     * @param con (Conexion)
     */
    private static void insertarModulo(Connection con) {

        // Datos
        System.out.println("Codigo: ");
        String codigo = sc.nextLine();

        System.out.println("Descripcion: ");
        String descripcion = sc.nextLine();

        try {
            if (comprobarCodigoModulo(codigo, con)) {
                System.out.println("Error. Un modulo con ese codigo ya existe.");
            } else {
                String sql = "INSERT INTO  pruebaconexionbd.modulo_profesional(CODIGO, DESCRIPCION) VALUES(?,?);";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, codigo);
                ps.setString(2, descripcion);

                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Añade un alumno, con sus datos correspondientes
     * @param con (Conexion)
     */
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

            String sql = "INSERT INTO  pruebaconexionbd.alumnos(nombre, apellido1, apellido2, email, edad) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,nombre);
            ps.setString(2, apellido1);
            ps.setString(3, apellido2);
            ps.setString(4, email);
            ps.setInt(5, edad);
            ps.executeUpdate();

            // Obtener ID
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            String id = rs.getString(1);

            // Update NUMESC
            sql = "UPDATE pruebaconexionbd.alumnos SET NUMESC=id*2 WHERE id=" + id + ";";
            Statement st = con.createStatement();

            int affectedRows = st.executeUpdate(sql);
            if (affectedRows == 0) System.out.println("Creating user failed, no rows affected.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Comprueba si el id del alumno corresponde con uno existente en la base de datos
     * @param id
     * @param con
     * @return boolean comprobacion
     * @throws SQLException
     */
    public static boolean comprobarIdAlumno(int id, Connection con) throws SQLException {
        String sql = "SELECT id FROM pruebaconexionbd.alumnos WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    /**
     * Comprueba si el codigo del modulo corresponde con uno existente en la base de datos
     * @param codigo
     * @param con
     * @return boolean comprobacion
     * @throws SQLException
     */
    public static boolean comprobarCodigoModulo(String codigo, Connection con) throws SQLException {
        String sql = "SELECT CODIGO FROM pruebaconexionbd.modulo_profesional WHERE CODIGO=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, codigo);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}