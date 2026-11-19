package clases;

import enums.EstadoVehiculo;
import enums.Marca;
import interfaces.IObtenerID;

import java.math.BigDecimal;
import java.util.Objects;

public class Vehiculo implements IObtenerID {

    /// ----------------------------------------------------------|Atributos|----------------------------------------------------------
    private final int id;
    private String patente;
    private Marca marca;
    private String modelo;
    private int anio;
    private int kilometraje;
    private EstadoVehiculo estadoVehiculo;
    private BigDecimal precioPorDia;

    //----------------------------------------------------------|Constructor/es|----------------------------------------------------------
    public Vehiculo(int id, String patente, Marca marca, String modelo, int anio, int kilometraje, EstadoVehiculo estadoVehiculo, BigDecimal precioPorDia) {
        this.id = id;
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.kilometraje = kilometraje;
        this.estadoVehiculo = estadoVehiculo;
        this.precioPorDia = precioPorDia;
    }


    //----------------------------------------------------------|Getters|----------------------------------------------------------
    public int getId() {
        return id;
    }

    public String getPatente() {
        return patente;
    }

    public Marca getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnio() {
        return anio;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public EstadoVehiculo getEstadoVehiculo() {
        return estadoVehiculo;
    }

    public BigDecimal getPrecioPorDia() {
        return precioPorDia;
    }

    //----------------------------------------------------------|Setters|----------------------------------------------------------
    public void setPatente(String patente) {
        this.patente = patente;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public void setEstadoVehiculo(EstadoVehiculo estadoVehiculo) {
        this.estadoVehiculo = estadoVehiculo;
    }

    public void setPrecioPorDia(BigDecimal precioPorDia) {
        this.precioPorDia = precioPorDia;
    }

    //----------------------------------------------------------|Metodos|----------------------------------------------------------


    @Override
    public String toString() {
        return "Vehiculo{" +
                "id=" + id +
                ", patente='" + patente + '\'' +
                ", marca=" + marca +
                ", modelo='" + modelo + '\'' +
                ", anio=" + anio +
                ", kilometraje=" + kilometraje +
                ", estadoVehiculo=" + estadoVehiculo +
                ", precioPorDia=" + precioPorDia +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vehiculo vehiculo)) return false;
        return id == vehiculo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public int obtenerID() {
        return getId();
    }
}