package actividades;

public class ArregloPersonas {
    private Persona[] personas = new Persona[100];
    private int cantidad = 0;

    public void agregar(Persona persona)
    {
        if(cantidad >= personas.length)
        {
            throw new IllegalArgumentException("Agenda llena.");
        }
        personas[cantidad] = persona;
        cantidad++;
    }

    public Persona buscar(String nombre)
    {
        for(int i = 0; i < cantidad; i++)
        {
            if(personas[i].getNombre().equalsIgnoreCase(nombre))
            {
                return personas[i];
            }
        }
        return null;
    }

    public int getCantidad() { return cantidad; }
}
