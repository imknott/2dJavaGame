package soulsvania.states;

import soulsvania.main.Game;
import soulsvania.ui.MenuButton;

import java.awt.event.MouseEvent;

public class State {

    protected Game game;


    public State(Game game){
        this.game = game;
    }

    public boolean isIn(MouseEvent e, MenuButton button){
        return button.getBounds().contains(e.getX(), e.getY());
    }

    public Game getGame(){
        return game;
    }
}
