package clases;

import clases.empleados.Usuario;
import enums.TipoUsuario;
import excepciones.AccesoDenegadoEx;
import excepciones.ContraseniaIncorrectaEx;
import excepciones.UsuarioNoEncontradoEx;
import sql.BDHandler;

import java.sql.SQLException;
import java.util.HashSet;

public class Sistema {

    /// Atributos
    private Gestor<Reserva> reservas;
    private Gestor<Alquiler> alquileres;
    private HashSet<Vehiculo> vehiculos;

    /// Constructor/es
    public Sistema() {
        this.reservas = new Gestor<>();
        this.alquileres = new Gestor<>();
        this.vehiculos = new HashSet<>(); // vehiculos debería ser hashset?
    }

    /// Métodos

    // Gestión de usuarios del sistema
    public Usuario iniciarSesion(String nombreUsuario, String contrasenia) throws SQLException, UsuarioNoEncontradoEx, ContraseniaIncorrectaEx {

        Usuario usuario = BDHandler.buscarUsuario(nombreUsuario);

        if (usuario == null) {
            throw new UsuarioNoEncontradoEx("El usuario no existe.");
        }

        if (!usuario.getContrasenia().equals(contrasenia)) {
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

    private boolean tienePermiso(Usuario usuario, TipoUsuario... tipos) {
        for (TipoUsuario tipo : tipos) {
            if (usuario.getTipoUsuario() == tipo) {
                return true;
            }
        }
        return false;
    }

    // Gestión de vehículos
    public void agregarVehiculo(Usuario usuario) throws AccesoDenegadoEx {

        TipoUsuario tipo = usuario.getTipoUsuario();

        if (!tienePermiso(usuario, TipoUsuario.ADMINISTRADOR, TipoUsuario.EMPLEADO_VENTAS)) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }

        vehiculos.add(vehiculo);
    }

    // Gestión de reservas
    public void agregarReserva() throws AccesoDenegadoEx {

    }

    // Gestión de alquileres
    public void agregarAlquiler()  throws AccesoDenegadoEx {

    }

}
