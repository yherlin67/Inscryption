package inscryption.cards;

import inscryption.logic.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Cat extends AnimalsCards {

    public Cat() {super("Chat",0,1,1,0,false, new ArrayList<>(List.of(PowerEnum.MANY_LIVES)));}

    @Override
    public String toString(){
        return "Chat("+this.getName()+")";
    }

    public Cat(Cat target) { super(target); }
    @Override public Cat clone() { return new Cat(this); }
}
