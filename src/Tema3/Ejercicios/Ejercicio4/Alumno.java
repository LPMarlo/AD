package Tema3.Ejercicios.Ejercicio4;

import java.time.LocalDate;
import java.util.Map;

public class Alumno {

    private String dni;
    private String nombre;
    private String direccion;
    private String poblacion;
    private int codPostal;
    private String provincia;
    private String telefono1;
    private String telefono2;
    private String fechaNac;
    private Map<Asignatura,Nota> notaAsignatura;

    public Alumno(String dni, String nombre, String direccion, String poblacion, int codPostal, String provincia, String telefono1, String telefono2, String fechaNac, Map<Asignatura, Nota> notaAsignatura) {
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.codPostal = codPostal;
        this.provincia = provincia;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.fechaNac = fechaNac;
        this.notaAsignatura = notaAsignatura;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        String stringNA = notaAsignatura.toString().replace("{","").replace("}","").replace("=", "").replace(",", "");
        return  "\n===Info===\nDNI: " + dni +
                "\nNombre: " + nombre +
                "\nDireccion: " + direccion +
                "\nPoblacion: " + poblacion +
                "\nCodigo Postal: " + codPostal +
                "\nProvincia: " + provincia +
                "\nTelefono1: " + telefono1 +
                "\nTelefono2: " + telefono2 +
                "\nFecha Nacimiento: " + fechaNac +
                stringNA;
    }
}
