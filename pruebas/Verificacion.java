import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import personajes.Gestor;
import personajes.Personaje;
import contador.ContadorPalabras;
import modelo.VideojuegoModelo;
import actividades.Actividad1;
import actividades.Actividad4;
import actividades.Actividad5;
import actividades.ArregloPersonas;

public class Verificacion {
    private static int correctas = 0;

    private static void comprobar(boolean resultado, String mensaje)
    {
        if(!resultado)
        {
            throw new AssertionError(mensaje);
        }
        correctas++;
        System.out.println("OK " + correctas + " - " + mensaje);
    }

    public static void main(String[] args) throws Exception
    {
        Path carpeta = Files.createTempDirectory("laboratorio7_");
        String archivo = carpeta.resolve("personajes.dat").toString();
        Gestor gestor = new Gestor(archivo, new Scanner(""));
        gestor.leerPersonajes();
        comprobar(gestor.getPersonajes().isEmpty(), "Inicia sin archivo");
        gestor.agregarPersonaje("Caballero", 4, 2, 4, 2);
        comprobar(gestor.existePersonaje("caballero"), "Búsqueda por nombre sin distinguir mayúsculas");
        comprobar(Files.exists(Path.of(archivo)), "Guardado automático");
        gestor.agregarPersonaje("Guerrero", 2, 4, 2, 3);
        comprobar(gestor.getPersonajes().size() == 2, "Se agregan personajes");
        boolean duplicado = false;
        try { gestor.agregarPersonaje("CABALLERO", 1, 1, 1, 1); }
        catch(IllegalArgumentException e) { duplicado = true; }
        comprobar(duplicado, "No admite personajes duplicados");
        boolean invalido = false;
        try { gestor.agregarPersonaje("Otro", 1, -1, 2, 3); }
        catch(IllegalArgumentException e) { invalido = true; }
        comprobar(invalido, "Rechaza atributos no positivos");
        comprobar(gestor.getPersonajes().size() == 2, "No guarda personaje inválido");
        gestor.modificarPersonaje("Guerrero", 8, 7, 6, 5);
        comprobar(gestor.buscarPersonaje("Guerrero").getVida() == 8, "Modificar personaje");
        gestor.actualizarAtributo("Caballero", 2, 9);
        comprobar(gestor.buscarPersonaje("Caballero").getAtaque() == 9, "Actualizar un atributo");
        Gestor segundo = new Gestor(archivo, new Scanner(""));
        segundo.leerPersonajes();
        comprobar(segundo.getPersonajes().size() == 2, "Persistencia tras reiniciar");
        comprobar(segundo.buscarPersonaje("Caballero").getAtaque() == 9, "Persiste nuevo ataque");
        segundo.eliminarPersonaje("Guerrero");
        Gestor tercero = new Gestor(archivo, new Scanner(""));
        tercero.leerPersonajes();
        comprobar(tercero.getPersonajes().size() == 1, "Eliminación persistente");

        String texto = "Hola hola.\nJava 7, hola!\n\n123 abc";
        Path ruta = carpeta.resolve("texto.txt");
        Files.writeString(ruta, texto, StandardCharsets.UTF_8);
        ContadorPalabras contador = new ContadorPalabras();
        contador.contarArchivo(ruta.toFile());
        comprobar(contador.getLineas() == 4, "Cuenta líneas en blanco");
        comprobar(contador.getPalabras() == 7, "Cuenta secuencias de letras y dígitos");
        comprobar(contador.getFrecuencia("hola") == 3, "Frecuencia de palabras");
        comprobar(contador.getFrecuencia("123") == 1, "Números como palabra");
        comprobar(contador.getCaracteres() == 30, "Cuenta caracteres sin saltos de línea");
        boolean noExiste = false;
        try { contador.contarArchivo(carpeta.resolve("falso.txt").toFile()); }
        catch(java.io.IOException e) { noExiste = true; }
        comprobar(noExiste, "Rechaza archivos inexistentes");
        Path vacio = carpeta.resolve("vacio.txt");
        Files.writeString(vacio, "");
        contador.contarArchivo(vacio.toFile());
        comprobar(contador.getLineas() == 0 && contador.getPalabras() == 0, "Archivo vacío");

        String rutaJuegos = carpeta.resolve("videojuegos.txt").toString();
        VideojuegoModelo tienda = new VideojuegoModelo(rutaJuegos);
        tienda.leerRegistros();
        comprobar(tienda.getVideojuegos().isEmpty(), "Archivo de videojuegos inexistente");
        tienda.agregarRegistro(1, "Elden Ring", 180.0);
        tienda.agregarRegistro(2, "Minecraft", 90.50);
        comprobar(tienda.getVideojuegos().size() == 2, "Agrega registros");
        comprobar(tienda.buscarRegistro(2).getNombre().equals("Minecraft"), "Busca por ID");
        boolean idRepetido = false;
        try { tienda.agregarRegistro(1, "Otro", 50); }
        catch(IllegalArgumentException e) { idRepetido = true; }
        comprobar(idRepetido, "No permite ID duplicado");
        VideojuegoModelo recuperado = new VideojuegoModelo(rutaJuegos);
        recuperado.leerRegistros();
        comprobar(recuperado.getVideojuegos().size() == 2, "Archivo secuencial persistente");
        recuperado.eliminarRegistro(1);
        VideojuegoModelo restante = new VideojuegoModelo(rutaJuegos);
        restante.leerRegistros();
        comprobar(restante.getVideojuegos().size() == 1, "Eliminación persistente de registro");
        comprobar(restante.buscarRegistro(1) == null, "Registro eliminado no reaparece");
        comprobar(Files.readString(Path.of(rutaJuegos)).contains("2;Minecraft;90.5"), "Formato secuencial correcto");
        boolean malPrecio = false;
        try { restante.agregarRegistro(3, "Juego", -100); }
        catch(IllegalArgumentException e) { malPrecio = true; }
        comprobar(malPrecio, "No admite precios negativos");

        Path agenda = carpeta.resolve("agenda.txt");
        Files.writeString(agenda, "Ana\n999\nCercado\nLuis\n111\nCayma\n");
        ArregloPersonas contactos = Actividad5.cargarAgenda(agenda.toString());
        comprobar(contactos.getCantidad() == 2, "Carga la agenda");
        comprobar(contactos.buscar("ana") != null, "Busca contacto");
        Path java = carpeta.resolve("TestFile.java");
        Files.writeString(java, "public class TestFile {}\n");
        comprobar(Actividad4.leerArchivo(java.toString()).contains("TestFile"), "Lectura de bytes");
        Actividad1.mostrarInformacion(java.toString());
        comprobar(Files.exists(java), "Ruta para la actividad NIO");
        System.out.println("RESULTADO: " + correctas + " VERIFICACIONES CORRECTAS");
    }
}
