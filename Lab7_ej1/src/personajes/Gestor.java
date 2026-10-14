package personajes;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.EOFException;

public class Gestor {
    private List<Personaje> personajes = new ArrayList<>();
    private String archivo;
    private Scanner scanner;

    public Gestor()
    {
        this("personajes.dat", new Scanner(System.in));
    }

    public Gestor(String archivo, Scanner scanner)
    {
        this.archivo = archivo;
        this.scanner = scanner;
    }

    public void leerPersonajes()
    {
        personajes.clear();
        if(!new File(archivo).exists())
        {
            return;
        }
        try(FileInputStream fis = new FileInputStream(archivo);
                ObjectInputStream reader = new ObjectInputStream(fis))
        {
            while(true)
            {
                personajes.add((Personaje) reader.readObject());
            }
        }
        catch(EOFException e)
        {
            System.out.println("Personajes cargados correctamente.");
        }
        catch(IOException | ClassNotFoundException e)
        {
            personajes.clear();
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public boolean guardarPersonajes()
    {
        try(FileOutputStream fos = new FileOutputStream(archivo);
                ObjectOutputStream writer = new ObjectOutputStream(fos))
        {
            for(Personaje personaje: personajes)
            {
                writer.writeObject(personaje);
            }
            return true;
        }
        catch(IOException e)
        {
            System.out.println("Error al guardar: " + e.getMessage());
            return false;
        }
    }

    public void mostrarPersonajes()
    {
        if(personajes.isEmpty())
        {
            System.out.println("No hay personajes registrados.");
        }
        for(Personaje personaje: personajes)
        {
            System.out.println(personaje);
        }
    }

    public Personaje buscarPersonaje(String nombre)
    {
        for(Personaje personaje: personajes)
        {
            if(personaje.getNombre().equalsIgnoreCase(nombre.trim()))
            {
                return personaje;
            }
        }
        return null;
    }

    public boolean existePersonaje(String nombre)
    {
        return buscarPersonaje(nombre) != null;
    }

    public void agregarPersonaje(String nombre, int vida, int ataque, int defensa, int alcance)
    {
        if(nombre == null || nombre.isBlank())
        {
            throw new IllegalArgumentException("Ingresa un nombre válido.");
        }
        if(existePersonaje(nombre))
        {
            throw new IllegalArgumentException("Ya existe un personaje con ese nombre.");
        }
        personajes.add(new Personaje(nombre, vida, ataque, defensa, alcance));
        guardarPersonajes();
    }

    public void añadirPersonaje()
    {
        System.out.print("Nombre del personaje: ");
        String nombre = scanner.nextLine();
        int vida = leerNumero("Vida: ");
        int ataque = leerNumero("Ataque: ");
        int defensa = leerNumero("Defensa: ");
        int alcance = leerNumero("Alcance: ");
        agregarPersonaje(nombre, vida, ataque, defensa, alcance);
        System.out.println("Personaje agregado.");
    }

    public void modificarPersonaje(String nombre)
    {
        Personaje personaje = buscarPersonaje(nombre);
        if(personaje == null)
        {
            throw new IllegalArgumentException("No existe ese personaje.");
        }
        int vida = leerNumero("Nueva vida: ");
        int ataque = leerNumero("Nuevo ataque: ");
        int defensa = leerNumero("Nueva defensa: ");
        int alcance = leerNumero("Nuevo alcance: ");
        modificarPersonaje(nombre, vida, ataque, defensa, alcance);
        System.out.println("Personaje modificado.");
    }

    public void modificarPersonaje(String nombre, int vida, int ataque, int defensa, int alcance)
    {
        Personaje personaje = buscarPersonaje(nombre);
        if(personaje == null)
        {
            throw new IllegalArgumentException("No existe ese personaje.");
        }
        Personaje nuevosDatos = new Personaje(nombre, vida, ataque, defensa, alcance);
        personaje.setVida(nuevosDatos.getVida());
        personaje.setAtaque(nuevosDatos.getAtaque());
        personaje.setDefensa(nuevosDatos.getDefensa());
        personaje.setAlcance(nuevosDatos.getAlcance());
        guardarPersonajes();
    }

    public void eliminarPersonaje(String nombre)
    {
        Personaje personaje = buscarPersonaje(nombre);
        if(personaje == null)
        {
            throw new IllegalArgumentException("No existe ese personaje.");
        }
        personajes.remove(personaje);
        guardarPersonajes();
    }

    // Ejercicio 2: actualizar un solo atributo.
    public void actualizarAtributo(String nombre, int opcion, int valor)
    {
        Personaje personaje = buscarPersonaje(nombre);
        if(personaje == null)
        {
            throw new IllegalArgumentException("Personaje no encontrado.");
        }
        switch(opcion)
        {
        case 1: personaje.setVida(valor); break;
        case 2: personaje.setAtaque(valor); break;
        case 3: personaje.setDefensa(valor); break;
        case 4: personaje.setAlcance(valor); break;
        default: throw new IllegalArgumentException("Atributo inválido.");
        }
        guardarPersonajes();
    }

    // Ejercicio 2: estadísticas generales de los personajes.
    public void mostrarEstadisticas()
    {
        if(personajes.isEmpty())
        {
            System.out.println("No hay personajes para calcular estadísticas.");
            return;
        }
        int vida = 0, ataque = 0, defensa = 0, alcance = 0;
        for(Personaje personaje: personajes)
        {
            vida += personaje.getVida();
            ataque += personaje.getAtaque();
            defensa += personaje.getDefensa();
            alcance += personaje.getAlcance();
        }
        System.out.println("Total de personajes: " + personajes.size());
        System.out.printf("Promedios - Vida: %.2f | Ataque: %.2f | Defensa: %.2f | Alcance: %.2f%n",
                (double)vida / personajes.size(), (double)ataque / personajes.size(),
                (double)defensa / personajes.size(), (double)alcance / personajes.size());
    }

    // Ejercicio 2: filtrado sencillo sin expresiones lambda.
    public void filtrarPorVida(int minimo)
    {
        boolean encontrado = false;
        for(Personaje personaje: personajes)
        {
            if(personaje.getVida() >= minimo)
            {
                System.out.println(personaje);
                encontrado = true;
            }
        }
        if(!encontrado)
        {
            System.out.println("No se encontraron personajes.");
        }
    }

    public List<Personaje> getPersonajes()
    {
        return new ArrayList<>(personajes);
    }

    private int leerNumero(String mensaje)
    {
        System.out.print(mensaje);
        try
        {
            return Integer.parseInt(scanner.nextLine().trim());
        }
        catch(NumberFormatException e)
        {
            throw new IllegalArgumentException("Debes ingresar un número entero.");
        }
    }

    public void iniciar()
    {
        leerPersonajes();
        String opcion;
        do
        {
            System.out.println("\nGESTOR DE PERSONAJES");
            System.out.println("1. Mostrar personajes");
            System.out.println("2. Agregar personaje");
            System.out.println("3. Modificar personaje");
            System.out.println("4. Eliminar personaje");
            System.out.println("5. Actualizar un atributo");
            System.out.println("6. Mostrar estadísticas");
            System.out.println("7. Filtrar por vida mínima");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            if(!scanner.hasNextLine())
            {
                break;
            }
            opcion = scanner.nextLine();
            try
            {
                switch(opcion)
                {
                case "1": mostrarPersonajes(); break;
                case "2": añadirPersonaje(); break;
                case "3":
                    System.out.print("Nombre a modificar: ");
                    modificarPersonaje(scanner.nextLine());
                    break;
                case "4":
                    System.out.print("Nombre a eliminar: ");
                    eliminarPersonaje(scanner.nextLine());
                    System.out.println("Personaje eliminado.");
                    break;
                case "5":
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.println("1. Vida  2. Ataque  3. Defensa  4. Alcance");
                    int atributo = leerNumero("Atributo: ");
                    int valor = leerNumero("Nuevo valor: ");
                    actualizarAtributo(nombre, atributo, valor);
                    System.out.println("Atributo actualizado.");
                    break;
                case "6": mostrarEstadisticas(); break;
                case "7": filtrarPorVida(leerNumero("Vida mínima: ")); break;
                case "0": System.out.println("Hasta luego."); break;
                default: System.out.println("Opción inválida.");
                }
            }
            catch(IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
        } while(!opcion.equals("0"));
    }

    public static void main(String[] args)
    {
        Gestor gestor = new Gestor();
        gestor.iniciar();
    }
}
