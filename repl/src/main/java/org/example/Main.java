package org.example;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entradaTerminal = new Scanner(System.in);
        if (args.length == 0) {
            while (true) {
                System.out.print("REPL-ESCOM: ");
                if (!entradaTerminal.hasNextLine()) {
                    break;
                } else if (entradaTerminal.nextLine().equals("salir"))
                    break;
            }
        } else if (args.length == 1) {
            File rutaDelArchivo = new File("/home/salvador/Escritorio/repl.txt");
            String caracteres = entradaTerminal.nextLine();

        }

    }

    }
