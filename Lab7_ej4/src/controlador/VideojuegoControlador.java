package controlador;

import java.io.IOException;
import modelo.Videojuego;
import modelo.VideojuegoModelo;
import vista.VideojuegoVista;

public class VideojuegoControlador {
    private VideojuegoModelo modelo;
    private VideojuegoVista vista;

    public VideojuegoControlador(VideojuegoModelo modelo, VideojuegoVista vista)
    {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void iniciar()
    {
        try
        {
            modelo.leerRegistros();
            vista.mostrarMensaje("Registros existentes:");
            vista.mostrarRegistros(modelo.getVideojuegos());
        }
        catch(IOException e)
        {
            vista.mostrarMensaje("Error al abrir los datos: " + e.getMessage());
            return;
        }
        String opcion;
        do
        {
            vista.mostrarMenu();
            opcion = vista.solicitarTexto("Opción: ");
            try
            {
                switch(opcion)
                {
                case "1":
                    vista.mostrarRegistros(modelo.getVideojuegos());
                    break;
                case "2":
                    int id = vista.solicitarEntero("ID: ");
                    String nombre = vista.solicitarTexto("Nombre del videojuego: ");
                    double precio = vista.solicitarPrecio("Precio: ");
                    modelo.agregarRegistro(id, nombre, precio);
                    vista.mostrarMensaje("Registro agregado correctamente.");
                    break;
                case "3":
                    int buscado = vista.solicitarEntero("ID a buscar: ");
                    Videojuego registro = modelo.buscarRegistro(buscado);
                    if(registro == null)
                    {
                        vista.mostrarMensaje("Registro no encontrado.");
                    }
                    else
                    {
                        vista.mostrarMensaje(registro.toString());
                    }
                    break;
                case "4":
                    modelo.eliminarRegistro(vista.solicitarEntero("ID a eliminar: "));
                    vista.mostrarMensaje("Registro eliminado correctamente.");
                    break;
                case "0": vista.mostrarMensaje("Saliendo..."); break;
                default: vista.mostrarMensaje("Opción inválida.");
                }
            }
            catch(IOException | IllegalArgumentException e)
            {
                vista.mostrarMensaje("Error: " + e.getMessage());
            }
        } while(!opcion.equals("0"));
    }
}
