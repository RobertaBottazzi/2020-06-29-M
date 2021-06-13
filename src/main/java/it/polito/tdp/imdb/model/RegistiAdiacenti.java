package it.polito.tdp.imdb.model;

public class RegistiAdiacenti {
	private Director director;
	private int peso;
	
	public RegistiAdiacenti(Director director, int peso) {
		this.director = director;
		this.peso = peso;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return director.toString() +"# attori condivisi " + peso;
	}
	
	
	

}
