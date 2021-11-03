package Ejemplos.Tema1.Serializacion;

import java.io.*;

public class ejemplo1 {

    public static void main(String[] args) {
        crearFichero();
        leerFichero();
    }

    private static void leerFichero() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("hola.dat"))) {
            Persona persona;
            while (true) {
                persona = (Persona) ois.readObject();
                System.out.println(persona);
            }
        } catch (EOFException e) {
            System.out.println("Fin");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void crearFichero() {
        String[] nombres = {"Pepe", "Pepa", "Luis", "Luisa"};
        int[] edades = {20, 30, 18, 16};
        Persona persona;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("hola.dat"))) {
            for (int i = 0; i < edades.length; i++) {
                persona = new Persona(nombres[i], edades[i]);
                oos.writeObject(persona);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
