package inscryption.players;

import inscryption.GameManager;
import inscryption.PowerEnum;
import inscryption.cards.*;

import java.util.ArrayList;

public class Opponent {
    private final ArrayList<Cards>[] m_actions;
    private int m_turnAttack;

    public Opponent()
    {
        m_actions = new ArrayList[]{
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        };

        m_turnAttack = 0;
    }

    public void setMatch(ArrayList<Cards> actions0, ArrayList<Cards> actions1, ArrayList<Cards> actions2)
    {
        m_actions[0] = actions0;
        m_actions[1] = actions1;
        m_actions[2] = actions2;
    }

    public void play(GameManager gameManager)
    {
        for(int i=0; i<4; i++)
        {
            if(!gameManager.isCard(1,i))
            {
                gameManager.moveCardToRow1(i);
                gameManager.setCard(null, 0, i);
            }
            if (!gameManager.isCard(0,i) && !m_actions[i].isEmpty())
            {
                gameManager.setCard(m_actions[i].getFirst(), 0, i);
                m_actions[i].removeFirst();
            }
        }
    }

    public int getTurnAttack(){
        return m_turnAttack;
    }

    public void attack(GameManager gameManager)
    {
        m_turnAttack = 0;
        for(int i=0; i<4; i++)
        {
            if(gameManager.isCard(1,i)) {
                if (!gameManager.isCard(2, i)) {
                    gameManager.setScore(-gameManager.getAnimalAttack(1, i));
                    m_turnAttack += gameManager.getAnimalAttack(1, i);
                } else {
                    int degats = gameManager.getAnimalAttack(1, i);
                    if (gameManager.isCard(2, i)) {
                        if (gameManager.getCardPowerFirst(2, i) == PowerEnum.PUANT || gameManager.getCardPowerLast(2, i) == PowerEnum.PUANT) {
                            m_turnAttack--;
                            degats--;
                        }
                    }
                    if (gameManager.isCard(1, i) && gameManager.getCardFly(1, i)) {
                        gameManager.setScore(degats);
                        m_turnAttack += gameManager.getAnimalAttack(1, i);
                    } else if (gameManager.isCard(1, i) && !gameManager.getCardFly(1, i)) {
                        gameManager.cardTakeDamage(2, i, degats);

                        if (gameManager.getCardPowerFirst(1, i) == PowerEnum.CONTACT_MORTEL || gameManager.getCardPowerLast(1, i) == PowerEnum.CONTACT_MORTEL) {
                            gameManager.cardTakeDamage(2, i, 999);
                        }

                        if (gameManager.isCard(2, i) && gameManager.isCardAnimal(2, i)) {
                            if (gameManager.getCardPowerFirst(2, i) == PowerEnum.PIQUES_POINTUES || gameManager.getCardPowerLast(2, i) == PowerEnum.PIQUES_POINTUES) {
                                gameManager.cardTakeDamage(1, i, 1);

                                if (gameManager.getCardHealthPoints(1, i) <= 0) {
                                    gameManager.setCard(null, 1, i);
                                }
                            }
                        }

                        if (gameManager.isCard(2, i) && gameManager.getCardHealthPoints(2, i) <= 0) {
                            gameManager.setCard(null, 2, i);
                        }
                    }

                    if (gameManager.isCard(1, i) && gameManager.isCardAnimal(1, i) && (gameManager.getCardPowerFirst(1, i) == PowerEnum.CROISSANCE || gameManager.getCardPowerLast(1, i) == PowerEnum.CROISSANCE)) {
                        gameManager.setCard(new Loup(), 1, i);
                    }

                    ArrayList<Integer> indicesBloques = new ArrayList<>();

                    for (int j = 0; j < 4; j++) {
                        if (indicesBloques.contains(j)) {
                            continue;
                        }

                        if (gameManager.isCard(1, j) && gameManager.isCardAnimal(1, j)) {
                            if (gameManager.getCardPowerFirst(1, j) == PowerEnum.COUREUR || gameManager.getCardPowerLast(1, j) == PowerEnum.COUREUR) {
                                if (j < 3 && !gameManager.isCard(1, j + 1)) {
                                    gameManager.moveCard(1, j, j + 1);
                                    indicesBloques.add(j + 1);
                                } else if (j > 0 && !gameManager.isCard(1, j - 1)) {
                                    gameManager.moveCard(1, j, j - 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString(){
        return "Opponent";
    }
}