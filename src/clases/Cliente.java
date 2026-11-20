package clases;

import interfaces.IObtenerID;

import java.time.LocalDate;
import java.util.Objects;

public class Cliente implements IObtenerID {

    /// Atributos
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String licenciaConducir;
    private LocalDate vencimientoLicencia;
    private boolean activo;

    /// Constructor/es
    public Cliente(int id, String nombre, String apellido, String dni, String telefono, String licenciaConducir, LocalDate vencimientoLicencia) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.licenciaConducir = licenciaConducir;
        this.vencimientoLicencia = vencimientoLicencia;
        this.activo = true;
    }

    public Cliente(int id, String nombre, String apellido,
                   String dni, String telefono,
                   String licenciaConducir,
                   LocalDate vencimientoLicencia,
                   boolean activo) {

        this(id, nombre, apellido, dni, telefono,
                licenciaConducir, vencimientoLicencia);

        this.activo = activo;
    }

    /// Getters
    public int getId() {
        return id;
    }

    /// Setters

    /// Métodos

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", telefono='" + telefono + '\'' +
                ", licenciaConducir='" + licenciaConducir + '\'' +
                ", vencimientoLicencia=" + vencimientoLicencia +
                ", activo=" + activo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cliente cliente)) return false;
        return id == cliente.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public int obtenerID() {
        return getId();
    }

    public boolean isActivo() {
        return this.activo;
    }
}
