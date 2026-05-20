package inscryption.cartes;

public abstract class Cartes {
    private String m_nom;
    private int m_points_de_vie;
    private Cartes_animaux m_animaux;

    public Cartes(String nom, int pdv)
    {
        m_nom = nom;
        m_points_de_vie = pdv;
    }

    public String getNom() {
        return m_nom;
    }

    public int getPointsDeVie() {
        return m_points_de_vie;
    }

    public void subirDegats(int degats) {
        this.m_points_de_vie -= degats;
    }

    public void setAnimaux(Cartes_animaux animaux) {
        this.m_animaux = animaux;
    }

    public Cartes_animaux getAnimaux() {
        return m_animaux;
    }
}