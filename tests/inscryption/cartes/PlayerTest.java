package inscryption.cartes;
import inscryption.players.Player;
import inscryption.cards.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void InitialStateTest() {
        Player player = new Player();

        assertEquals(0, player.getDrawSize(), "Au départ, la pioche doit être vide avant l'appel à setGamecards()");
        assertEquals(0, player.getHandSize(), "La main doit être vide");
        assertTrue(player.isDrawEmpty(), "isDrawEmpty() doit retourner true");
        assertEquals(0, player.getBones(), "Le player commence avec 0 os");
        assertEquals(0, player.getTurnAttack(), "L'attaque du tour commence à 0");
    }

    @Test
    void SetGamecardsTest() {
        Player player = new Player();
        player.setGamecards();

        assertEquals(15, player.getDrawSize(), "La pioche doit contenir 15 cartes après préparation");
        assertFalse(player.isDrawEmpty(), "La pioche ne doit plus être vide");
    }

    @Test
    void DrawAndRemoveCardTest() {
        Player player = new Player();
        player.setGamecards();

        player.draw();
        player.draw();

        assertEquals(13, player.getDrawSize(), "Il doit rester 13 cartes dans la pioche (15 - 2)");
        assertEquals(2, player.getHandSize(), "Le player doit avoir 2 cartes en main");

        player.removeCard(0);
        assertEquals(1, player.getHandSize(), "Il doit rester 1 carte en main après le retrait");
    }

    @Test
    void ManageBonesTest() {
        Player player = new Player();

        player.setBones(3);
        assertEquals(3, player.getBones(), "Le setter d'os doit fonctionner");

        player.increaseBones();
        assertEquals(4, player.getBones(), "L'incrémentation doit ajouter 1 os");
    }

    @Test
    void ManageDrawTest() {
        Player player = new Player();

        Wolf loupTest = new Wolf();
        player.addInDraw(loupTest);

        assertEquals(1, player.getDrawSize());
        assertEquals(loupTest, player.getAnimalAtInDraw(0));

        player.draw();

        assertEquals(1, player.getHandSize());

        assertNotNull(player.getCardNameInHand(0), "Le nom de la carte ne doit pas être null");
        assertNotNull(player.getCardFirstPowerInHand(0), "Le premier pouvoir ne doit pas être null");
        assertNotNull(player.getCardLastPowerInHand(0), "Le dernier pouvoir ne doit pas être null");

        assertTrue(player.getCardAttackInHand(0) >= 0);
        assertTrue(player.getCardHealthPointInHand(0) >= 0);
        assertTrue(player.getCardBloodInHand(0) >= 0);
        assertTrue(player.getCardBonesInHand(0) >= 0);
    }
}