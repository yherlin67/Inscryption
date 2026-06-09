package inscryption.cartes;

import inscryption.cards.*;
import inscryption.logic.*;
import inscryption.players.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Nested
class InscryptionGameTests {

    // Test la mise en place d'une partie (plateau et pioches) & piocher une carte
    @Test
    public void testSetGameAndDraw() {
        Player player = new Player();
        Opponent opponent = new Opponent();
        GameManager gameManager = new GameManager(player, opponent); 

        // Vérification de la création de la pioche initiale
        assertFalse(player.isDrawEmpty(), "La pioche ne doit pas être vide à l'initialisation"); 

        // La méthode setGame(2) place automatiquement une carte Rock et fait piocher 4 cartes au joueur
        gameManager.setGame(2); 

        assertEquals(4, player.getHandSize(), "Le joueur doit avoir pioché 4 cartes lors de la mise en place"); 
        assertTrue(gameManager.isCard(2, 0), "Une carte de Rock doit être présente en ligne 2, colonne 0");
    }

    // Test l'attaque d'une carte, l'attaque de toutes les cartes & la mise à jour du score
    @Test
    public void testAttackAndUpdateScore() {
        Player player = new Player();
        Opponent opponent = new Opponent();
        GameManager gameManager = new GameManager(player, opponent); 

        //Création d'un loup pour le test
        AnimalsCard playerCard = new Wolf();

        // On place la carte sur le plateau du joueur (ligne 2, colonne 0)
        gameManager.setCard(playerCard, 2, 0);

        int scoreInitial = gameManager.getScore(); 

        // L'attaque du joueur va déclencher player.attack() et mettre à jour le score global
        gameManager.playerAttack(); 

        // Puisqu'il n'y a pas de défenseur en face (ligne 1), les dégâts sont infligés directement
        assertEquals(scoreInitial + 3, gameManager.getScore(), "Le score du GameManager doit augmenter de 3 suite à l'attaque directe"); 
    }

    // Test les pouvoirs (ex: le pouvoir Puant)
    @Test
    public void testPowers() {
        Player player = new Player(); 
        Card[][] gameboard = new Card[3][4];

        // Carte attaquante avec 2 d'attaque
        AnimalsCard attacker = new Coyote();

        // Carte défenseuse avec le pouvoir STINKY (Puant)
        AnimalsCard stinkyDefender = new Bug();

        gameboard[2][0] = attacker;
        gameboard[1][0] = stinkyDefender;

        // Exécution de l'attaque
        player.attack(gameboard); 

        // Le pouvoir Puant (STINKY) réduit l'attaque adverse de 1 (degats--)
        // 2 dégâts - 1 = 1 dégât infligé. Le défenseur avait 2 PV, il doit lui en rester 1.
        assertEquals(1, stinkyDefender.getHealthPoints(), "Les PV restants du défenseur doivent être de 1 car le pouvoir STINKY a réduit l'attaque de 1");
    }

    // Test le mécanisme de la pierre de sacrifice
    @Test
    public void testTransferPower() {
        Player player = new Player();
        //On gère notre propre pioche pour le test, pour s'assurer que les cartes ont bien des pouvoirs
        AnimalsCard sourceCard = new Moose();
        AnimalsCard aimCard = new Squirrel();

        // On ajoute manuellement les cartes à la pioche du joueur pour simuler les index 0 et 1
        player.addCardToDraw(sourceCard);
        player.addCardToDraw(aimCard);

        int nbPowerAimBefore = aimCard.getPowerSizeAnimal();

        // Simule la logique de la pierre de Sacrifice = transfère d'un pouvoir d'un animal à un autre et suppression de la carte source de la pioche
        PowerEnum transferedPower = sourceCard.getFirstPowerAnimal();

        // On vérifie qu'on ne transfère pas le pouvoir "NONE"
        if (transferedPower != PowerEnum.NONE) {
            aimCard.addPower(transferedPower);
        }
        // Supprime la source
        player.removeAtInDraw(0);

        assertEquals(nbPowerAimBefore + 1, aimCard.getPowerSizeAnimal(), "La carte cible doit posséder un pouvoir supplémentaire");
        assertEquals(PowerEnum.RUNNER, aimCard.getLastPowerAnimal(), "Le dernier pouvoir de la carte cible doit être le pouvoir RUNNER reçu");
    }

    // Test le fait de piocher une carte
    @Test
    public void testDrawCard() {
        Player player = new Player();
        player.setGamecards();

        int initialHandSize = player.getHandSize();
        int initialeDrawSize = player.getDrawSize();

        player.draw();

        assertEquals(initialHandSize + 1, player.getHandSize(), "La main doit avoir augmenté d'une carte");
        assertEquals(initialeDrawSize - 1, player.getDrawSize(), "La pioche doit avoir diminué d'une carte");
    }

