package clases;

public abstract class Usuario {

    /// Atributos
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String nombreUsuario;
    private String email;
    private String contrasenia;

    /// Constructor/es
    public Usuario(int id, String nombre, String apellido, String dni, String nombreUsuario, String email, String contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasenia = contrasenia;
    }

    //getters
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


    /// Métodos

}