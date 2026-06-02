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
        return switch (this) {
            case NOMBREUSES_VIES -> "Vies ∞";
            case CROISSANCE -> "Croissance";
            case PUANT -> "Puant";
            case COUREUR -> "Coureur";
            case CONTACT_MORTEL -> "Poison";
            case PIQUES_POINTUES -> "Pointes";
            case AUCUN -> "Aucun";
        };
    }

}






