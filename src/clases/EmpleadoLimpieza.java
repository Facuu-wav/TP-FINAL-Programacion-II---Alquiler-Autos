package clases;

import enums.EstadoVehiculo;

import java.math.BigDecimal;

public class EmpleadoLimpieza extends Empleado {

    //----------------------------------------------------------|Constructor/es|----------------------------------------------------------

    public EmpleadoLimpieza(int id, String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia, int legajo, BigDecimal pagoporhora, int horastrabajadas) {
        super(id, nombre, apellido, dni, nombreUsuario, email, contrasenia, legajo, pagoporhora, horastrabajadas);
    }

    //----------------------------------------------------------|Metodos|----------------------------------------------------------

    //--> Cambia EstadoVehiculo a EN_LIMPIEZA solo si esta DISPONIBLE o RESERVADO.
    public Vehiculo empezarLimpieza(Vehiculo vehiculo){
        if(vehiculo == null ||
                vehiculo.getEstadoVehiculo() != EstadoVehiculo.DISPONIBLE ||
                vehiculo.getEstadoVehiculo() != EstadoVehiculo.RESERVADO)
            return null;

        vehiculo.setEstadoVehiculo(EstadoVehiculo.EN_LIMPIEZA);

        return vehiculo;
    }

    //--> Cambia EstadoVehiculo a DISPONIBLE solo si esta EN_LIMPIEZA.
    public Vehiculo terminarLimpieza(Vehiculo vehiculo){
        if(vehiculo == null || vehiculo.getEstadoVehiculo() != EstadoVehiculo.EN_LIMPIEZA)return null;

        vehiculo.setEstadoVehiculo(EstadoVehiculo.DISPONIBLE);

        return vehiculo;
    }

}
