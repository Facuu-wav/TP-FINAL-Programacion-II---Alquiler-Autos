package clases;

import clases.empleados.Usuario;
import enums.EstadoReserva;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva {

    //----------------------------------------------------------|Atributos|----------------------------------------------------------
    private int id;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private Usuario usuario;

    private LocalDateTime fechaReserva;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private EstadoReserva estadoReserva;

    //----------------------------------------------------------|Constructor/es|----------------------------------------------------------

    public Reserva(int id, Cliente cliente, Vehiculo vehiculo, Usuario usuario, LocalDateTime fechaReserva, LocalDate fechaInicio, LocalDate fechaFin, EstadoReserva estadoReserva) {
        this.id = id;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.usuario = usuario;
        this.fechaReserva = fechaReserva;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoReserva = estadoReserva;
    }

    //----------------------------------------------------------|Getters|----------------------------------------------------------

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    //----------------------------------------------------------|Setters|----------------------------------------------------------

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    //----------------------------------------------------------|Métodos|----------------------------------------------------------


}