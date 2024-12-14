package excepcion;

public class ErrorEnConversionDeDuraciónException extends RuntimeException {
    private String mensaje;

    public ErrorEnConversionDeDuraciónException(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String getMessage(){
        return this.mensaje;
    }
}