package inscryption.cartes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Cartes_animauxTest {

    @Test
    public void subirDegats() {
        Cartes_animaux chat = new Chat();
        chat.subirDegats(1);
        assertEquals(0, chat.getPointsDeVie());
    }

    @Test
    public void attaquer() {
        Cartes_animaux grizzly = new Grizzly();
        Cartes_animaux loup = new Loup();
        loup.Attaquer(grizzly);
        assertEquals(3, grizzly.getPointsDeVie());
    }
}