package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lector {
    private final String usuario_oracion;
    private final List<Token> tokens = new ArrayList<>();
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
        this.usuario_oracion = usuario_oracion + " ";
    }

    public List<Token> scan() throws Exception {
        int estado = 0;
        String lexema = "";
        int linea = 1;

        for (int i = 0; i < usuario_oracion.length(); i++) {
            char preanalisis = usuario_oracion.charAt(i);

            switch (estado) {
                case 0:
                    if (Character.isWhitespace(preanalisis)) {
                        if (preanalisis == '\n') linea++;
                    } else if (preanalisis == '>' && i + 1 < usuario_oracion.length() && usuario_oracion.charAt(i + 1) == '=') {
                        tokens.add(new Token(TipoToken.GREATER_EQUAL, ">=", linea));
                        i++;
                    } else if (preanalisis == '<' && i + 1 < usuario_oracion.length() && usuario_oracion.charAt(i + 1) == '=') {
                        tokens.add(new Token(TipoToken.LESS_EQUAL, "<=", linea));
                        i++;
                    } else if (preanalisis == '!' && i + 1 < usuario_oracion.length() && usuario_oracion.charAt(i + 1) == '=') {
                        tokens.add(new Token(TipoToken.NOT_EQUAL, "!=", linea));
                        i++;
                    } else if (preanalisis == '&' && i + 1 < usuario_oracion.length() && usuario_oracion.charAt(i + 1) == '&') {
                        tokens.add(new Token(TipoToken.AND, "&&", linea));
                        i++;
                    } else if (preanalisis == '|' && i + 1 < usuario_oracion.length() && usuario_oracion.charAt(i + 1) == '|') {
                        tokens.add(new Token(TipoToken.OR, "||", linea));
                        i++;
                    } else if (Character.isLetter(preanalisis)) {
                        estado = 6;
                        lexema = "" + preanalisis;
                    } else if (preanalisis == '"') {
                        estado = 7;
                        lexema = "";
                    } else if (esSignoPuntuacion(preanalisis)) {
                        tokens.add(new Token(getTipoSignoPuntuacion(preanalisis), Character.toString(preanalisis), linea));
                    } else if (Character.isDigit(preanalisis)) {
                        estado = 8;
                        lexema = "" + preanalisis;
                    } else if (preanalisis == '=') {
                        estado = 9;
                        lexema = "" + preanalisis;
                    } else if (preanalisis == '*') {
                        tokens.add(new Token(TipoToken.STAR, "*", linea));
                    } else {
                        throw new Exception("Carácter inesperado '" + preanalisis + "' en la línea " + linea);
                    }
                    break;

                case 6:
                    if (Character.isLetterOrDigit(preanalisis)) {
                        lexema += preanalisis;
                    } else {
                        if (palabrasReservadas.containsKey(lexema)) {
                            tokens.add(new Token(palabrasReservadas.get(lexema), lexema, linea));
                        } else {
                            tokens.add(new Token(TipoToken.ID, lexema, null, linea));
                        }
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;

                case 7:
                    if (preanalisis == '"') {
                        tokens.add(new Token(TipoToken.CADENA, lexema, lexema, linea));
                        estado = 0;
                        lexema = "";
                    } else {
                        lexema += preanalisis;
                    }
                    break;

                case 8: // Números
                    if (Character.isDigit(preanalisis)) {
                        lexema += preanalisis;
                    } else if (preanalisis == 'E' || preanalisis == 'e') {
                        lexema += preanalisis;
                        estado = 10;
                    } else {
                        tokens.add(new Token(TipoToken.NUMBER, lexema, lexema, linea));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;

                case 9: // Asignación
                    if (preanalisis == '=') {
                        lexema += preanalisis;
                        tokens.add(new Token(TipoToken.EQUALS, lexema, linea));
                    } else {
                        tokens.add(new Token(TipoToken.ASSIGN, lexema, linea));
                        i--;
                    }
                    estado = 0;
                    lexema = "";
                    break;

                case 10:
                    if (Character.isDigit(preanalisis)) {
                        lexema += preanalisis;
                    } else {
                        tokens.add(new Token(TipoToken.NUMBER, lexema, lexema, linea));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
            }
        }
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
            default:
                throw new IllegalArgumentException("Carácter de puntuación no reconocido: " + c);
        }
    }
}
