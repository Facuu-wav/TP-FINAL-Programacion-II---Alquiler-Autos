package clases;

import clases.empleados.Usuario;
import enums.EstadoVehiculo;
import enums.Marca;
import enums.TipoUsuario;
import excepciones.AccesoDenegadoEx;
import excepciones.ContraseniaIncorrectaEx;
import excepciones.UsuarioNoEncontradoEx;
import sql.DBHandler;
import sql.VehiculoDAO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class Sistema {

    /// Atributos
    private Gestor<Reserva> reservas;
    private Gestor<Alquiler> alquileres;
    private Gestor<Vehiculo> vehiculos;

    /// Constructor/es
    public Sistema() {
        this.reservas = new Gestor<>(new ArrayList<>());
        this.alquileres = new Gestor<>(new ArrayList<>());
        this.vehiculos = new Gestor<>(new HashSet<>());
    }

    /// Métodos

    // Gestión de usuarios del sistema
    public Usuario iniciarSesion(String nombreUsuario, String contrasenia) throws SQLException, UsuarioNoEncontradoEx, ContraseniaIncorrectaEx {

        Usuario usuario = DBHandler.buscarUsuario(nombreUsuario);

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

    /// Gestión de vehículos
    public String agregarVehiculo(Usuario usuario, String patente, Marca marca, String modelo, int anio, int kilometraje, EstadoVehiculo estadoVehiculo, BigDecimal precioPorDia) throws AccesoDenegadoEx, SQLException {

        /// REVISAR ESTO
        TipoUsuario tipo = usuario.getTipoUsuario();

        if (!tienePermiso(usuario, TipoUsuario.ADMINISTRADOR)) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }
        ///

        int idGenerada = VehiculoDAO.agregarVehiculoBDD(patente, marca, modelo, anio, kilometraje, estadoVehiculo, precioPorDia);

        if (idGenerada != -1) {
            Vehiculo vehiculo = new Vehiculo(idGenerada, patente, marca, modelo, anio, kilometraje, estadoVehiculo, precioPorDia);
            vehiculos.add(vehiculo);
            return "Se agregó correctamente el vehículo.";
        }
        return "No se pudo agregar el vehículo";
    }

    public void darDeBajaVehiculo(Usuario usuario, int id) throws AccesoDenegadoEx, SQLException {

        // validación de usuario acá

        // VehiculoDAO acá

        Vehiculo vehiculo = vehiculos.buscar(id);
        vehiculo.setEstadoVehiculo(EstadoVehiculo.FUERA_DE_SERVICIO);


    }

    // Gestión de reservas
    public void agregarReserva() throws AccesoDenegadoEx {

    }

    // Gestión de alquileres
    public void agregarAlquiler()  throws AccesoDenegadoEx {

    }

}
