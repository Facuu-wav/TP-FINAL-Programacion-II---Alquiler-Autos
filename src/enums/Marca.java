package enums;

public enum Marca {
    TOYOTA,
    FORD,
    VOLKSWAGEN,
    CHEVROLET,
    FIAT,
    REANULT,
    PEUGEOT,
    HONDA;

    // metodo para pasar de string a enum. si recibe un string vacio o nullo entonces retora null
    public Marca fromString(String strmarca){
        if(strmarca == null || strmarca.isEmpty() )return null;

        String strmarcatrim = strmarca.trim();

        for (Marca m : values()){
            if (m.name().equalsIgnoreCase(strmarcatrim)){
                return m;
            }
        }
        return null;
    }
}