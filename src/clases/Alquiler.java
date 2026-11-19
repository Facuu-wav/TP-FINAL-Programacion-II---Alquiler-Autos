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
    ///
    public Alquiler(int id, Reserva reserva, Usuario usuario, LocalDateTime fechaEntrega, LocalDateTime fechaDevolucion, int kilometrajeInicial, int kilometrajeFinal, EstadoAlquiler estadoAlquiler) {
        this.id = id;
        this.reserva = reserva;
        this.usuario = usuario;
        this.fechaEntrega = fechaEntrega;
        this.fechaDevolucion = fechaDevolucion;
        this.kilometrajeInicial = kilometrajeInicial;
        this.kilometrajeFinal = kilometrajeFinal;
        this.estadoAlquiler = estadoAlquiler;
    }

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
