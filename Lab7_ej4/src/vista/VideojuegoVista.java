package vista;

import java.util.ArrayList;
import java.util.Scanner;
import modelo.Videojuego;

public class VideojuegoVista {
    private Scanner scanner;

    public VideojuegoVista(Scanner scanner)
    {
        this.scanner = scanner;
    }

    public void mostrarMenu()
    {
        System.out.println("\nGESTIÓN DE VIDEOJUEGOS");
        System.out.println("1. Listar registros");
        System.out.println("2. Agregar registro");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Eliminar por ID");
        System.out.println("0. Salir");
    }

    public String solicitarTexto(String mensaje)
    {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public int solicitarEntero(String mensaje)
    {
        try
        {
            return Integer.parseInt(solicitarTexto(mensaje).trim());
        }
        catch(NumberFormatException e)
        {
            throw new IllegalArgumentException("Ingresa un número entero.");
        }
    }

    public double solicitarPrecio(String mensaje)
    {
        try
        {
            return Double.parseDouble(solicitarTexto(mensaje).trim().replace(',', '.'));
        }
        catch(NumberFormatException e)
        {
            throw new IllegalArgumentException("Ingresa un precio válido.");
        }
    }

    public void mostrarRegistros(ArrayList<Videojuego> registros)
    {
        if(registros.isEmpty())
        {
            System.out.println("No hay registros.");
        }
        for(Videojuego videojuego: registros)
        {
            System.out.println(videojuego);
        }
    }

    public void mostrarMensaje(String mensaje)
    {
        System.out.println(mensaje);
    }
}
