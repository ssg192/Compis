package org.example;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entradaTerminal = new Scanner(System.in);
            if(args.length == 0) {
                do {
                    System.out.print("REPL-escom: ");
                } while (!entradaTerminal.nextLine().equals("salir"));
            }else if(args.length == 1){
                File rutaDelArchivo = new File("/home/salvador/Escritorio/repl.txt");

            }
            else{
                System.out.println("Se recibió más de un argumento");
                System.exit(1);
            }
            }


    }
