package desafio.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Telefone implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Max(value = 99)
	@Min(value = 01)
	private Integer ddd;
	@NotBlank
	@Size(max = 9, min = 8)
	@Column(length = 9)
	private String numero;
	@NotBlank
	private String tipo;

	public Telefone() {
	}

	public Telefone(Integer ddd, String numero, String tipo) {
		this.ddd = ddd;
		this.numero = numero;
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDdd() {
		return ddd;
	}

	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ddd, id, numero, tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telefone other = (Telefone) obj;
		return Objects.equals(ddd, other.ddd) && Objects.equals(id, other.id) && Objects.equals(numero, other.numero)
				&& Objects.equals(tipo, other.tipo);
	}

	@Override
	public String toString() {
		return "Telefone [id=" + id + ", ddd=" + ddd + ", numero=" + numero + ", tipo=" + tipo + "]";
	}

}
