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

    /// Constructor/es

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
