package inscryption.cartes;

public abstract class Cartes {
    private String m_nom;
    private int m_points_de_vie;

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
}
