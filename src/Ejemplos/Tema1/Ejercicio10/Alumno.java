package Ejemplos.Tema1.Ejercicio10;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Alumno implements Serializable {

    private String nombre;
    private String apellido1;
    private String apellido2;
    private int edad;
    private Date fMatricula;
    List<ModuloProfesional> matricula;

    public Alumno(String nombre, String apellido1, String apellido2, int edad, Date fMatricula, List<ModuloProfesional> matricula) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.edad = edad;
        this.fMatricula = fMatricula;
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Date getfMatricula() {
        return fMatricula;
    }

    public void setfMatricula(Date fMatricula) {
        this.fMatricula = fMatricula;
    }

    public List<ModuloProfesional> getMatricula() {
        return matricula;
    }

    public void setMatricula(List<ModuloProfesional> matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                ", edad=" + edad +
                ", fMatricula=" + fMatricula +
                ", matricula=" + matricula.toString() +
                '}';
    }

    public String toStringXML() {
        StringBuilder sb = new StringBuilder("<alumno>\n" +
                "   <nombre>" + nombre + "</nombre>\n" +
                "   <apellido1>" + apellido1 + "</apellido1>\n" +
                "   <apellido2>" + apellido2 + "</apellido2>\n" +
                "   <edad>" + edad + "</edad>\n" +
                "   <fMatricula>" + fMatricula + "</fMatricula>\n");
        if (matricula.size() != 0) {
            sb.append("   <matricula>\n");
            for (ModuloProfesional moduloProfesional : this.matricula) {
                sb.append("        <modulo numHoras=\"");
                sb.append(moduloProfesional.getNumHoras());
                sb.append("\">");
                sb.append(moduloProfesional.getNombreModulo());
                sb.append("</modulo>\n");

            }
            sb.append("    </matricula>\n");
        } else {
            sb.append("   <matricula/>\n");
        }
        sb.append("</alumno>");
        return sb.toString();
    }
}
