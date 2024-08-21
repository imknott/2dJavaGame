package soulsvania.states;

import soulsvania.main.Game;
import soulsvania.ui.MenuButton;
import soulsvania.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static soulsvania.states.GameState.*;

public class Menu extends State implements GameStateMethods{

    private MenuButton[] menuButtons = new MenuButton[3];
    private BufferedImage backgroundImg,backgroundImg1,backgroundImg2;
    private int menuX, menuY, menuWidth, menuHeight;
    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
    }

    private void loadBackground() {
        backgroundImg = LoadSave.setup("randomimages/menuHolder");
        backgroundImg1 = LoadSave.setup("randomimages/Background_0");
        backgroundImg2 = LoadSave.setup("randomimages/Background_1");
        menuWidth = (backgroundImg.getWidth() * 6);
        menuHeight = (backgroundImg.getHeight() * 5);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (137 * Game.SCALE);

    }

    private void loadButtons() {
        menuButtons[0] = new MenuButton(Game.GAME_WIDTH/2, (int)(165 * Game.SCALE),0 , PLAYING);
        menuButtons[2] = new MenuButton(Game.GAME_WIDTH/2, (int)(227 * Game.SCALE),2 , OPTIONS);
        menuButtons[1] = new MenuButton(Game.GAME_WIDTH/2, (int)(285 * Game.SCALE),1 , QUIT);

    }

    @Override
    public void update() {
        for(MenuButton button : menuButtons) {
            button.update();
        }

    }

    @Override
    public void draw(Graphics g) {
        //Title Name
        g.setColor(new Color(0,0,0));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.drawImage(backgroundImg1, 0, 0, Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
        g.drawImage(backgroundImg2, 0, 0, Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
        g.setFont(g.getFont().deriveFont(Font.ITALIC, 56F));
        g.setColor(Color.WHITE);
        String text = "SoulsVania";
        g.drawString(text,menuX,menuY -20);

        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
        for(MenuButton button : menuButtons) {
            button.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //check if mouse is pressing inside a button
        for(MenuButton button : menuButtons) {
            if(isIn(e,button)){
                button.setMousePressed(true);
                break;
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //check if mouse is being released within a button
        for(MenuButton button : menuButtons) {
            if(isIn(e,button)){
                if(button.isMousePressed()){
                    button.applyGameState();
                    break;
                }

            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for(MenuButton button : menuButtons) {
            button.resetBools();
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton button : menuButtons) {
            button.setMouseOver(false);
        }
        for(MenuButton button : menuButtons) {
            if(isIn(e,button)){
                button.setMouseOver(true);
                break;
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            state = PLAYING;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
