package modelo;

// Generated 17/07/2015 11:41:36 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Destino generated by hbm2java
 */
public class Destino implements java.io.Serializable {

	private int idDestino;
	private String nombre;
	private String siglas;
	private Boolean estatus;
	private Set usuarioses = new HashSet(0);

	public Destino() {
	}

	public Destino(int idDestino) {
		this.idDestino = idDestino;
	}

	public Destino(int idDestino, String nombre, String siglas,
			Boolean estatus, Set usuarioses) {
		this.idDestino = idDestino;
		this.nombre = nombre;
		this.siglas = siglas;
		this.estatus = estatus;
		this.usuarioses = usuarioses;
	}

	public int getIdDestino() {
		return this.idDestino;
	}

	public void setIdDestino(int idDestino) {
		this.idDestino = idDestino;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSiglas() {
		return this.siglas;
	}

	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}

	public Boolean getEstatus() {
		return this.estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}

	public Set getUsuarioses() {
		return this.usuarioses;
	}

	public void setUsuarioses(Set usuarioses) {
		this.usuarioses = usuarioses;
	}

}
