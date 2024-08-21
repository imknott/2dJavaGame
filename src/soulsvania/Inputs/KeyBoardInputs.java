package soulsvania.Inputs;

import soulsvania.main.GamePanel;
import soulsvania.states.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardInputs implements KeyListener {
    private GamePanel gp;

    public KeyBoardInputs(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state) {
            case PLAYING:
                gp.getGame().getPlaying().keyPressed(e);
                break;
            case MENU:
                gp.getGame().getMenu().keyPressed(e);
                break;
            case OPTIONS:
                gp.getGame().getOptionsMenu().keyPressed(e);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.state) {
            case PLAYING:
                gp.getGame().getPlaying().keyReleased(e);
                break;
            case MENU:
                gp.getGame().getMenu().keyReleased(e);
                break;
            case OPTIONS:
                gp.getGame().getOptionsMenu().keyReleased(e);
                break;
        }
    }
}
