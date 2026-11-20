package clases;

import clases.empleados.Administrador;
import clases.empleados.Empleado;
import clases.empleados.Usuario;
import enums.*;
import excepciones.*;
import sql.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;

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

        if (usuario == null) {
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

    private boolean esAdministrador(Usuario usuario) {
        return usuario instanceof Administrador;
    }

    private boolean tienePermisos(Usuario usuario, TipoEmpleado tipoEmpleado) {
        return usuario instanceof Empleado empleado && empleado.getTipoEmpleado() == tipoEmpleado;
    }

    public String agregarAdministrador(String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia) throws AccesoDenegadoEx, SQLException {

        if (!esAdministrador(usuarioActual)) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }

        int idGenerada = UsuarioDAO.agregarAdministradorBDD(nombre, apellido, dni, nombreUsuario, email, contrasenia);

        Administrador administrador = new Administrador(idGenerada, nombre, apellido, dni, nombreUsuario, email, contrasenia);

        usuarios.agregar(administrador);

        return "Se agregó correctamente el administrador.";
    }

    public String agregarEmpleado(String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia, TipoEmpleado tipoEmpleado, int legajo, BigDecimal pagoPorHora, int horasTrabajadas) throws AccesoDenegadoEx, SQLException {

        if (!esAdministrador(usuarioActual)) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }

        int idGenerada = UsuarioDAO.agregarEmpleadoBDD(nombre, apellido, dni, nombreUsuario, email, contrasenia, tipoEmpleado, legajo, pagoPorHora, horasTrabajadas);

        Empleado empleado = new Empleado(idGenerada, nombre, apellido, dni, nombreUsuario, email, contrasenia, tipoEmpleado, legajo, pagoPorHora, horasTrabajadas);

        usuarios.agregar(empleado);

        return "Se agregó correctamente el empleado.";
    }

    /// Gestión de clientes
    public String agregarCliente(String nombre, String apellido, String dni, String telefono, String licenciaConducir, LocalDate vencimientoLicencia) throws AccesoDenegadoEx, SQLException {

        if (!esAdministrador(usuarioActual)) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }

        int idGenerada = ClienteDAO.agregarClienteBDD( nombre, apellido, dni, telefono, licenciaConducir, vencimientoLicencia);

        if (idGenerada != -1) {
            Cliente cliente = new Cliente(idGenerada, nombre, apellido, dni, telefono, licenciaConducir, vencimientoLicencia);
            clientes.agregar(cliente);
            return "Se agregó correctamente el cliente.";
        }

        return "No se pudo agregar el cliente.";
    }

    public StringBuilder listarClientesActivos() {

        StringBuilder sb = new StringBuilder();

        for (Cliente cliente : clientes.obtenerTodos()) {

            if (cliente.isActivo()) {
                sb.append(cliente).append("\n");
            }
        }

        return sb;
    }

    public void cargarClientesBDD() throws SQLException, NoExistenElementosCargadosEx {

        LinkedHashSet<Cliente> clientesBDD = ClienteDAO.listarClientesBDD();

        for (Cliente cliente : clientesBDD) {
            clientes.agregar(cliente);
        }
    }


    /// Gestión de vehículos
    public String agregarVehiculo(String patente, Marca marca, String modelo, int anio, int kilometraje, BigDecimal precioPorDia) throws AccesoDenegadoEx, SQLException {

        if (!esAdministrador(usuarioActual)) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }

        if (patente == null || patente.isBlank() || marca == null || modelo == null || modelo.isBlank() || anio <= 0 || kilometraje < 0 || precioPorDia == null || precioPorDia.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Los datos del vehículo no son válidos.");
        }

        if (VehiculoDAO.existePatenteBDD(patente)) {
            throw new IllegalArgumentException("La patente ya está registrada");
        }

        EstadoVehiculo estadoVehiculo = EstadoVehiculo.DISPONIBLE;
        int idGenerada = VehiculoDAO.agregarVehiculoBDD(patente, marca, modelo, anio, kilometraje, estadoVehiculo, precioPorDia);

        if (idGenerada == -1) {
            return "No se pudo agregar el vehículo.";
        }

        Vehiculo vehiculo = new Vehiculo(idGenerada, patente, marca, modelo, anio, kilometraje, estadoVehiculo, precioPorDia);

        vehiculos.agregar(vehiculo);

        return "Se agregó correctamente el vehículo.";
    }

    public void darDeBajaVehiculo(int id) throws AccesoDenegadoEx, ObjetoNoExisteEx, VehiculoNoDisponibleEx, SQLException {

        if (!esAdministrador(usuarioActual)) {
            throw new AccesoDenegadoEx("Acceso denegado.");
        }

        Vehiculo vehiculo = vehiculos.buscar(id);

        if (vehiculo == null) {
            throw new ObjetoNoExisteEx("El vehículo no existe.");
        }

        if (vehiculo.getEstadoVehiculo() == EstadoVehiculo.RESERVADO || vehiculo.getEstadoVehiculo() == EstadoVehiculo.ALQUILADO) {
            throw new VehiculoNoDisponibleEx("No se puede dar de baja un vehículo reservado o alquilado.");
        }

        if (vehiculo.getEstadoVehiculo() == EstadoVehiculo.FUERA_DE_SERVICIO) {
            throw new IllegalArgumentException("El vehículo ya se encuentra fuera de servicio.");
        }

        if (!VehiculoDAO.actualizarEstadoVehiculoBDD(id, EstadoVehiculo.FUERA_DE_SERVICIO)) {
            throw new SQLException("No se pudo dar de baja el vehículo.");
        }

        vehiculo.setEstadoVehiculo(EstadoVehiculo.FUERA_DE_SERVICIO);
    }

    public void cargarVehiculosBDD() throws SQLException, NoExistenElementosCargadosEx {

        LinkedHashSet<Vehiculo> vehiculosBDD = VehiculoDAO.listarVehiculosBDD();

        for (Vehiculo vehiculo : vehiculosBDD) {
            vehiculos.agregar(vehiculo);
        }
    }

    public StringBuilder listarVehiculosActivos() {

        StringBuilder sb = new StringBuilder();

        for (Vehiculo vehiculo : vehiculos.obtenerTodos()) {

            if (vehiculo.getEstadoVehiculo() != EstadoVehiculo.FUERA_DE_SERVICIO) {
                sb.append(vehiculo).append("\n");
            }
        }

        return sb;
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
    /*
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
     */

}
