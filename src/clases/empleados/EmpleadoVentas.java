package clases.empleados;

import enums.TipoUsuario;

import java.math.BigDecimal;

public class EmpleadoVentas extends Empleado {

    //----------------------------------------------------------|Constructor/es|----------------------------------------------------------

    public EmpleadoVentas(int id, String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia, TipoUsuario tipoUsuario, int legajo, BigDecimal pagoporhora, int horastrabajadas) {
        super(id, nombre, apellido, dni, nombreUsuario, email, contrasenia, tipoUsuario, legajo, pagoporhora, horastrabajadas);
    }


    //----------------------------------------------------------|Metodos|----------------------------------------------------------

    //-->
    public void generarReserva(){
    }

    public void generarAlquiler(){
    }


}
