package clases;

public class EmpleadoMantenimiento extends Empleado{

    //CONSTRUCTOR/ES

    public EmpleadoMantenimiento(int id, String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia, int legajo) {
        super(id, nombre, apellido, dni, nombreUsuario, email, contrasenia, legajo);
    }

    //METODOS

    //dar mantenimiento: cambia EstadoVehiculo

    public Vehiculo darMantenimiento(Vehiculo vehiculo){
        if(vehiculo == null)return null;
        retrurn vehiculo;
    }

}
