package excepciones;

public class ContraseniaIncorrectaEx extends RuntimeException {
    public ContraseniaIncorrectaEx(String message) {
        super(message);
    }
}
