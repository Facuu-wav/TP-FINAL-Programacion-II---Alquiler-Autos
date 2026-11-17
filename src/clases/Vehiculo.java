package clases;

import enums.EstadoVehiculo;
import enums.Marca;

import java.math.BigDecimal;
import java.util.Objects;

public class Vehiculo {

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
    public boolean equals(Object o) {
        if(this == o)return true;

        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return id == vehiculo.id && anio == vehiculo.anio && kilometraje == vehiculo.kilometraje && Objects.equals(patente, vehiculo.patente) && marca == vehiculo.marca && Objects.equals(modelo, vehiculo.modelo) && estadoVehiculo == vehiculo.estadoVehiculo && Objects.equals(precioPorDia, vehiculo.precioPorDia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patente, marca, modelo, anio, kilometraje, estadoVehiculo, precioPorDia);
    }
}