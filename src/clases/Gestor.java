package clases;

import interfaces.IObtenerID;

import java.util.LinkedHashSet;
import java.util.Set;

public class Gestor <T extends IObtenerID> {

    /// Atributos
    private Set<T> elementos;

    /// Constructor/es
    public Gestor() {
        this.elementos = new LinkedHashSet<>();
    }

    /// Métodos
    public void agregar(T e) {
        elementos.add(e);
    }

    public T buscar(int id) {
        for (T e : elementos) {
            if (e.obtenerID() == id) {
                return e;
            }
        }
        return null;
    }

    public boolean existe(T elemento) {
        if (elemento == null) {
            return false;
        }
        return elementos.stream().anyMatch(e -> e.obtenerID() == elemento.obtenerID());
    }

    public boolean existe(int id) {
        return elementos.stream().anyMatch(e -> e.obtenerID() == id);
    }




}
