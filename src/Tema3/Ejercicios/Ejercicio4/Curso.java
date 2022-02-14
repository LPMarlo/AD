package Tema3.Ejercicios.Ejercicio4;

import java.util.List;

public class Curso {

    private int idCurso;
    private String descripcion;
    private String nivel;
    private String turno;
    private List<Alumno> alumnos;

    public Curso(int idCurso, String descripcion, String nivel, String turno, List<Alumno> alumnos) {
        this.idCurso = idCurso;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.turno = turno;
        this.alumnos = alumnos;
    }

    @Override
    public String toString() {
        return "idCurso: " + idCurso +
                "\n\tDescripcion: " + descripcion +
                "\n\tNivel: " + nivel +
                "\n\tTurno:" + turno +
                "\n\tAlumnos:\n" + alumnos;
    }

    public String toStringAlumno(String dni) {
        Alumno alumno = null;
        for (Alumno value : alumnos) {
            if (value.getDni().equals(dni)) {
                alumno = value;
            }
        }
        return alumno.toString();
    }
}
