package actividades;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Actividad7 extends JFrame {
    private JTextArea area = new JTextArea();

    public Actividad7()
    {
        super("Información de archivos y directorios");
        add(new JScrollPane(area));
        area.setEditable(false);
        setSize(650, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void seleccionarArchivo()
    {
        JFileChooser selector = new JFileChooser();
        selector.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if(selector.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                Path ruta = selector.getSelectedFile().toPath();
                StringBuilder datos = new StringBuilder();
                datos.append("Nombre: ").append(ruta.getFileName()).append("\n");
                datos.append("Ruta absoluta: ").append(ruta.toAbsolutePath()).append("\n");
                datos.append("Es directorio: ").append(Files.isDirectory(ruta)).append("\n");
                datos.append("Tamaño: ").append(Files.size(ruta)).append(" bytes\n");
                datos.append("Última modificación: ").append(Files.getLastModifiedTime(ruta)).append("\n");
                if(Files.isDirectory(ruta))
                {
                    datos.append("\nContenido:\n");
                    try(DirectoryStream<Path> archivos = Files.newDirectoryStream(ruta))
                    {
                        for(Path archivo: archivos)
                        {
                            datos.append("- ").append(archivo.getFileName()).append("\n");
                        }
                    }
                }
                area.setText(datos.toString());
            }
            catch(IOException e)
            {
                area.setText("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args)
    {
        Actividad7 ventana = new Actividad7();
        ventana.setVisible(true);
        ventana.seleccionarArchivo();
    }
}
