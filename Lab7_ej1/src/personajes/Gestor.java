package personajes;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Optional;
import java.util.InputMismatchException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.EOFException;

public class Gestor {
	private List<Personaje> personajes = new ArrayList<>();
	
	public void leerPersonajes()
	{
		try(FileInputStream fis = new FileInputStream("personajes.dat"); ObjectInputStream reader= new ObjectInputStream(fis))
		{
			while(true)
			{
				personajes.add((Personaje) reader.readObject());
			}
		} catch(ClassNotFoundException e)
		{
			System.out.println(e.getMessage());
		}catch(EOFException e)
		{
			System.out.println("Archivo cargado correctamente.");
		}catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void guardarPersonajes()
	{
		try(FileOutputStream fos = new FileOutputStream("personajes.dat"); ObjectOutputStream writer = new ObjectOutputStream(fos))
		{
			
			for(Personaje personaje: personajes)
			{
				writer.writeObject(personaje);
			}
		
		}catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void mostrarPersonajes()
	{
		for(Personaje personaje: personajes)
		{
			System.out.println(personaje);
		}
	}
	
	public Personaje buscarPersonaje(String nombre)
	{
		Optional<Personaje> resultado = personajes.stream().filter(p -> p.getNombre().equals(nombre)).findFirst();
		
		if(resultado.isPresent())
		{
			return resultado.get();
		}
		return null;
	}
	
	public boolean existePersonaje(String nombre)
	{
		if(buscarPersonaje(nombre) == null)
		{
			return false;
		}
		
		return true;
	}
	
	public void añadirPersonaje()
	{
		try(Scanner scanner = new Scanner(System.in))
		{
		
			System.out.print("Ingrese el nombre del personaje: ");
			String nombre = scanner.nextLine();
			
			if(existePersonaje(nombre))
			{
				throw new IllegalArgumentException("Ya existe un personaje con ese nombre.");
			}
			
			System.out.print("\nIngrese la vida del personaje: ");
			int vida = scanner.nextInt();
			
			System.out.print("\nIngrese el ataque del personaje: ");
			int ataque = scanner.nextInt();
			
			System.out.print("\nIngrese la defensa del personaje: ");
			int defensa = scanner.nextInt();
			
			System.out.print("\nIngrese el alcance del personaje: ");
			int alcance = scanner.nextInt();
			
			personajes.add(new Personaje(nombre, vida, ataque, defensa, alcance));
		}catch(InputMismatchException e)
		{
			System.out.println("Error, dato inválido"+e.getMessage());
		}catch(IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}finally
		{
			guardarPersonajes();
		}
		
		
	}
	
	public void modificarPersonaje(String nombre)
	{
		Personaje personaje = null;
		if(existePersonaje(nombre))
		{
			personaje = buscarPersonaje(nombre);
		}
		
		try(Scanner scanner = new Scanner(System.in))
		{
			System.out.println("Ingrese la vida del personaje");
			personaje.setVida(scanner.nextInt());
			
			System.out.println("Ingrese el ataque del personaje");
			personaje.setAtaque(scanner.nextInt());
			
			System.out.println("Ingrese la defensa del personaje");
			personaje.setDefensa(scanner.nextInt());
			
			System.out.println("Ingrese el alcance del personaje");
			personaje.setAlcance(scanner.nextInt());
		} catch(InputMismatchException e)
		{
			System.out.println(e.getMessage());
		}finally
		{
			guardarPersonajes();
		}
	}
	
	public void eliminarPersonaje(String nombre)
	{
		if(!existePersonaje(nombre))
		{
			System.out.println("No existe ese personaje");
			return;
		}
		Personaje personaje = buscarPersonaje(nombre);
		
		int index = personajes.indexOf(personaje);
		
		personajes.remove(index);
		
		guardarPersonajes();
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gestor gestor = new Gestor();
		
		gestor.leerPersonajes();
		
		for(Personaje personaje: gestor.personajes)
		{
			System.out.println(personaje);
		}
	}
}
