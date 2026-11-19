package clases.empleados;

import interfaces.IObtenerID;

import java.util.Objects;

public abstract class Usuario implements IObtenerID {

    // ----------------------------------------------------------|Atributos|----------------------------------------------------------
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String nombreUsuario;
    private String email;
    private String contrasenia;

    // ----------------------------------------------------------|Constructor/es|----------------------------------------------------------

    public Usuario(int id, String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasenia = contrasenia;
    }

    //----------------------------------------------------------|Getters|----------------------------------------------------------
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    //----------------------------------------------------------|Setters|----------------------------------------------------------

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    //----------------------------------------------------------|Métodos|----------------------------------------------------------

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", email='" + email + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Usuario usuario)) return false;
        return id == usuario.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public int obtenerID() {
        return getId();
    }
}