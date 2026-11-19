package clases.empleados;

import enums.TipoUsuario;

public class Administrador extends Usuario {

    public Administrador(int id, String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia, TipoUsuario tipoUsuario) {
        super(id, nombre, apellido, dni, nombreUsuario, email, contrasenia, tipoUsuario);
    }
}
