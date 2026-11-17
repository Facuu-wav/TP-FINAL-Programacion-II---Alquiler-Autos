package clases;

import enums.Sector;

public class Empleado extends Usuario {
   private int legajo;

    //CONSTRUCTOR/ES
    public Empleado(int id, String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia, int legajo) {
        super(id, nombre, apellido, dni, nombreUsuario, email, contrasenia);
        this.legajo = legajo;
    }

    //GETTERS
    public int getLegajo() {
        return legajo;
    }

    //METODOS

}
