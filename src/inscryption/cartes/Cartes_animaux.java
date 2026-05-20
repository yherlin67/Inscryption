package inscryption.cartes;

public abstract class Cartes_animaux extends Cartes{

    private int m_attaque;

    private int m_gouttes_de_sang;
    private int m_os;
    private boolean m_volant;

    public Cartes_animaux(String nom, int att, int pdv, int gds, int os, boolean vol)
    {
        super(nom,pdv);
        this.setAnimaux(this);
        m_attaque = att;
        m_gouttes_de_sang = gds;
        m_os = os;
        m_volant = vol;
    }

    public int getAttaque() {
        return m_attaque;
    }

    public int getGouttesDeSang() {
        return m_gouttes_de_sang;
    }

    public int getOs() {
        return m_os;
    }

    public boolean isVolant() {
        return m_volant;
    }

    public void Attaquer(Cartes_animaux other)
    {
        int degats = this.getAttaque();
        other.subirDegats(degats);
    }
}
