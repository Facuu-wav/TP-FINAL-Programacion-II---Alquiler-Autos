package clases;

import java.util.ArrayList;

public class Gestor <T> {

    /// Atributos
    private ArrayList<T> lista;

    /// Constructor/es
    public Gestor() {
        this.lista = new ArrayList<>();
    }

    /// Métodos
    public void agregarElemento(T e) {
        lista.add(e);
    }




}
