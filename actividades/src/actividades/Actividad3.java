package actividades;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Actividad3 {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        String archivo = "matriz.dat";
        if(args.length > 0)
        {
            archivo = args[0];
        }
        try
        {
            System.out.print("Número de filas: ");
            int filas = Integer.parseInt(scanner.nextLine());
            System.out.print("Número de columnas: ");
            int columnas = Integer.parseInt(scanner.nextLine());
            if(filas <= 0 || columnas <= 0 || filas > 100 || columnas > 100)
            {
                throw new IllegalArgumentException("Las dimensiones deben estar entre 1 y 100.");
            }
            double[][] matriz = new double[filas][columnas];
            for(int i = 0; i < filas; i++)
            {
                for(int j = 0; j < columnas; j++)
                {
                    System.out.print("matriz[" + i + "][" + j + "]: ");
                    matriz[i][j] = Double.parseDouble(scanner.nextLine());
                }
            }
            try(DataOutputStream salida = new DataOutputStream(new FileOutputStream(archivo)))
            {
                salida.writeInt(filas);
                salida.writeInt(columnas);
                for(int i = 0; i < filas; i++)
                {
                    for(int j = 0; j < columnas; j++)
                    {
                        salida.writeDouble(matriz[i][j]);
                    }
                }
            }
            System.out.println("Matriz guardada.");
            try(DataInputStream entrada = new DataInputStream(new FileInputStream(archivo)))
            {
                int f = entrada.readInt();
                int c = entrada.readInt();
                System.out.println("Matriz recuperada (" + f + " x " + c + "):");
                for(int i = 0; i < f; i++)
                {
                    for(int j = 0; j < c; j++)
                    {
                        System.out.print(entrada.readDouble() + " ");
                    }
                    System.out.println();
                }
            }
        }
        catch(IOException | IllegalArgumentException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
