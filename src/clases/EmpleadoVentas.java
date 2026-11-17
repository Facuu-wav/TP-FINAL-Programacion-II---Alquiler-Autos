package clases;

import enums.EstadoReserva;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmpleadoVentas extends Empleado {

    //----------------------------------------------------------|Constructor/es|----------------------------------------------------------

    public EmpleadoVentas(int id, String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia, int legajo, BigDecimal pagoporhora, int horastrabajadas) {
        super(id, nombre, apellido, dni, nombreUsuario, email, contrasenia, legajo, pagoporhora, horastrabajadas);
    }

    //----------------------------------------------------------|Metodos|----------------------------------------------------------

    //-->
    public void generarReserva(){
    }

    public void generarAlquiler(){
    }


}
