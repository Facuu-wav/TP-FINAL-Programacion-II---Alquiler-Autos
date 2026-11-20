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

    public Reserva getReserva() {
        return reserva;
    }

    public int getKilometrajeInicial() {
        return kilometrajeInicial;
    }


    public EstadoAlquiler getEstadoAlquiler() {
        return estadoAlquiler;
    }

    /// Setters
    public void setKilometrajeFinal(int kilometrajeFinal) {
        this.kilometrajeFinal = kilometrajeFinal;
    }

    public void setEstadoAlquiler(EstadoAlquiler estadoAlquiler) {
        this.estadoAlquiler = estadoAlquiler;
    }

    /// Métodos
    @Override
    public int obtenerID() {
        return getId();
    }




}
