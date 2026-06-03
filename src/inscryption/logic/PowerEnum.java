package inscryption.logic;

public enum PowerEnum {
    MANY_LIVES,
    GROW,
    STINKY,
    RUNNER,
    DEATH_TOUCH,
    SHARP_SPIKES,
    NONE;

    @Override
    public String toString()
    {
        return switch (this) {
            case MANY_LIVES -> "Vies ∞";
            case GROW -> "Croissance";
            case STINKY -> "Puant";
            case RUNNER -> "Coureur";
            case DEATH_TOUCH -> "Poison";
            case SHARP_SPIKES -> "Pointes";
            case NONE -> "Aucun";
        };
    }

}






