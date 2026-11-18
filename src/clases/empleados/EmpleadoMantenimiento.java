package clases.empleados;

import clases.Vehiculo;
import enums.EstadoVehiculo;

import java.math.BigDecimal;

public class EmpleadoMantenimiento extends Empleado {

    //----------------------------------------------------------|Constructor/es|----------------------------------------------------------

    public EmpleadoMantenimiento(int id, String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia, int legajo, BigDecimal pagoporhora, int horastrabajadas) {
        super(id, nombre, apellido, dni, nombreUsuario, email, contrasenia, legajo, pagoporhora, horastrabajadas);
    }

    //----------------------------------------------------------|Metodos|----------------------------------------------------------

    //--> Cambia el EstadoVehiculo y lo pone EN_MANTENIMIENTO el tiempo necesario para que en el sistema no figure DISPONIBLE para el Alquiler.
    public Vehiculo empezarMantenimiento(Vehiculo vehiculo){
        if(vehiculo == null ||
                vehiculo.getEstadoVehiculo() != EstadoVehiculo.DISPONIBLE ||
                vehiculo.getEstadoVehiculo() != EstadoVehiculo.RESERVADO)
            return null;

        vehiculo.setEstadoVehiculo(EstadoVehiculo.EN_MANTENIMIENTO);

        return vehiculo ;
    }

    //--> Cambia el estado del vehiculo de EN_MANTENIMIENTO a DISPONIBLE solo si este ya estaba EN_MANTENIMIENTO.
    public Vehiculo terminarMantenimiento(Vehiculo vehiculo){
        if(vehiculo == null || vehiculo.getEstadoVehiculo() != EstadoVehiculo.EN_MANTENIMIENTO )return null;

        vehiculo.setEstadoVehiculo(EstadoVehiculo.DISPONIBLE);

        return vehiculo;
    }


}
