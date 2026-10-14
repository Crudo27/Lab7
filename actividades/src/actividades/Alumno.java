package actividades;

public class Alumno extends PersonaSerializable {
    private static final long serialVersionUID = 1L;
    private Fecha fechaMatricula;

    public Alumno(String nombre, int edad, Fecha fechaMatricula)
    {
        super(nombre, edad);
        this.fechaMatricula = fechaMatricula;
    }

    @Override
    public String toString()
    {
        return super.toString() + " | Fecha de matrícula: " + fechaMatricula;
    }
}
