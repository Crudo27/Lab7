package actividades;

public class Persona {
    private String nombre;
    private String telefono;
    private String direccion;

    public Persona(String nombre, String telefono, String direccion)
    {
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getNombre() { return nombre; }

    @Override
    public String toString()
    {
        return "Nombre: " + nombre + " | Teléfono: " + telefono
                + " | Dirección: " + direccion;
    }
}
