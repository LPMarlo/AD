package Tema1.Ejercicio11;

import java.io.Serializable;

public class Libro implements Serializable {

    private String ISBN;
    private String nombre;
    private Autor autor;
    private int numEjemplares;

    public Libro(String ISBN, String nombre, Autor autor, int numEjemplares) throws LibroException {
        setISBN(ISBN);
        setNombre(nombre);
        this.autor = autor;
        this.numEjemplares = numEjemplares;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) throws LibroException {
        this.ISBN = ISBN;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws LibroException {
        this.nombre = nombre;
    }

    public int getNumEjemplares() {
        return numEjemplares;
    }

    public void setNumEjemplares(int numEjemplares) {
        this.numEjemplares = numEjemplares;
    }

    @Override
    public String toString() {
        return ISBN + ";" + nombre + ";" +
                autor.toString() + ";" +
                numEjemplares + "\n";
    }
}
