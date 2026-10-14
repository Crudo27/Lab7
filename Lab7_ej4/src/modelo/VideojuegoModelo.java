package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class VideojuegoModelo {
    private ArrayList<Videojuego> videojuegos = new ArrayList<>();
    private String archivo;

    public VideojuegoModelo(String archivo)
    {
        this.archivo = archivo;
    }

    public void leerRegistros() throws IOException
    {
        videojuegos.clear();
        File ruta = new File(archivo);
        if(!ruta.exists())
        {
            return;
        }
        try(BufferedReader lector = new BufferedReader(new FileReader(ruta)))
        {
            String linea;
            int numero = 0;
            while((linea = lector.readLine()) != null)
            {
                numero++;
                if(linea.isBlank())
                {
                    continue;
                }
                String[] partes = linea.split(";", -1);
                if(partes.length != 3)
                {
                    throw new IOException("Formato incorrecto en la línea " + numero);
                }
                try
                {
                    int id = Integer.parseInt(partes[0]);
                    double precio = Double.parseDouble(partes[2]);
                    Videojuego registro = new Videojuego(id, partes[1], precio);
                    if(buscarRegistro(id) != null)
                    {
                        throw new IOException("ID repetido en el archivo: " + id);
                    }
                    videojuegos.add(registro);
                }
                catch(IllegalArgumentException e)
                {
                    throw new IOException("Registro inválido en la línea " + numero, e);
                }
            }
        }
    }

    public void agregarRegistro(int id, String nombre, double precio) throws IOException
    {
        if(buscarRegistro(id) != null)
        {
            throw new IllegalArgumentException("Ya existe un registro con ese ID.");
        }
        Videojuego videojuego = new Videojuego(id, nombre, precio);
        try(PrintWriter escritor = new PrintWriter(new FileWriter(archivo, true)))
        {
            escritor.println(videojuego.getId() + ";" + videojuego.getNombre() + ";" + videojuego.getPrecio());
            if(escritor.checkError())
            {
                throw new IOException("No se pudieron guardar los datos.");
            }
        }
        videojuegos.add(videojuego);
    }

    public Videojuego buscarRegistro(int id)
    {
        for(Videojuego juego: videojuegos)
        {
            if(juego.getId() == id)
            {
                return juego;
            }
        }
        return null;
    }

    public void eliminarRegistro(int id) throws IOException
    {
        Videojuego videojuego = buscarRegistro(id);
        if(videojuego == null)
        {
            throw new IllegalArgumentException("No se encontró el videojuego.");
        }
        videojuegos.remove(videojuego);
        try
        {
            guardarRegistros();
        }
        catch(IOException e)
        {
            videojuegos.add(videojuego);
            throw e;
        }
    }

    private void guardarRegistros() throws IOException
    {
        try(PrintWriter escritor = new PrintWriter(new FileWriter(archivo)))
        {
            for(Videojuego videojuego: videojuegos)
            {
                escritor.println(videojuego.getId() + ";" + videojuego.getNombre()
                        + ";" + videojuego.getPrecio());
            }
            if(escritor.checkError())
            {
                throw new IOException("Error al actualizar el archivo.");
            }
        }
    }

    public ArrayList<Videojuego> getVideojuegos()
    {
        return new ArrayList<>(videojuegos);
    }
}
