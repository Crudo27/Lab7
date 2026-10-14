package actividades;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Actividad1 {
    public static void mostrarInformacion(String nombre) throws IOException
    {
        Path ruta = Paths.get(nombre);
        if(!Files.exists(ruta))
        {
            System.out.println("La ruta no existe.");
            return;
        }
        System.out.println("Nombre: " + ruta.getFileName());
        System.out.println("Es directorio: " + Files.isDirectory(ruta));
        System.out.println("Es ruta absoluta: " + ruta.isAbsolute());
        System.out.println("Última modificación: " + Files.getLastModifiedTime(ruta));
        System.out.println("Tamaño: " + Files.size(ruta) + " bytes");
        System.out.println("Ruta absoluta: " + ruta.toAbsolutePath());
        if(Files.isDirectory(ruta))
        {
            System.out.println("Contenido:");
            try(DirectoryStream<Path> archivos = Files.newDirectoryStream(ruta))
            {
                for(Path archivo: archivos)
                {
                    System.out.println("- " + archivo.getFileName());
                }
            }
        }
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ruta del archivo o directorio: ");
        try
        {
            mostrarInformacion(scanner.nextLine());
        }
        catch(IOException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
