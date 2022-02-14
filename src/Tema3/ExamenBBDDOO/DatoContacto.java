package Tema3.ExamenBBDDOO;

/**
 * @author JESUS
 *
 */
public class DatoContacto {
	String tipoDato;
	Integer valor;

	public DatoContacto(Integer valor) {
		this.valor = valor;
	}

	public DatoContacto(String tipoDato, Integer valor) {
		this.tipoDato = tipoDato;
		this.valor = valor;
	}

	@Override
	public String toString() {
		String s = "";
		if (tipoDato.equalsIgnoreCase("T")) {
			s += "Tel�fono trabajo: ";
		} else if (tipoDato.equalsIgnoreCase("M")) {
			s += "Tel�fono m�vil: ";
		} else if (tipoDato.equalsIgnoreCase("F")) {
			s += "Tel�fono fijo: ";
		}
		s += valor;
		return s;
	}

	public String getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
