package clases.empleados;

public class Administrador extends Usuario {

    public Administrador(int id, String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia) {
        super(id, nombre, apellido, dni, nombreUsuario, email, contrasenia);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
