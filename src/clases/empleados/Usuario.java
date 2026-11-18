package clases.empleados;

import enums.TipoUsuario;

public abstract class Usuario {

    // ----------------------------------------------------------|Atributos|----------------------------------------------------------
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String nombreUsuario;
    private String email;
    private String contrasenia;
    private TipoUsuario tipoUsuario;

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

}