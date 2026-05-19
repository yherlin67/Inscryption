package inscryption.cartes;

public abstract class Cartes_animaux {

    private String m_nom;
    private int m_attaque;
    private int m_points_de_vie;
    private int m_gouttes_de_sang;
    private int m_os;
    private boolean m_volant;

    public Cartes_animaux(String nom, int att, int pdv, int gds, int os, boolean vol)
    {
        m_nom = nom;
        m_attaque = att;
        m_points_de_vie = pdv;
        m_gouttes_de_sang = gds;
        m_os = os;
        m_volant = vol;
    }

    public String getNom() {
        return m_nom;
    }

    public int getAttaque() {
        return m_attaque;
    }

    public int getPointsDeVie() {
        return m_points_de_vie;
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

    public void subirDegats(int degats) {
        this.m_points_de_vie -= degats;
    }

    public void Attaquer(Cartes_animaux other)
    {
        int degats = this.getAttaque();
        other.subirDegats(degats);
    }

}
