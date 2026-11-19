package clases;

import clases.empleados.Administrador;
import clases.empleados.Empleado;
import clases.empleados.Usuario;
import enums.*;
import excepciones.AccesoDenegadoEx;
import excepciones.ContraseniaIncorrectaEx;
import excepciones.ObjetoNoExisteEx;
import excepciones.VehiculoNoDisponibleEx;
import sql.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class Sistema {

    /// Atributos
    private Usuario usuarioActual;
    private Gestor<Cliente>  clientes;
    private Gestor<Vehiculo> vehiculos;
    private Gestor<Usuario>  usuarios;
    private Gestor<Reserva>  reservas;
    private Gestor<Alquiler> alquileres;

    /// Constructor/es
    public Sistema() {
        this.usuarioActual = null;
        this.clientes   = new Gestor<>();
        this.vehiculos  = new Gestor<>();
        this.usuarios   = new Gestor<>();
        this.reservas   = new Gestor<>();
        this.alquileres = new Gestor<>();
    }

    /// Métodos

    /// Gestión de usuarios del sistema
    // Este método valida que un usuario exista dentro del sistema.
    public Usuario iniciarSesion(String nombreUsuario, String contrasenia) throws SQLException, ObjetoNoExisteEx, ContraseniaIncorrectaEx {

        Usuario usuario = UsuarioDAO.buscarUsuario(nombreUsuario);

        if (!usuarioExiste(usuario)) {
            throw new ObjetoNoExisteEx("El usuario no existe.");
        }

        if (!usuario.getContrasenia().equals(contrasenia)) {
            throw new ContraseniaIncorrectaEx("La contraseña es incorrecta.");
        }

        this.usuarioActual = usuario;
        return usuario;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    private boolean usuarioExiste(Usuario usuario) {
        return usuario != null;
    }

    private boolean esAdministrador(Usuario usuario) {
        return usuario instanceof Administrador;
    }

    private boolean tienePermisos(Usuario usuario, TipoEmpleado tipoEmpleado) {
        return usuario instanceof Empleado empleado && empleado.getTipoEmpleado() == tipoEmpleado;
    }

    public String agregarAdministrador(String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia) throws AccesoDenegadoEx {

        if (!esAdministrador(usuarioActual)) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }

        int idGenerada = UsuarioDAO.agregarAdministradorBDD(nombre, apellido, dni, nombreUsuario, email, contrasenia);

        if (idGenerada != -1) {
            Administrador administrador = new Administrador(idGenerada, nombre, apellido, dni, nombreUsuario, email, contrasenia);
            usuarios.agregar(administrador);
            return "Se agregó correctamente el administrador.";
        }
        return "No se pudo agregar el administrador.";
    }

    public String agregarEmpleado(String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia, TipoEmpleado tipoEmpleado, int legajo, BigDecimal pagoPorHora, int horasTrabajadas) throws AccesoDenegadoEx {

        if (!esAdministrador(usuarioActual)) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }

        int idGenerada = UsuarioDAO.agregarEmpleadoBDD(nombre, apellido, dni, nombreUsuario, email, contrasenia, tipoEmpleado, legajo, pagoPorHora, horasTrabajadas);

        if (idGenerada != -1) {
            Empleado empleado = new Empleado(idGenerada, nombre, apellido, dni, nombreUsuario, email, contrasenia, tipoEmpleado, legajo, pagoPorHora, horasTrabajadas);
            usuarios.agregar(empleado);
            return "Se agregó correctamente el empleado.";
        }
        return "No se pudo agregar el empleado";
    }

    /// Gestión de clientes
    public String agregarCliente() throws AccesoDenegadoEx {

        if (!esAdministrador(usuarioActual)) {
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
    public String agregarVehiculo(String patente, Marca marca, String modelo, int anio, int kilometraje, EstadoVehiculo estadoVehiculo, BigDecimal precioPorDia) throws ObjetoNoExisteEx, AccesoDenegadoEx, SQLException {

        if (!esAdministrador(usuarioActual)) {
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

    public void darDeBajaVehiculo(int id) throws AccesoDenegadoEx, SQLException {

        if (!esAdministrador(usuarioActual)) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }

        VehiculoDAO.actualizarEstadoVehiculoBDD(id, EstadoVehiculo.FUERA_DE_SERVICIO);

        Vehiculo vehiculo = vehiculos.buscar(id);
        vehiculo.setEstadoVehiculo(EstadoVehiculo.FUERA_DE_SERVICIO);
    }

    /// Gestión de reservas
    public String agregarReserva(int idCliente, int idVehiculo, LocalDateTime fechaReserva, LocalDate fechaInicio, LocalDate fechaFin) throws AccesoDenegadoEx, ObjetoNoExisteEx, VehiculoNoDisponibleEx, SQLException {

        if (!esAdministrador(usuarioActual) && !tienePermisos(usuarioActual, TipoEmpleado.EMPLEADO_VENTAS)) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }

        Cliente cliente = clientes.buscar(idCliente);

        if (cliente == null) {
            throw new ObjetoNoExisteEx("El cliente ingresado no existe.");
        }

        Vehiculo vehiculo = vehiculos.buscar(idVehiculo);

        if (vehiculo == null) {
            throw new ObjetoNoExisteEx("El vehículo ingresado no existe.");
        }

        if (vehiculo.getEstadoVehiculo() != EstadoVehiculo.DISPONIBLE) {
            throw new VehiculoNoDisponibleEx("El vehículo no está disponible.");
        }

        if (fechaReserva == null || fechaInicio == null || fechaFin == null || !fechaInicio.isBefore(fechaFin) || fechaReserva.toLocalDate().isAfter(fechaInicio)) {
            throw new IllegalArgumentException("Las fechas de la reserva no son válidas.");
        }

        Usuario usuario = usuarioActual;
        int idUsuario = usuario.obtenerID();

        EstadoReserva estadoReserva = EstadoReserva.CONFIRMADA;

        int idGenerada = ReservaDAO.agregarReservaBDD(idCliente, idVehiculo, idUsuario, fechaReserva, fechaInicio, fechaFin, estadoReserva);

        if (idGenerada == -1) {
            return "No se pudo agregar la reserva.";
        }

        if (!VehiculoDAO.actualizarEstadoVehiculoBDD(idVehiculo, EstadoVehiculo.RESERVADO)) {
            throw new SQLException("No se pudo actualizar el estado del vehículo.");
        }

        Reserva reserva = new Reserva(idGenerada, cliente, vehiculo, usuario, fechaReserva, fechaInicio, fechaFin, estadoReserva);

        reservas.agregar(reserva);
        vehiculo.setEstadoVehiculo(EstadoVehiculo.RESERVADO);

        return "Se agregó correctamente la reserva.";
    }

    /// Gestión de alquileres
    public String agregarAlquiler()  throws AccesoDenegadoEx {

        if (!esAdministrador(usuarioActual) && !tienePermisos(usuarioActual, TipoEmpleado.EMPLEADO_VENTAS)) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }

        int idGenerada = AlquilerDAO.agregarAlquilerBDD();

        if (idGenerada != -1) {
            Alquiler alquiler = new Alquiler();
            alquileres.agregar(alquiler);
            return "Se agregó correctamente el alquiler.";
        }
        return "No se pudo agregar el alquiler";
    }

}
