package actividades;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;
import java.io.IOException;

public class Actividad6 {
    public static void main(String[] args)
    {
        String archivo = "alumnos.dat";
        if(args.length > 0)
        {
            archivo = args[0];
        }
        try(ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo)))
        {
            salida.writeObject(new Alumno("Gian Piero", 20, new Fecha(14, 10, 2026)));
            salida.writeObject(new Alumno("María", 21, new Fecha(12, 8, 2026)));
            salida.writeObject(new Alumno("Luis", 22, new Fecha(11, 8, 2026)));
            System.out.println("Tres alumnos guardados.");
        }
        catch(IOException e)
        {
            System.out.println("Error al guardar: " + e.getMessage());
            return;
        }
        try(ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo)))
        {
            while(true)
            {
                System.out.println(entrada.readObject());
            }
        }
        catch(EOFException e)
        {
            System.out.println("Lectura finalizada.");
        }
        catch(IOException | ClassNotFoundException e)
        {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }
}
