package inscryption.cards;

public class Sapin extends Cards {

    public Sapin() {super("Sapin",3);}

    @Override
    public String toString(){
        return "Sapin("+this.getName()+")";
    }

    public boolean isAnimal() {return false;}
}
