#!/bin/sh
set -eu
cd "$(dirname "$0")/.."
mkdir -p Lab7_ej1/bin Lab7_ej3/bin Lab7_ej4/bin actividades/bin pruebas/bin
javac -encoding UTF-8 -d Lab7_ej1/bin $(find Lab7_ej1/src -name '*.java')
javac -encoding UTF-8 -d Lab7_ej3/bin $(find Lab7_ej3/src -name '*.java')
javac -encoding UTF-8 -d Lab7_ej4/bin $(find Lab7_ej4/src -name '*.java')
javac -encoding UTF-8 -d actividades/bin $(find actividades/src -name '*.java')
javac -encoding UTF-8 -cp Lab7_ej1/bin:Lab7_ej3/bin:Lab7_ej4/bin:actividades/bin -d pruebas/bin pruebas/Verificacion.java
java -cp Lab7_ej1/bin:Lab7_ej3/bin:Lab7_ej4/bin:actividades/bin:pruebas/bin Verificacion
