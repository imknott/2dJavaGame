package soulsvania.main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow extends JFrame {

    public GameWindow(GamePanel gp){
        //create a jframe soulsvania.object
        JFrame jframe = new JFrame();
        //set the size of the window for the game.
        //exits the program when we close the window with the x button on the window
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gp);

        jframe.setResizable(false);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        //make the window visible and should be at the bottom of the code.
        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener(){

            @Override
            public void windowGainedFocus(WindowEvent e) {
               gp.getGame().windowFocusLost();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }
}
