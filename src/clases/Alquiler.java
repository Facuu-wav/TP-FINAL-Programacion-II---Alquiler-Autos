package clases;

import clases.empleados.Usuario;
import enums.EstadoAlquiler;
import interfaces.IObtenerID;

import java.time.LocalDateTime;

public class Alquiler implements IObtenerID {

    /// Atributos
    private int id;
    private Reserva reserva;
    private Usuario usuario;

    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaDevolucion;

    private int kilometrajeInicial;
    private int kilometrajeFinal;

    private EstadoAlquiler estadoAlquiler;

    /// Constructor/es

    /// Getters
    public int getId() {
        return id;
    }

    /// Métodos
    @Override
    public int obtenerID() {
        return getId();
    }




}
