package tema1.Ejercicio11;

import java.io.Serializable;

public class Libro implements Serializable {

    private String id;
    private String nombre;
    private String autor;
    private int cantidad;

    public Libro(String id, String nombre, String autor, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String toStringXML() {
        return "<libro>" +
                "<nombre id=\""+ id + "\">" + nombre + "</nombre>" +
                "<autor>" + autor + "</autor>" +
                "<cantidad>" + cantidad + "</cantidad>" +
                "</libro>";
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", autor='" + autor + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
