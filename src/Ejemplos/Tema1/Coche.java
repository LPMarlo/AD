package Ejemplos.Tema1;

import java.time.LocalDate;
import java.util.Date;

public class Coche {
    private String marca;
    private String modelo;
    private LocalDate fMatriculacion;
    private int kms;
    private int precio;

    public Coche(String marca, String modelo, LocalDate fMatriculacion, int kms, int precio) {
        this.marca = marca;
        this.modelo = modelo;
        this.fMatriculacion = fMatriculacion;
        this.kms = kms;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Coche{" +
                "marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", fMatriculacion=" + fMatriculacion +
                ", kms=" + kms +
                ", precio=" + precio +
                '}';
    }
}
