package Tema3.Ejercicios.Ejercicio4;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.time.LocalDate;
import java.util.*;

public class Principal {

    private static final String BD_EJERCICIO4 = "ficheros/ejercicio4.oo";
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD_EJERCICIO4);
        cargarDatos(db);
        informacionAlumno(db);
        db.close();
    }

    private static void informacionAlumno(ObjectContainer db) {
        System.out.println("DNI: ");
        String dni = sc.nextLine();

        List<Alumno> alumnos = new ArrayList<>();
        alumnos.add(new Alumno(dni, null, null, null, 0, null, null, null, null, null));
        ObjectSet<Curso> r = db.queryByExample(new Curso(0, null, null, null,  alumnos));
        if (r.hasNext()) {
            System.out.println(r.next().toStringAlumno(dni));
        }
    }

    private static void cargarDatos(ObjectContainer db) {
        Asignatura asig1 = new Asignatura("ACCDAT", "Acceso a Datos", "Informática");
        Asignatura asig2 = new Asignatura("DESINT", "Desarrollo de Interfaces", "Informática");
        Asignatura asig3 = new Asignatura("SGEMP", "Sistenas de Gestión Empresarial", "Informática");

        Nota nota1 = new Nota();
        nota1.setNota1ev(1);
        nota1.setNota2ev(2);
        nota1.setNota3ev(3);
        nota1.setNotaJun(2);
        Nota nota2 = new Nota();
        nota2.setNota1ev(5);
        nota2.setNota2ev(6);
        nota2.setNota3ev(5);
        nota2.setNotaJun(5);
        nota2.setNotaSep(7);
        Nota nota3 = new Nota();
        nota3.setNota1ev(4);
        nota3.setNota2ev(3);
        nota3.setNota3ev(8);
        nota3.setNotaJun(5);
        Nota nota4 = new Nota();
        nota4.setNota1ev(5);
        nota4.setNota2ev(4);
        nota4.setNota3ev(7);
        nota4.setNotaJun(6);

        Nota nota5 = new Nota();
        nota5.setNota1ev(5);
        nota5.setNota2ev(4);
        nota5.setNota3ev(5);
        nota5.setNotaJun(5);

        Map<Asignatura, Nota> notaAsignatura = new HashMap<>();
        notaAsignatura.put(asig1, nota1);
        notaAsignatura.put(asig2, nota2);

        Map<Asignatura, Nota> notaAsignatura2 = new HashMap<>();
        notaAsignatura2.put(asig1, nota3);
        notaAsignatura2.put(asig2, nota4);
        notaAsignatura2.put(asig3, nota5);

        Alumno alumno1 = new Alumno("12345678A", "Pepe", "C/ Constitución, 1", "Alcalá de Guadaira", 41500, "Sevilla", "123456789", "987654321", LocalDate.of(2002, 11, 13).toString(), notaAsignatura);
        Alumno alumno2 = new Alumno("87654321Z", "Pepa", "C/ Constitución, 2", "Alcalá de Guadaira", 41500, "Sevilla", "987654321", "123456789", LocalDate.of(2001, 11, 13).toString(), notaAsignatura2);

        ArrayList<Alumno> alumnos = new ArrayList<>();
        alumnos.add(alumno1);
        alumnos.add(alumno2);

        Curso curso = new Curso(1234, "Segundo Desarrollo de Aplicaciones Multimedia", "Grado Superior", "Mañana", alumnos);
        db.store(curso);
    }
}
