package inscryption;

public enum PowerEnum {
    NOMBREUSES_VIES,
    CROISSANCE,
    PUANT,
    COUREUR,
    CONTACT_MORTEL,
    PIQUES_POINTUES,
    AUCUN;

    @Override
    public String toString()
    {
        switch(this)
        {
            case NOMBREUSES_VIES: return "Nombreuses Vies";
            case CROISSANCE: return "Croissance";
            case PUANT: return "Puant";
            case COUREUR: return "Coureur";
            case CONTACT_MORTEL: return "Contact mortel";
            case PIQUES_POINTUES: return "Piques pointues";
            case AUCUN: return "Aucun";
            default: return "";
        }
    }

}






