package Tema1.Ejemplos.DOM_SAX;

import java.io.Serializable;

public class Domicilio implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String calle;
	private int numero;
	public Domicilio(String calle, int numero) {
		super();
		this.calle = calle;
		this.numero = numero;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	
}
