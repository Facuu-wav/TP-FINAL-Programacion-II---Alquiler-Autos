package clases.empleados;

import enums.TipoUsuario;

import java.math.BigDecimal;

public class Empleado extends Usuario {
   //----------------------------------------------------------|Atributos|----------------------------------------------------------

    private int legajo;
    private BigDecimal pagoporhora;
    private int horastrabajadas;


    //----------------------------------------------------------|constructores|----------------------------------------------------------

    public Empleado(int id, String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia, TipoUsuario tipoUsuario, int legajo, BigDecimal pagoporhora, int horastrabajadas) {
        super(id, nombre, apellido, dni, nombreUsuario, email, contrasenia, tipoUsuario);
        this.legajo = legajo;
        this.pagoporhora = pagoporhora;
        this.horastrabajadas = horastrabajadas;
    }

    //----------------------------------------------------------|Getters|----------------------------------------------------------

    public int getLegajo() {
        return legajo;
    }

    //----------------------------------------------------------|Metodos|----------------------------------------------------------


}
