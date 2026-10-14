# Laboratorio 7 - Manejo de archivos en Java

Lenguajes de Programación III - Universidad Católica de Santa María.

## Contenido

- `actividades`: siete actividades de manejo de archivos, lectura/escritura, serialización y Swing.
- `Lab7_ej1`: ejercicios 1 y 2, gestor de personajes con archivo `personajes.dat`.
- `Lab7_ej3`: contador de palabras de un archivo mediante `JFileChooser`.
- `Lab7_ej4`: gestión de videojuegos con MVC y archivo secuencial.
- `pruebas`: verificaciones de los proyectos Java.
- `docs`: informe académico y evidencias de ejecución.

## Ejecución

Los proyectos pueden importarse en Eclipse como proyectos Java independientes. Se necesita Java 17 o superior.

Desde la raíz del repositorio:

```bash
javac -encoding UTF-8 -d Lab7_ej1/bin $(find Lab7_ej1/src -name '*.java')
java -cp Lab7_ej1/bin personajes.Gestor
```

Para los demás programas se usa el mismo procedimiento, cambiando la carpeta y la clase principal:

- `Lab7_ej3`: `contador.ContadorPalabras`
- `Lab7_ej4`: `principal.Main`
- `actividades`: `actividades.Actividad1` hasta `actividades.Actividad7`

Los datos de `Lab7_ej1` se guardan en `personajes.dat` en la carpeta desde la que se ejecuta el programa. El ejercicio 4 utiliza `videojuegos.txt` del mismo modo.

El contador abre un selector de archivos al ejecutarse; también acepta una ruta por argumento para ejecutar las pruebas desde terminal.

## Verificación

En Linux, desde la raíz:

```bash
sh pruebas/ejecutar.sh
```

El programa de verificación comprueba creación, modificación, lectura, eliminación y persistencia de datos. Los archivos de prueba se crean en una carpeta temporal.
