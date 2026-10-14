package actividades;

import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Actividad4 {
    public static String leerArchivo(String ruta) throws IOException
    {
        StringBuilder texto = new StringBuilder();
        try(FileInputStream entrada = new FileInputStream(ruta))
        {
            int dato;
            while((dato = entrada.read()) != -1)
            {
                texto.append((char) dato);
            }
        }
        return texto.toString();
    }

    public static void main(String[] args)
    {
        String ruta = "datos/TestFile.java";
        if(args.length > 0)
        {
            ruta = args[0];
        }
        try
        {
            JTextArea area = new JTextArea(leerArchivo(ruta));
            area.setEditable(false);
            JFrame ventana = new JFrame("Contenido de " + ruta);
            ventana.add(new JScrollPane(area));
            ventana.setSize(650, 400);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        }
        catch(IOException e)
        {
            System.out.println("No fue posible abrir el archivo: " + e.getMessage());
        }
    }
}
