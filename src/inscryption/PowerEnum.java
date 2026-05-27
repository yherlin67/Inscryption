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
            case NOMBREUSES_VIES: return "Vies ∞";
            case CROISSANCE: return "Croissance";
            case PUANT: return "Puant";
            case COUREUR: return "Coureur";
            case CONTACT_MORTEL: return "Poison";
            case PIQUES_POINTUES: return "Pointes";
            case AUCUN: return "Aucun";
            default: return "";
        }
    }

}






