package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        if (args.length > 1) {
            System.out.println("Se recibió más de un argumento, solo se permite de 0 a 1 argumento");
            System.exit(1);
        }

        if (args.length == 0) {
            ejecutarRepl();
        } else {
            leerArchivo();
        }
    }

    public static void ejecutarRepl() {
        Scanner entradaTerminal = new Scanner(System.in);
        while (true) {
            System.out.print("REPL-ESCOM: ");
            if (!entradaTerminal.hasNextLine()) {
                break;
            }
            String entrada = entradaTerminal.nextLine();
            if (entrada.equalsIgnoreCase("/exit")) {
                System.out.println("Goodbye");
                break;
            }
            System.out.println(entrada);
        }
        entradaTerminal.close();
    }

    public static void leerArchivo() throws FileNotFoundException {
        File directorioActual = new File(".");
        File[] archivos = directorioActual.listFiles((dir, name) -> name.endsWith(".txt"));
        if (archivos == null || archivos.length == 0) {
            System.out.println("Error: No se encontró ningún archivo .txt en el directorio actual.");
        }

        File archivoALeer = archivos[0];
        Scanner lecturaArchivo = new Scanner(archivoALeer);

        while (lecturaArchivo.hasNextLine()) {
            System.out.println(lecturaArchivo.nextLine());
        }

        lecturaArchivo.close();
    }
}
