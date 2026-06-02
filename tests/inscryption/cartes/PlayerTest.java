package inscryption.cartes;
import inscryption.players.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void InitialStateTest() {
        Player joueur = new Player();

        assertEquals(0, joueur.getDrawSize(), "Au départ, la pioche doit être vide avant l'appel à setGamecards()");
        assertEquals(0, joueur.getHandSize(), "La main doit être vide");
        assertTrue(joueur.isDrawEmpty(), "isDrawEmpty() doit retourner true");
        assertEquals(0, joueur.getBones(), "Le joueur commence avec 0 os");
        assertEquals(0, joueur.getTurnAttack(), "L'attaque du tour commence à 0");
    }

    @Test
    void SetGamecardsTest() {
        Player joueur = new Player();
        joueur.setGamecards();

        assertEquals(15, joueur.getDrawSize(), "La pioche doit contenir 15 cartes après préparation");
        assertFalse(joueur.isDrawEmpty(), "La pioche ne doit plus être vide");
    }

    @Test
    void DrawAndRemoveCardTest() {
        Player joueur = new Player();
        joueur.setGamecards();

        joueur.draw();
        joueur.draw();

        assertEquals(13, joueur.getDrawSize(), "Il doit rester 13 cartes dans la pioche (15 - 2)");
        assertEquals(2, joueur.getHandSize(), "Le joueur doit avoir 2 cartes en main");

        joueur.removeCard(0);
        assertEquals(1, joueur.getHandSize(), "Il doit rester 1 carte en main après le retrait");
    }

    @Test
    void ManageBonesTest() {
        Player joueur = new Player();

        joueur.setBones(3);
        assertEquals(3, joueur.getBones(), "Le setter d'os doit fonctionner");

        joueur.increaseBones();
        assertEquals(4, joueur.getBones(), "L'incrémentation doit ajouter 1 os");
    }

    @Test
    void ManageDrawTest() {
        Player joueur = new Player();

        Loup loupTest = new Loup();
        joueur.addInDraw(loupTest);

        assertEquals(1, joueur.getDrawSize());
        assertEquals(loupTest, joueur.getAnimalAtInDraw(0));

        joueur.draw();

        assertEquals(1, joueur.getHandSize());

        assertNotNull(joueur.getCardNameInHand(0), "Le nom de la carte ne doit pas être null");
        assertNotNull(joueur.getCardFirstPowerInHand(0), "Le premier pouvoir ne doit pas être null");
        assertNotNull(joueur.getCardLastPowerInHand(0), "Le dernier pouvoir ne doit pas être null");

        assertTrue(joueur.getCardAttackInHand(0) >= 0);
        assertTrue(joueur.getCardHealthPointInHand(0) >= 0);
        assertTrue(joueur.getCardBloodInHand(0) >= 0);
        assertTrue(joueur.getCardBonesInHand(0) >= 0);
    }
}