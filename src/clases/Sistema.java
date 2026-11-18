package clases;

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
    public void iniciarSesion() throws UsuarioNoEncontradoEx, ContraseniaIncorrectaEx {

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