    // Test le placement des cartes sur le plateau
    @Test
    public void testCardPlacement() {
        Player player = new Player();
        Opponent opponent = new Opponent();
        GameManager gameManager = new GameManager(player, opponent);

        // On pioche au moins une carte dans la main
        player.draw();
        int handSizeBefore = player.getHandSize();

        // Placement de la première carte de la main (index 0) sur la première colonne du plateau (index 0)
        gameManager.placeCard(0, 0); 

        assertEquals(handSizeBefore - 1, player.getHandSize(), "La carte doit disparaître de la main du joueur une fois placée");
        assertTrue(gameManager.isCard(2, 0), "La carte doit être physiquement placée sur le plateau (ligne 2, colonne 0)"); 
    }

    // Test le fait de gagner ou perdre une partie / le jeu
    @Test
    public void testVictoryAndDefeat() {
        Player player = new Player();
        Opponent opponent = new Opponent();
        GameManager gameManager = new GameManager(player, opponent); 

        gameManager.setScore(0); 
        assertNull(gameManager.gameReview(), "Avec un score de 0, la partie n'est ni gagnée ni perdue (retourne null)"); 

        gameManager.setScore(5); 
        assertTrue(gameManager.gameReview(), "Avec un score de 5, le joueur doit gagner la partie (retourne true)");

        // Le score passe de 5 à -5
        gameManager.setScore(-10);
        assertFalse(gameManager.gameReview(), "Avec un score de -5, le joueur doit perdre la partie (retourne false)"); 
    }

    // Test l'ajout de nouvelles cartes dans la pioche à la fin de la deuxième partie
    @Test
    public void testAddNewCardsInDraw() {
        Player player = new Player(); 
        int drawSizeBefore = player.getDrawSize();

        // Création de la carte à ajouter
        AnimalsCard newCard = new Grizzly();

        player.addInDraw(newCard);

        assertEquals(drawSizeBefore + 1, player.getDrawSize(), "La pioche doit contenir une carte de plus");
        assertEquals(newCard.getName(), player.getAnimalAtInDraw(player.getDrawSize() - 1).getName(), "La carte ajoutée doit se trouver à la toute fin de la pioche");
    }

    // Test l'attaque de toutes les cartes à la fin d'un tour (Plateau complet)
    @Test
    public void testAttackInBoard() {
        Player player = new Player();
        Opponent opponent = new Opponent();
        GameManager gameManager = new GameManager(player, opponent);

        // Création de 4 cartes attaquantes avec des dégâts variés (total attendu : 2 + 1 + 3 + 2 = 8)
        AnimalsCard cardCol0 = new Coyote();
        AnimalsCard cardCol1 = new Bug();
        AnimalsCard cardCol2 = new Wolf();
        AnimalsCard cardCol3 = new Crow();

        // On remplit entièrement la ligne du joueur (Ligne 2, colonnes 0, 1, 2 et 3)
        gameManager.setCard(cardCol0, 2, 0);
        gameManager.setCard(cardCol1, 2, 1);
        gameManager.setCard(cardCol2, 2, 2);
        gameManager.setCard(cardCol3, 2, 3);

        int initialScore = gameManager.getScore();

        // Exécution de l'attaque du joueur
        gameManager.playerAttack();

        // Les 4 cartes font des dégâts directs à l'adversaire (cases en face vides).
        // 2 + 1 + 3 + 2 = 8 dégâts.
        assertEquals(initialScore + 8, gameManager.getScore(), "Les attaques des 4 cartes présentes sur le plateau doivent s'additionner correctement");
    }

    // Test l'attaque d'une carte (Combat direct carte contre carte)
    @Test
    public void testAttackBetweenCards() {

        AnimalsCard playerCard = new Crow();
        AnimalsCard opponentCard = new Ermine();

        ArrayList<Location> impactedLocations = new ArrayList<>();

        // On lance le duel (Ligne 2 attaque Ligne 1 sur la colonne 1)
        playerCard.duel(impactedLocations, playerCard.getAnimalAttack(), 1, 2, 1, opponentCard);

        // On vérifie les PV du défenseur (3 PV de départ - 2 dégâts = 1 PV)
        assertEquals(1, opponentCard.getHealthPoints(), "La carte adverse doit perdre des PV égaux à l'attaque de la carte alliée");

        // On vérifie que l'attaquant n'a pas pris de dégâts (car l'ennemi n'a pas le pouvoir Sharp Spikes)
        assertEquals(3, playerCard.getHealthPoints(), "L'attaquant ne doit pas perdre de PV lors d'un combat standard");
    }
}