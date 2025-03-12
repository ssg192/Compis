package org.example;

public class Token {
    private final TipoToken tipo;
    private final String lexema;
    private final Object valor;
    private final int linea;

    public Token(TipoToken tipo, String lexema, int linea) {
        this(tipo, lexema, null, linea);
    }

    public Token(TipoToken tipo, String lexema, Object valor, int linea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.valor = valor;
        this.linea = linea;
    }

    public TipoToken getTipo() {
        return tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public Object getValor() {
        return valor;
    }

    public int getLinea() {
        return linea;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tipo=" + tipo +
                ", lexema='" + lexema + '\'' +
                ", valor=" + valor +
                ", linea=" + linea +
                '}';
    }
}
