package clases.empleados;

import enums.TipoEmpleado;

import java.math.BigDecimal;

public class Empleado extends Usuario {
   //----------------------------------------------------------|Atributos|----------------------------------------------------------

    private TipoEmpleado tipoEmpleado;
    private int legajo;
    private BigDecimal pagoPorHora;
    private int horasTrabajadas;

    //----------------------------------------------------------|constructores|----------------------------------------------------------

    public Empleado(int id, String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia, TipoEmpleado tipoEmpleado, int legajo, BigDecimal pagoPorHora, int horasTrabajadas) {
        super(id, nombre, apellido, dni, nombreUsuario, email, contrasenia);
        this.tipoEmpleado = tipoEmpleado;
        this.legajo = legajo;
        this.pagoPorHora = pagoPorHora;
        this.horasTrabajadas = horasTrabajadas;
    }

    //----------------------------------------------------------|Getters|----------------------------------------------------------


    public TipoEmpleado getTipoEmpleado() {
        return tipoEmpleado;
    }

    public int getLegajo() {
        return legajo;
    }


    //----------------------------------------------------------|Métodos|----------------------------------------------------------


    @Override
    public String toString() {
        return super.toString() + "Empleado{" +
                "tipoEmpleado=" + tipoEmpleado +
                ", legajo=" + legajo +
                ", pagoPorHora=" + pagoPorHora +
                ", horasTrabajadas=" + horasTrabajadas +
                '}';
    }
}
