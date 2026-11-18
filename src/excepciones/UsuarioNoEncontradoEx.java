package excepciones;

public class UsuarioNoEncontradoEx extends RuntimeException {
    public UsuarioNoEncontradoEx(String message) {
        super(message);
    }
}
