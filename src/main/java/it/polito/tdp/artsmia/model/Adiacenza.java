package it.polito.tdp.artsmia.model;

public class Adiacenza {
	
	private ArtObject obj1;
	private ArtObject obj2;
	 private int peso;
	public Adiacenza(ArtObject obj1, ArtObject obj2, int peso) {
		super();
		this.obj1 = obj1;
		this.obj2 = obj2;
		this.peso = peso;
	}
	public ArtObject getObj1() {
		return obj1;
	}
	public void setObj1(ArtObject obj1) {
		this.obj1 = obj1;
	}
	public ArtObject getObj2() {
		return obj2;
	}
	public void setObj2(ArtObject obj2) {
		this.obj2 = obj2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	 



}
