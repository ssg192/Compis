package org.example;

public class Token {
    private final TipoToken tipo;
    private final String lexema;
    private final Object literal;
    private final int linea;

    public Token(TipoToken tipo, String lexema, int linea) {
        this(tipo, lexema, null, linea);
    }

    public Token(TipoToken tipo, String lexema, Object literal, int linea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.linea = linea;
    }

    public TipoToken getTipo() {
        return tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public Object getValor() {
        return literal;
    }

    public int getLinea() {
        return linea;
    }

    @Override
    public String toString() {
        if (literal != null) {
            return "<" + tipo + ", lexema:'" + lexema + "', literal:" + literal + ", linea:" + linea + ">";
        } else {
            return "<" + tipo + ", lexema:'" + lexema + "', linea:" + linea + ">";
        }
    }
}
