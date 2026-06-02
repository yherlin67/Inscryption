package inscryption.cards;

public class Rocher extends Cards {

    public Rocher() {super("Rocher",5);}

    @Override
    public String toString(){
        return "Rocher("+this.getName()+")";
    }

    public boolean isAnimal() {return false;}
}
