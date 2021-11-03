package Tema1.Ejercicio11;

import java.io.Serializable;

public class Autor implements Serializable {
    private String nombre;
    private String[] apellido;

    public Autor(String nombre, String[] apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Autor(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String[] getApellido() {
        return apellido;
    }

    public void setApellido(String[] apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        String cadena = null;
        if (apellido != null) {
            if (apellido.length == 2) {
                cadena =  nombre + " " + apellido[0] + " " + apellido[1];
            } else {
                cadena = nombre + " " + apellido[0];
            }
        } else {
            cadena = nombre;
        }
        return cadena;
    }
}
