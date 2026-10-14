package actividades;

import java.io.Serializable;

public class PersonaSerializable implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private int edad;

    public PersonaSerializable(String nombre, int edad)
    {
        this.nombre = nombre;
        this.edad = edad;
    }

    @Override
    public String toString()
    {
        return nombre + " (" + edad + " años)";
    }
}
