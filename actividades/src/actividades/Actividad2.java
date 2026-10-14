package actividades;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Actividad2 {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        String archivo = "datos.txt";
        if(args.length > 0)
        {
            archivo = args[0];
        }
        try(FileWriter fw = new FileWriter(archivo, true);
                PrintWriter salida = new PrintWriter(fw))
        {
            System.out.println("Escribe líneas de texto. Escribe FIN para salir.");
            String linea = scanner.nextLine();
            while(!linea.equalsIgnoreCase("FIN"))
            {
                salida.println(linea);
                if(salida.checkError())
                {
                    throw new IOException("No fue posible escribir los datos.");
                }
                linea = scanner.nextLine();
            }
            System.out.println("Datos agregados a " + archivo);
        }
        catch(IOException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
