package clases;

import clases.empleados.Administrador;
import clases.empleados.Empleado;
import clases.empleados.Usuario;
import enums.EstadoVehiculo;
import enums.Marca;
import enums.TipoEmpleado;
import enums.TipoUsuario;
import excepciones.AccesoDenegadoEx;
import excepciones.ContraseniaIncorrectaEx;
import excepciones.UsuarioNoEncontradoEx;
import sql.*;

import java.math.BigDecimal;
import java.sql.SQLException;


public class Sistema {

    /// Atributos
    private Gestor<Cliente>  clientes;
    private Gestor<Vehiculo> vehiculos;
    private Gestor<Usuario>  usuarios;
    private Gestor<Reserva>  reservas;
    private Gestor<Alquiler> alquileres;

    /// Constructor/es
    public Sistema() {
        this.clientes   = new Gestor<>();
        this.vehiculos  = new Gestor<>();
        this.usuarios   = new Gestor<>();
        this.reservas   = new Gestor<>();
        this.alquileres = new Gestor<>();
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

    // REVISAR
    public String agregarUsuarioAdministrador() throws AccesoDenegadoEx {

        // validaciones y excepciones acá

        int idGenerada = UsuarioDAO.agregarUsuarioAdministradorBDD();

        if (idGenerada != -1) {
            Administrador administrador = new Administrador();
            usuarios.agregar(administrador);
            return "Se agregó correctamente el administrador.";
        }
        return "No se pudo agregar el administrador.";
    }

    public String agregarEmpleado(Usuario usuario) throws AccesoDenegadoEx {

        if (!esAdministrador(usuario)) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }

        int idGenerada = UsuarioDAO.agregarEmpleado();

        if (idGenerada != -1) {
            Empleado empleado = new Empleado();
            usuarios.agregar(empleado);
            return "Se agregó correctamente el empleado.";
        }
        return "No se pudo agregar el empleado";
    }

    public void mostrarPermisos() {

    }

    /// corregir
    /*
    private boolean tienePermiso(Usuario usuario, TipoEmpleado... tipos) {
        for (TipoEmpleado tipo : tipos) {
            if (usuario.getTipoUsuario() == tipo) {
                return true;
            }
        }
        return false;
    }
    */

    private boolean esAdministrador(Usuario usuario) {
        return usuario instanceof Administrador;
    }

    private boolean puedeGestionar(Usuario usuario) {
        return usuario instanceof Empleado empleado && empleado.getTipoEmpleado() == TipoEmpleado.EMPLEADO_VENTAS;
    }

    /// Gestión de clientes
    public String agregarCliente(Usuario usuario) throws AccesoDenegadoEx {

        if (!esAdministrador(usuario)) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }

        int idGenerada = ClienteDAO.agregarClienteBDD();

        if (idGenerada != -1) {
            Cliente cliente = new Cliente();
            clientes.agregar(cliente);
            return "Se agregó correctamente el cliente.";
        }
        return "No se pudo agregar el cliente.";
    }

    /// Gestión de vehículos
    public String agregarVehiculo(Usuario usuario, String patente, Marca marca, String modelo, int anio, int kilometraje, EstadoVehiculo estadoVehiculo, BigDecimal precioPorDia) throws AccesoDenegadoEx, SQLException {

        if (!esAdministrador(usuario)) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }

        int idGenerada = VehiculoDAO.agregarVehiculoBDD(patente, marca, modelo, anio, kilometraje, estadoVehiculo, precioPorDia);

        if (idGenerada != -1) {
            Vehiculo vehiculo = new Vehiculo(idGenerada, patente, marca, modelo, anio, kilometraje, estadoVehiculo, precioPorDia);
            vehiculos.agregar(vehiculo);
            return "Se agregó correctamente el vehículo.";
        }
        return "No se pudo agregar el vehículo.";
    }

    public void darDeBajaVehiculo(Usuario usuario, int id) throws AccesoDenegadoEx, SQLException {

        // validación de usuario acá

        // VehiculoDAO acá

        Vehiculo vehiculo = vehiculos.buscar(id);
        vehiculo.setEstadoVehiculo(EstadoVehiculo.FUERA_DE_SERVICIO);


    }

    /// Gestión de reservas
    public String agregarReserva(Usuario usuario) throws AccesoDenegadoEx {

        if (!esAdministrador(usuario) && !(puedeGestionar(usuario))) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }

        int idGenerada = ReservaDAO.agregarReservaBDD();

        if (idGenerada != -1) {
            Reserva reserva = new Reserva();
            reservas.agregar(reserva);
            return "Se agregó correctamente la reserva.";
        }
        return "No se pudo agregar la reserva";
    }

    /// Gestión de alquileres
    public String agregarAlquiler()  throws AccesoDenegadoEx {

        // validaciones y excepciones acá

        int idGenerada = AlquilerDAO.agregarAlquilerBDD();

        if (idGenerada != -1) {
            Alquiler alquiler = new Alquiler();
            alquileres.agregar(alquiler);
            return "Se agregó correctamente el alquiler.";
        }
        return "No se pudo agregar el alquiler";
    }

}
