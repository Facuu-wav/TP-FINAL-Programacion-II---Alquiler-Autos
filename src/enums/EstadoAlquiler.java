package enums;

public enum EstadoAlquiler {
    ACTIVO,
    FINALIZADO;

    // metodo para pasar de string a enum. si recibe un string vacio o nulo entonces retora null
    public static EstadoAlquiler fromString(String strestadoalquiler){
        if(strestadoalquiler == null || strestadoalquiler.isEmpty() )return null;

        String strestadoalquilertrim = strestadoalquiler.trim();

        for (EstadoAlquiler ea : values()){
            if (ea.name().equalsIgnoreCase(strestadoalquiler.trim())){
                return ea;
            }
        }
        return null;
    }
}
