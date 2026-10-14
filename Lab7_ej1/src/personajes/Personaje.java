package personajes;

import java.io.Serializable;

public class Personaje implements Serializable{
	private String nombre;
	private int vida;
	private int ataque;
	private int defensa;
	private int alcance;
	
	public Personaje(String nombre, int vida, int ataque, int defensa, int alcance)
	{
		try
		{
			this.setNombre(nombre);
			this.setVida(vida);
			this.setAtaque(ataque);
			this.setDefensa(defensa);
			this.setAlcance(alcance);
		} catch(IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public int getAlcance() {
		return alcance;
	}

	public void setAlcance(int alcance) {
		
		if(alcance <= 0)
		{
			throw new IllegalArgumentException("El alcance debe ser positivo.");
		}
		this.alcance = alcance;
	}

	public int getDefensa() {
		return defensa;
	}

	public void setDefensa(int defensa) {
		if(defensa <= 0)
		{
			throw new IllegalArgumentException("La defensa debe ser un número positivo.");
		}
		this.defensa = defensa;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		if(ataque <= 0)
		{
			throw new IllegalArgumentException("El ataque debe ser positivo.");
		}
		this.ataque = ataque;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		if(vida <= 0)
		{
			throw new IllegalArgumentException("La vida debe ser un número positivo.");
		}
		this.vida = vida;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if(nombre == null || nombre.isBlank())
		{
			throw new IllegalArgumentException("El nombre no puede estar vacío.");
		}
		this.nombre = nombre;
	}
	
	@Override
	public String toString()
	{
		return String.format("Nombre: "+nombre+" Vida: "+vida+" Ataque: "+ataque+" Defensa "+ defensa+" Alcance "+alcance);
	}
}
