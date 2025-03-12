package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lector {

    private String usuario_oracion;
    private List<Token> tokens = new ArrayList<>();
    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("else", TipoToken.ELSE);
        palabrasReservadas.put("if", TipoToken.IF);
        palabrasReservadas.put("print", TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("for", TipoToken.FOR);
        palabrasReservadas.put("while", TipoToken.WHILE);
        palabrasReservadas.put("var", TipoToken.VAR);
        palabrasReservadas.put("true", TipoToken.TRUE);
        palabrasReservadas.put("false", TipoToken.FALSE);
        palabrasReservadas.put("null", TipoToken.NULL);
        palabrasReservadas.put("fun", TipoToken.FUN);
    }

    public Lector(String usuario_oracion) {
        this.usuario_oracion = usuario_oracion + " "; // Agregar espacio al final para detectar EOF
    }

    public List<Token> scan() throws Exception {
        int estado = 0;
        String lexema = "";
        int linea = 1; // Empezamos con la primera línea

        for (int i = 0; i < usuario_oracion.length(); i++) {
            char preanalisis = usuario_oracion.charAt(i);

            // Detectar saltos de línea para incrementar la línea
            if (preanalisis == '\n') {
                linea++;
            }

            switch (estado) {
                case 0:
                    if (preanalisis == '>') {
                        estado = 1;
                        lexema += preanalisis;
                    } else if (preanalisis == '<') {
                        estado = 2;
                        lexema += preanalisis;
                    } else if (preanalisis == '!') {
                        estado = 3;
                        lexema += preanalisis;
                    } else if (preanalisis == '&') {
                        estado = 4;
                        lexema += preanalisis;
                    } else if (preanalisis == '|') {
                        estado = 5;
                        lexema += preanalisis;
                    } else if (Character.isLetter(preanalisis)) {
                        estado = 13;
                        lexema += preanalisis;
                    } else if (preanalisis == '"') { // Para las cadenas
                        estado = 6;
                        lexema = "";
                    } else if (esSignoPuntuacion(preanalisis)) {
                        tokens.add(new Token(getTipoSignoPuntuacion(preanalisis), Character.toString(preanalisis), linea));
                    } else if (Character.isDigit(preanalisis)) { // Números
                        estado = 7;
                        lexema = "" + preanalisis;
                    }
                    break;

                case 1: // Manejar '>' y '>='
                    if (preanalisis == '=') {
                        lexema += preanalisis;
                        tokens.add(new Token(TipoToken.GREATER_EQUAL, lexema, linea));
                    } else {
                        tokens.add(new Token(TipoToken.GREATER, ">", linea));
                        i--;
                    }
                    estado = 0;
                    lexema = "";
                    break;

                case 2: // Manejar '<' y '<='
                    if (preanalisis == '=') {
                        lexema += preanalisis;
                        tokens.add(new Token(TipoToken.LESS_EQUAL, lexema, linea));
                    } else {
                        tokens.add(new Token(TipoToken.LESS, "<", linea));
                        i--;
                    }
                    estado = 0;
                    lexema = "";
                    break;

                case 3: // Manejar '!' y '!='
                    if (preanalisis == '=') {
                        lexema += preanalisis;
                        tokens.add(new Token(TipoToken.NOT_EQUAL, lexema, linea));
                    } else {
                        tokens.add(new Token(TipoToken.NOT, "!", linea));
                        i--;
                    }
                    estado = 0;
                    lexema = "";
                    break;

                case 4: // Manejar '&' y '&&'
                    if (preanalisis == '&') {
                        lexema += preanalisis;
                        tokens.add(new Token(TipoToken.AND, lexema, linea));
                    } else {
                        throw new Exception("Error léxico: '&' no es un token válido");
                    }
                    estado = 0;
                    lexema = "";
                    break;

                case 5: // Manejar '|' y '||'
                    if (preanalisis == '|') {
                        lexema += preanalisis;
                        tokens.add(new Token(TipoToken.OR, lexema, linea));
                    } else {
                        throw new Exception("Error léxico: '|' no es un token válido");
                    }
                    estado = 0;
                    lexema = "";
                    break;

                case 6: // Manejo de cadenas (texto entre comillas)
                    if (preanalisis == '"') { // Fin de la cadena
                        tokens.add(new Token(TipoToken.CADENA, lexema, lexema, linea));
                        estado = 0; // Regresamos al estado inicial
                    } else {
                        lexema += preanalisis; // Continuamos leyendo la cadena
                    }
                    break;

                case 7: // Números
                    if (Character.isDigit(preanalisis) || preanalisis == '.') {
                        lexema += preanalisis;
                    } else {
                        tokens.add(new Token(TipoToken.NUMBER, lexema, Double.parseDouble(lexema), linea));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;

                case 13: // Identificadores y palabras reservadas
                    if (Character.isLetterOrDigit(preanalisis)) {
                        lexema += preanalisis;
                    } else {
                        TipoToken tt = palabrasReservadas.get(lexema);
                        if (tt == null) {
                            tokens.add(new Token(TipoToken.ID, lexema, linea));
                        } else {
                            tokens.add(new Token(tt, lexema, linea));
                        }
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
            }
        }

        // Agregar el token EOF al final
        tokens.add(new Token(TipoToken.EOF, "$", linea));

        return tokens;
    }

    private boolean esSignoPuntuacion(char c) {
        return c == '(' || c == ')' || c == '{' || c == '}' || c == ',' || c == ';';
    }

    private TipoToken getTipoSignoPuntuacion(char c) {
        switch (c) {
            case '(': return TipoToken.LEFT_PAREN;
            case ')': return TipoToken.RIGHT_PAREN;
            case '{': return TipoToken.LEFT_BRACE;
            case '}': return TipoToken.RIGHT_BRACE;
            case ',': return TipoToken.COMA;
            case ';': return TipoToken.SEMICOLON;
            default: throw new IllegalArgumentException("Carácter de puntuación no reconocido: " + c);
        }
    }

}
