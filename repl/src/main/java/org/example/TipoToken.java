package org.example;

public enum TipoToken {
    // Palabras reservadas
    ELSE, IF, PRINT, RETURN, FOR, WHILE, VAR, TRUE, FALSE, NULL, FUN,

    // Operadores y símbolos
    GREATER, GREATER_EQUAL, LESS, LESS_EQUAL, NOT, NOT_EQUAL, AND, OR,DIV,EQUALS,QUOTES,ASSIGN,STRING,SLASH,EQUAL_EQUAL,PLUS,MINUS,BANG,STAR,

    // Tipos de datos
    NUMBER, CADENA, ID,

    // Signos de puntuación
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, COMA, SEMICOLON,

    // Fin de archivo
    EOF
}
