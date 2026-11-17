package enums;

public enum EstadoReserva {
    PENDIENTE,
    CONFIRMADA,
    CANCELADA,
    FINALIZADA;

    // metodo para pasar de string a enum. si recibe un string vacio o nulo entonces retora null
    public EstadoReserva fromString(String strestadoreserva){
        if(strestadoreserva == null || strestadoreserva.isEmpty() )return null;

        String estreservatrim = strestadoreserva.trim();

        for (EstadoReserva er : values()){
            if (er.name().equalsIgnoreCase(strestadoreserva.trim())){
                return er;
            }
        }
        return null;
    }
}
