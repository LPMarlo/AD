package Tema3.ExamenBBDDOO;

import java.util.ArrayList;
import java.util.List;


/**
 * @author JESUS
 *
 */
public class Contacto {
	String apellido1, apellido2, nombre;
	List<DatoContacto> datos = new ArrayList<>();
	int edad;

	public Contacto(String apellido1, String apellido2, String nombre, List<DatoContacto> datos, int edad) {
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nombre = nombre;
		this.datos = datos;
		this.edad = edad;
	}

	public Contacto(String apellido1, String apellido2, String nombre) {
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nombre = nombre;

	}

	public Contacto(String apellido1, String apellido2, String nombre, int edad) {
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nombre = nombre;
		this.edad = edad;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void addDato(DatoContacto dc) {
		datos.add(dc);
	}

	public void removeDato(DatoContacto dc) {
		datos.remove(dc);
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public List<DatoContacto> getDatos() {
		return datos;
	}

	@Override
	public String toString() {
		return "Contacto{" +
				"apellido1='" + apellido1 + '\'' +
				", apellido2='" + apellido2 + '\'' +
				", nombre='" + nombre + '\'' +
				", datos=" + datos +
				", edad=" + edad +
				'}';
	}
}
