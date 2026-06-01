package inscryption.players;

import inscryption.GameManager;
import inscryption.PowerEnum;
import inscryption.cartes.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Opponent {
    private ArrayList<Cartes>[] m_actions;
    private GameManager m_datas;
    private int m_turnAttack;

    public Opponent()
    {
        m_actions = new ArrayList[]{
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        };

        m_turnAttack = 0;
    }

    public void setGameManager(GameManager gameManager) {
        this.m_datas = gameManager;
    }

    public void setFirstMatch()
    {
         m_actions[0].addAll(Arrays.asList(new Louveteau(), new Moineau(), null, null));
         m_actions[1].addAll(Arrays.asList(null, null, new Moineau(), null));
         m_actions[2].addAll(Arrays.asList(new Punaise(), null, null, new Punaise()));
         m_actions[3].addAll(Arrays.asList(null, null, new Louveteau(), null));
         m_datas.setCard(m_actions[0].getFirst(), 0, 0);
         m_datas.setCard(m_actions[1].getFirst(), 0, 1);
         m_datas.setCard(m_actions[2].getFirst(), 0, 2);
         m_datas.setCard(m_actions[3].getFirst(), 0, 3);
         m_actions[0].removeFirst();
         m_actions[1].removeFirst();
         m_actions[2].removeFirst();
         m_actions[3].removeFirst();
    }

    public void setSecondMatch()
    {
        m_actions[0].addAll(Arrays.asList(new Loup(), null, new Corbeau(), null));
        m_actions[1].addAll(Arrays.asList(null, new Hermine(), null, new Hermine()));
        m_actions[2].addAll(Arrays.asList(new Coyote(), null, new Loup(), null));
        m_actions[3].addAll(Arrays.asList(null, new Corbeau(), null, new Coyote()));
        m_datas.setCard(m_actions[0].getFirst(), 0, 0);
        m_datas.setCard(m_actions[1].getFirst(), 0, 1);
        m_datas.setCard(m_actions[2].getFirst(), 0, 2);
        m_datas.setCard(m_actions[3].getFirst(), 0, 3);
        m_actions[0].removeFirst();
        m_actions[1].removeFirst();
        m_actions[2].removeFirst();
        m_actions[3].removeFirst();
    }

    public void setThirdMatch()
    {
        m_actions[0].addAll(Arrays.asList(new Grizzly(), null, new Loup(), new Corbeau()));
        m_actions[1].addAll(Arrays.asList(new Loup(), new Grizzly(), null, null));
        m_actions[2].addAll(Arrays.asList(null, new Corbeau(), new Grizzly(), new Loup()));
        m_actions[3].addAll(Arrays.asList(new Corbeau(), null, new Loup(), new Grizzly()));
        m_datas.setCard(m_actions[0].getFirst(), 0, 0);
        m_datas.setCard(m_actions[1].getFirst(), 0, 1);
        m_datas.setCard(m_actions[2].getFirst(), 0, 2);
        m_datas.setCard(m_actions[3].getFirst(), 0, 3);
        m_actions[0].removeFirst();
        m_actions[1].removeFirst();
        m_actions[2].removeFirst();
        m_actions[3].removeFirst();
    }

    public void play()
    {
        for(int i=0; i<4; i++)
        {
            if(!m_datas.isCard(1,i))
            {
                m_datas.moveCardToRow1(i);
                m_datas.setCard(null, 0, i);
            }
            if (!m_datas.isCard(0,i) && !m_actions[i].isEmpty())
            {
                m_datas.setCard(m_actions[i].getFirst(), 0, i);
                m_actions[i].removeFirst();
            }
        }
    }

    public int getTurnAttack(){
        return m_turnAttack;
    }

    public void attack()
    {
        m_turnAttack = 0;
        for(int i=0; i<4; i++)
        {
            if(m_datas.isCard(1,i)) {
                if (!m_datas.isCard(2, i)) {
                    m_datas.setScore(-m_datas.getAnimalAttack(1, i));
                    m_turnAttack += m_datas.getAnimalAttack(1, i);
                } else {
                    int degats = m_datas.getAnimalAttack(1, i);
                    if (m_datas.isCard(2, i)) {
                        if (m_datas.getCardPowerFirst(2, i) == PowerEnum.PUANT || m_datas.getCardPowerLast(2, i) == PowerEnum.PUANT) {
                            m_turnAttack--;
                            degats--;
                        }
                    }
                    if (m_datas.isCard(1, i) && m_datas.getCardFly(1, i)) {
                        m_datas.setScore(degats);
                        m_turnAttack += m_datas.getAnimalAttack(1, i);
                    } else if (m_datas.isCard(1, i) && !m_datas.getCardFly(1, i)) {
                        m_datas.cardTakeDamage(2, i, degats);

                        if (m_datas.getCardPowerFirst(1, i) == PowerEnum.CONTACT_MORTEL || m_datas.getCardPowerLast(1, i) == PowerEnum.CONTACT_MORTEL) {
                            m_datas.cardTakeDamage(2, i, 999);
                        }

                        if (m_datas.isCard(2, i) && m_datas.isCardAnimal(2, i)) {
                            if (m_datas.getCardPowerFirst(2, i) == PowerEnum.PIQUES_POINTUES || m_datas.getCardPowerLast(2, i) == PowerEnum.PIQUES_POINTUES) {
                                m_datas.cardTakeDamage(1, i, 1);

                                if (m_datas.getCardHealthPoints(1, i) <= 0) {
                                    m_datas.setCard(null, 1, i);
                                }
                            }
                        }

                        if (m_datas.isCard(2, i) && m_datas.getCardHealthPoints(2, i) <= 0) {
                            m_datas.setCard(null, 2, i);
                        }
                    }

                    if (m_datas.isCard(1, i) && m_datas.isCardAnimal(1, i) && (m_datas.getCardPowerFirst(1, i) == PowerEnum.CROISSANCE || m_datas.getCardPowerLast(1, i) == PowerEnum.CROISSANCE)) {
                        m_datas.setCard(new Loup(), 1, i);
                    }

                    ArrayList<Integer> indicesBloques = new ArrayList<>();

                    for (int j = 0; j < 4; j++) {
                        if (indicesBloques.contains(j)) {
                            continue;
                        }

                        if (m_datas.isCard(1, j) && m_datas.isCardAnimal(1, j)) {
                            if (m_datas.getCardPowerFirst(1, j) == PowerEnum.COUREUR || m_datas.getCardPowerLast(1, j) == PowerEnum.COUREUR) {
                                if (j < 3 && !m_datas.isCard(1, j + 1)) {
                                    m_datas.moveCard(1, j, j + 1);
                                    indicesBloques.add(j + 1);
                                } else if (j > 0 && !m_datas.isCard(1, j - 1)) {
                                    m_datas.moveCard(1, j, j - 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}