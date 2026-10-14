package principal;

import java.util.Scanner;
import controlador.VideojuegoControlador;
import modelo.VideojuegoModelo;
import vista.VideojuegoVista;

public class Main {
    public static void main(String[] args)
    {
        String archivo = "videojuegos.txt";
        if(args.length > 0)
        {
            archivo = args[0];
        }
        Scanner scanner = new Scanner(System.in);
        VideojuegoModelo modelo = new VideojuegoModelo(archivo);
        VideojuegoVista vista = new VideojuegoVista(scanner);
        VideojuegoControlador controlador = new VideojuegoControlador(modelo, vista);
        controlador.iniciar();
        scanner.close();
    }
}
