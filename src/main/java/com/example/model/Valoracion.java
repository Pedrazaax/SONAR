package com.example.model;

import org.springframework.data.annotation.Id;

/*Clase que representa las valoraciones tanto de rider como de restaurantes*/
public class Valoracion {
	/* ============================ */
	// VARIABLES

	// idValoracion : id de la valoracion para distinguir en los repositorios
	@Id
	private String idValoracion;
	// nota : nota que un usuario le da en la valoración
	private double nota;
	// comentario : comentario que deja un usuario
	private String comentario;
	// idValorado : id que funciona como clave ajena para buscar en cada repositorio
	// dependiendo de quien se trate
	private String idValorado;
	/* ==================================== */
	// MÉTODOS

	/* Constructor */
	public Valoracion(double nota, String comentario, String idValorado) {
		super();
		this.nota = nota;
		this.comentario = comentario;
		this.idValorado = idValorado;

	}

	/* Métodos getter y setter */
	public String getIdValoracion() {
		return idValoracion;
	}

	public void setIdValoracion(String idValoracion) {
		this.idValoracion = idValoracion;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getIdValorado() {
		return idValorado;
	}

	public void setIdValorado(String idValorado) {
		this.idValorado = idValorado;
	}

	@Override
	public String toString() {
		return "Valoracion [idValoracion=" + idValoracion + ", nota=" + nota + ", comentario=" + comentario
				+ ", idValorado=" + idValorado + "]";
	}

	/* Método que calcula la media para ambas clases */
	public double calcularMedia(double valoracionActual, double nuevoValor) {
		return (valoracionActual + nuevoValor) / 2;
	}

}
