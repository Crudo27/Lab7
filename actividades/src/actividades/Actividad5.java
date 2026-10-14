package actividades;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Actividad5 {
    public static ArregloPersonas cargarAgenda(String archivo) throws IOException
    {
        ArregloPersonas agenda = new ArregloPersonas();
        try(BufferedReader lector = new BufferedReader(new FileReader(archivo)))
        {
            String nombre;
            while((nombre = lector.readLine()) != null)
            {
                String telefono = lector.readLine();
                String direccion = lector.readLine();
                if(telefono == null || direccion == null)
                {
                    throw new IOException("Datos incompletos en la agenda.");
                }
                agenda.agregar(new Persona(nombre, telefono, direccion));
            }
        }
        return agenda;
    }

    public static void main(String[] args)
    {
        String archivo = "datos/agenda.txt";
        if(args.length > 0)
        {
            archivo = args[0];
        }
        try
        {
            ArregloPersonas agenda = cargarAgenda(archivo);
            System.out.println("Contactos cargados: " + agenda.getCantidad());
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nombre que deseas buscar: ");
            Persona persona = agenda.buscar(scanner.nextLine().trim());
            System.out.println(persona == null ? "Contacto no encontrado." : persona);
        }
        catch(IOException | IllegalArgumentException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
