package clases;

import clases.empleados.Usuario;
import excepciones.AccesoDenegadoEx;
import excepciones.ContraseniaIncorrectaEx;
import excepciones.UsuarioNoEncontradoEx;

public class Sistema {

    /// Atributos
    private Gestor<Reserva> reservas;
    private Gestor<Alquiler> alquileres;

    /// Constructor/es
    public Sistema() {
        this.reservas = new Gestor<>();
        this.alquileres = new Gestor<>();
    }

    /// Métodos

    // Gestión de usuarios del sistema
    public Usuario iniciarSesion(String nombreUsuario, String contrasenia) throws UsuarioNoEncontradoEx, ContraseniaIncorrectaEx {

        Usuario usuario = DBHandler.buscarUsuario(nombreUsuario);

        if (usuario == null) {
            throw new UsuarioNoEncontradoEx("El usuario no existe.");
        }

        if (!DBHandler.verificarContrasenia(contrasenia, usuario.getContrasenia())) {
            throw new ContraseniaIncorrectaEx("La contraseña es incorrecta.");
        }

        return usuario;
    }

    public void agregarUsuario() throws AccesoDenegadoEx {

    }

    public void mostrarPermisos() {

    }

    // Gestión de clientes
    public void agregarCliente() throws AccesoDenegadoEx {

    }

    // Gestión de vehículos
    public void agregarVehiculo() throws AccesoDenegadoEx {

    }

    // Gestión de reservas
    public void agregarReserva() throws AccesoDenegadoEx {

    }

    // Gestión de alquileres
    public void agregarAlquiler()  throws AccesoDenegadoEx {

    }

}
