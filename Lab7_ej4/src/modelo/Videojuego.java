package modelo;

public class Videojuego {
    private int id;
    private String nombre;
    private double precio;

    public Videojuego(int id, String nombre, double precio)
    {
        setId(id);
        setNombre(nombre);
        setPrecio(precio);
    }

    public int getId() { return id; }

    public void setId(int id)
    {
        if(id <= 0)
        {
            throw new IllegalArgumentException("El ID debe ser positivo.");
        }
        this.id = id;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre)
    {
        if(nombre == null || nombre.isBlank() || nombre.contains(";"))
        {
            throw new IllegalArgumentException("El nombre no puede estar vacío ni contener ;");
        }
        this.nombre = nombre.trim();
    }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio)
    {
        if(!Double.isFinite(precio) || precio < 0)
        {
            throw new IllegalArgumentException("El precio debe ser un número válido.");
        }
        this.precio = precio;
    }

    @Override
    public String toString()
    {
        return String.format("ID: %d | Juego: %s | Precio: S/ %.2f", id, nombre, precio);
    }
}
