package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        if (args.length > 1) {
            System.out.println("Se recibio mas de un argumento, solo se permite 0 a 1 argumento");
            System.exit(1);
        }

        if (args.length == 0) {
            ejecutarRepl();
        } else {
            leerArchivo("src/repl.txt");
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

    public static void leerArchivo(String ruta) throws FileNotFoundException {
        File rutaDelArchivo = new File(ruta);
        if (!rutaDelArchivo.exists() || !rutaDelArchivo.isFile()) {
            System.out.println("Error: El archivo especificado no existe o no es un archivo válido.");
            System.exit(1);
        }
        System.out.println(rutaDelArchivo);
        Scanner lecturaArchivo = new Scanner(rutaDelArchivo);
        while (lecturaArchivo.hasNextLine()) {
            System.out.println(lecturaArchivo.nextLine());
        }
    }
}
