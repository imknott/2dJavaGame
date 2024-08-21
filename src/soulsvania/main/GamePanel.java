package soulsvania.main;

import soulsvania.Inputs.KeyBoardInputs;
import soulsvania.Inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static soulsvania.main.Game.GAME_HEIGHT;
import static soulsvania.main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

    private MouseInputs mi;
    private Game game;


    public GamePanel(Game game) {
        mi = new MouseInputs(this);
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyBoardInputs(this));
        addMouseListener(mi);
        addMouseMotionListener(mi);
    }



    private void setPanelSize(){
        //These are ensuring the panel size can fit the specified tile size I am looking for.
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }


    public void updateGame() {

    }

    //gets called when we press the play button. Can only draw with a graphics soulsvania.object.
    public void paintComponent(Graphics g){
        //calling the jpanel super class or its own paintComponent and does what it needs to do in that class
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame(){
        return game;
    }



}
