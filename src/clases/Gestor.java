package clases;

import interfaces.IObtenerID;

import java.util.Collection;

public class Gestor <T extends IObtenerID> {

    /// Atributos
    private Collection<T> elementos;

    /// Constructor/es
    public Gestor(Collection<T> elementos) {
        this.elementos = elementos;
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




}
