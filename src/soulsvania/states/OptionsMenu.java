package soulsvania.states;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import soulsvania.main.Game;
import soulsvania.ui.MusicSfxOptions;
import soulsvania.ui.PauseButton;
import soulsvania.ui.functionButtons;
import soulsvania.utils.LoadSave;
import static soulsvania.utils.Constants.UI.URMButtons.*;

public class OptionsMenu extends State implements GameStateMethods{

        private MusicSfxOptions musicsfxOptions;
        private BufferedImage backgroundImg, optionsBackgroundImg, backgroundImg1;
        private int bgX, bgY, bgW, bgH;
        private functionButtons menuB;



    public OptionsMenu(Game game) {
        super(game);
        loadImgs();
        loadButton();
        musicsfxOptions = game.getAudioOptions();
    }

    private void loadButton() {
            int menuX = (int) (387 * Game.SCALE);
            int menuY = (int) (325 * Game.SCALE);

            menuB = new functionButtons(menuX, menuY, URM_SIZE, URM_SIZE, 2);
        }

        private void loadImgs() {
            backgroundImg = LoadSave.setup("randomimages/Background_0");
            backgroundImg1 = LoadSave.setup("randomimages/Background_1");
            optionsBackgroundImg = LoadSave.setup("randomimages/options_background");


            bgW = (int) (optionsBackgroundImg.getWidth() * Game.SCALE);
            bgH = (int) (optionsBackgroundImg.getHeight() * Game.SCALE);
            bgX = Game.GAME_WIDTH / 2 - bgW / 2;
            bgY = (int) (33 * Game.SCALE);
        }

        @Override
        public void update() {
            menuB.update();
            musicsfxOptions.update();

        }

        @Override
        public void draw(Graphics g) {
            g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
            g.drawImage(optionsBackgroundImg, bgX, bgY, bgW, bgH, null);

            menuB.draw(g);
            musicsfxOptions.draw(g);

        }

        public void mouseDragged(MouseEvent e) {
            musicsfxOptions.mouseDragged(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (isIn(e, menuB)) {
                menuB.setMousePressed(true);
            } else
                musicsfxOptions.mousePressed(e);

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (isIn(e, menuB)) {
                if (menuB.isMousePressed())
                    GameState.state = GameState.MENU;
            } else
                musicsfxOptions.mouseReleased(e);

            menuB.resetBools();

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            menuB.setMouseOver(false);

            if (isIn(e, menuB))
                menuB.setMouseOver(true);
            else
                musicsfxOptions.mouseMoved(e);

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                GameState.state = GameState.MENU;

        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        private boolean isIn(MouseEvent e, PauseButton b) {
            return b.getBounds().contains(e.getX(), e.getY());
        }
}
