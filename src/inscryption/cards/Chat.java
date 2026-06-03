package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AnimalsCards {

    public Chat() {super("Chat",0,1,1,0,false, new ArrayList<>(List.of(PowerEnum.NOMBREUSES_VIES)));}

    @Override
    public String toString(){
        return "Chat("+this.getName()+")";
    }

    public Chat(Chat target) { super(target); }
    @Override public Chat clone() { return new Chat(this); }
}
