package inscryption.cartes;

public class Rocher extends Cartes{

    public Rocher() {super("Rocher",5);}

    @Override
    public String toString(){
        return "Rocher("+this.getName()+")";
    }
}
