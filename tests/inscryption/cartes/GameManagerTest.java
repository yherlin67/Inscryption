package inscryption.cartes;

import inscryption.GameManager;
import inscryption.cards.*;
import inscryption.players.Opponent;
import inscryption.players.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    @Test
    void SetGameTest() {
        Player joueur = new Player();
        Opponent adversaire = new Opponent();
        GameManager gm = new GameManager(joueur, adversaire);

        gm.setGame(1);

        assertTrue(gm.isCard(2, 1), "Il devrait y avoir une carte en (2,1) pour la game 1");
        assertEquals("Sapin", gm.getCardName(2, 1), "La carte en (2,1) devrait être un Sapin");

        assertEquals(4, gm.getHandSize(), "Le joueur devrait avoir 4 cartes en main après le début de la partie");
    }

    @Test
    void VictoryTest() {
        Player joueur = new Player();
        Opponent adversaire = new Opponent();
        GameManager gm = new GameManager(joueur, adversaire);

        assertEquals(0, gm.getScore(), "Le score initial doit être de 0");
        assertNull(gm.gameReview(), "La partie ne devrait être ni gagnée ni perdue à 0 de score");

        gm.setScore(5);

        assertEquals(5, gm.getScore());
        assertTrue(gm.gameReview(), "Un score de 5 devrait entraîner la victoire de la partie");

        gm.setScore(-10);
        assertEquals(-5, gm.getScore());
        assertFalse(gm.gameReview(), "Un score de -5 devrait entraîner la défaite de la partie");
    }

    @Test
    void CardPlacementTest() {
        Player joueur = new Player();
        Opponent adversaire = new Opponent();
        GameManager gm = new GameManager(joueur, adversaire);

        Loup loup = new Loup();
        gm.setCard(loup, 2, 0);

        assertTrue(gm.isCard(2, 0), "Une carte devrait être présente sur la case (2,0)");
        assertTrue(gm.isCardAnimal(2, 0), "La carte devrait être reconnue comme un animal");
        assertEquals("Loup", gm.getCardName(2, 0), "La carte devrait être un Loup");

        assertFalse(gm.isCard(2, 1), "La case (2,1) devrait être vide");
    }

    @Test
    void AttackAndDamageTest() {
        Player joueur = new Player();
        Opponent adversaire = new Opponent();
        GameManager gm = new GameManager(joueur, adversaire);

        Loup loupJoueur = new Loup();
        Loup loupAdversaire = new Loup();

        gm.setCard(loupAdversaire, 1, 0);
        gm.setCard(loupJoueur, 2, 0);

        int degats = gm.getCardAttack(2, 0);
        gm.cardTakeDamage(1, 0, degats);

        assertEquals(-1, gm.getCardHealthPoints(1, 0), "Le loup adverse devrait avoir -1 PV restants (2 - 3)");
    }

    @Test
    void EndTurnTest() {
        Player joueur = new Player();
        Opponent adversaire = new Opponent();
        GameManager gm = new GameManager(joueur, adversaire);

        Loup loup1 = new Loup();
        Loup loup2 = new Loup();
        gm.setCard(loup1, 2, 0);
        gm.setCard(loup2, 2, 2);

        joueur.attack();

        assertEquals(6, gm.getScore(), "Les deux loups (3+3) devraient infliger 6 points au score global");
        assertEquals(6, joueur.getTurnAttack(), "Le récapitulatif des dégâts du tour devrait être de 6");
    }
}