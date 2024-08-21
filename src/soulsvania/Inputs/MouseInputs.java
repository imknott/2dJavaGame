package soulsvania.Inputs;

import soulsvania.main.GamePanel;
import soulsvania.states.GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private GamePanel gp;

    public MouseInputs(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameState.state) {
            case PLAYING:
                gp.getGame().getPlaying().mouseDragged(e);
                break;
            case OPTIONS:
                gp.getGame().getOptionsMenu().mouseDragged(e);
                break;
            default:
                break;

        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                gp.getGame().getMenu().mouseMoved(e);
                break;
            case PLAYING:
                gp.getGame().getPlaying().mouseMoved(e);
                break;
            case OPTIONS:
                gp.getGame().getOptionsMenu().mouseMoved(e);
            default:
                break;

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state) {
            case PLAYING:
                gp.getGame().getPlaying().mouseClicked(e);
                break;
            default:
                break;

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                gp.getGame().getMenu().mousePressed(e);
                break;
            case PLAYING:
                gp.getGame().getPlaying().mousePressed(e);
                break;
            case OPTIONS:
                gp.getGame().getOptionsMenu().mousePressed(e);
            default:
                break;

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                gp.getGame().getMenu().mouseReleased(e);
                break;
            case PLAYING:
                gp.getGame().getPlaying().mouseReleased(e);
                break;
                case OPTIONS:
                    gp.getGame().getOptionsMenu().mouseReleased(e);
            default:
                break;

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
