package contador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class ContadorPalabras {
    private int lineas;
    private int palabras;
    private int caracteres;
    private HashMap<String, Integer> frecuencias = new HashMap<>();

    public void contarArchivo(File archivo) throws IOException
    {
        lineas = 0;
        palabras = 0;
        caracteres = 0;
        frecuencias.clear();

        if(archivo == null || !archivo.isFile() || !archivo.canRead())
        {
            throw new IOException("El archivo no existe o no se puede leer.");
        }
        try(BufferedReader lector = new BufferedReader(new FileReader(archivo)))
        {
            String linea;
            while((linea = lector.readLine()) != null)
            {
                lineas++;
                caracteres += linea.length();
                String palabra = "";
                for(int i = 0; i < linea.length(); i++)
                {
                    char letra = linea.charAt(i);
                    if(Character.isLetterOrDigit(letra))
                    {
                        palabra += Character.toLowerCase(letra);
                    }
                    else if(!palabra.isEmpty())
                    {
                        agregarPalabra(palabra);
                        palabra = "";
                    }
                }
                if(!palabra.isEmpty())
                {
                    agregarPalabra(palabra);
                }
            }
        }
    }

    private void agregarPalabra(String palabra)
    {
        palabras++;
        if(frecuencias.containsKey(palabra))
        {
            frecuencias.put(palabra, frecuencias.get(palabra) + 1);
        }
        else
        {
            frecuencias.put(palabra, 1);
        }
    }

    public void mostrarResultados()
    {
        System.out.println("Total de líneas: " + lineas);
        System.out.println("Total de palabras: " + palabras);
        System.out.println("Total de caracteres: " + caracteres);
        double promedio = 0;
        if(lineas > 0)
        {
            promedio = (double) palabras / lineas;
        }
        System.out.printf("Promedio de palabras por línea: %.2f%n", promedio);
        System.out.println("Palabras más frecuentes:");

        ArrayList<String> pendientes = new ArrayList<>(frecuencias.keySet());
        int limite = Math.min(10, pendientes.size());
        for(int i = 0; i < limite; i++)
        {
            String frecuente = pendientes.get(0);
            for(String palabra: pendientes)
            {
                if(frecuencias.get(palabra) > frecuencias.get(frecuente)
                        || (frecuencias.get(palabra).equals(frecuencias.get(frecuente))
                        && palabra.compareTo(frecuente) < 0))
                {
                    frecuente = palabra;
                }
            }
            System.out.println(frecuente + ": " + frecuencias.get(frecuente));
            pendientes.remove(frecuente);
        }
    }

    public int getLineas() { return lineas; }
    public int getPalabras() { return palabras; }
    public int getCaracteres() { return caracteres; }
    public int getFrecuencia(String palabra) { return frecuencias.getOrDefault(palabra, 0); }

    public static void main(String[] args)
    {
        ContadorPalabras contador = new ContadorPalabras();
        Scanner scanner = new Scanner(System.in);
        boolean terminado = false;
        while(!terminado)
        {
            File archivo;
            if(args.length > 0)
            {
                archivo = new File(args[0]);
            }
            else
            {
                JFileChooser selector = new JFileChooser();
                selector.setDialogTitle("Seleccionar un archivo de texto");
                int opcion = selector.showOpenDialog(null);
                if(opcion != JFileChooser.APPROVE_OPTION)
                {
                    System.out.println("Selección cancelada.");
                    break;
                }
                archivo = selector.getSelectedFile();
            }
            try
            {
                contador.contarArchivo(archivo);
                System.out.println("Archivo: " + archivo.getName());
                contador.mostrarResultados();
                terminado = true;
            }
            catch(IOException e)
            {
                System.out.println("Error: " + e.getMessage());
                if(args.length > 0)
                {
                    System.out.println("Vuelve a ejecutar indicando otra ruta válida.");
                    break;
                }
                System.out.println("Selecciona otro archivo.");
            }
        }
        scanner.close();
    }
}
