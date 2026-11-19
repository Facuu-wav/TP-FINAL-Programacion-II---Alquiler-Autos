package clases;

import interfaces.IObtenerID;

import java.time.LocalDate;

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
    }

    /// Getters
    public int getId() {
        return id;
    }

    /// Setters

    /// Métodos
    @Override
    public int obtenerID() {
        return getId();
    }
}
