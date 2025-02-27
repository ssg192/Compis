package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        if (args.length > 1) {
            System.out.println("Se recibió más de un argumento");
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
        String[] extensiones = {".txt", ".csv"};
        File directorioActual = new File("/home/salvador/Escritorio/Compis/");
        File archivoAleer = null;
        File[] archivos = directorioActual.listFiles();

        for (File archivo : archivos) {
            for (String extensionValidas : extensiones) {
                if (archivo.getName().endsWith(extensionValidas)) {
                    archivoAleer = archivo;
                    break;
                }
            }
        }
        if (archivoAleer == null) {
            System.out.println("No se encontró ningún archivo válido en el directorio.");
        } else {
            Scanner lecturaArchivo = new Scanner(archivoAleer);
            while (lecturaArchivo.hasNextLine()) {
                System.out.println(lecturaArchivo.nextLine());
            }
            lecturaArchivo.close();
        }
    }
}
