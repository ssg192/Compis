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
            leerArchivo(args[0]);
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
        File archivo = new File(ruta);

        if (!archivo.exists()) {
            System.out.println("Archivo no encontrado");
            return;
        }

        if (!archivo.isFile()) {
            System.out.println("La ruta proporcionada no es un archivo válido.");
            return;
        }

        Scanner lecturaArchivo = new Scanner(archivo);
        while (lecturaArchivo.hasNextLine()) {
            System.out.println(lecturaArchivo.nextLine());
        }
        lecturaArchivo.close();
    }
}