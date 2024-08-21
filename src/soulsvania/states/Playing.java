package soulsvania.states;

import soulsvania.enitities.EnemyManager;
import soulsvania.enitities.Player;
import soulsvania.levels.LevelManager;
import soulsvania.main.Game;
import soulsvania.main.Sound;
import soulsvania.object.ObjectManager;
import soulsvania.ui.GameOverLay;
import soulsvania.ui.LevelCompleteUI;
import soulsvania.ui.PauseUI;
import soulsvania.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Playing extends State implements GameStateMethods{
    private Player player;
    private LevelManager lm;
    private EnemyManager enemyManager;
    private PauseUI pauseUI;
    private GameOverLay gameOverLay;
    private LevelCompleteUI levelCompleteUI;
    private ObjectManager objectManager;
    Sound music = new Sound();
    private boolean paused = false;

    private int xLevelOffset;
    //20% to the left 20% to the right
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int maxLvlOffsetX;

    BufferedImage backgroundImage, getBackgroundImage1, getBackgroundImage2,leaves;

    private boolean gameOver;
    private boolean lvlCompleted;
    private boolean playerDying;

    public Playing(Game game) {
        super(game);
        initClasses();
        backgroundImage = LoadSave.setup("randomimages/background_layer_1");
        getBackgroundImage1 = LoadSave.setup("randomimages/background_layer_2");
        getBackgroundImage2 = LoadSave.setup("randomimages/background_layer_3");
        playMusic(0);

        calcLvlOffset();
        loadStartLevel();
    }

    public void loadNextLevel() {
        resetAll();
        lm.loadNextLevel();
        player.setSpawn(lm.getCurrentLevel().getPlayerSpawn());
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(lm.getCurrentLevel());
        objectManager.loadObjects(lm.getCurrentLevel());
    }

    private void calcLvlOffset() {
        maxLvlOffsetX = lm.getCurrentLevel().getLvlOffset();
    }

    private void initClasses() {
        lm = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);
        //this player entity will change the size of the hitbox
        player = new Player(200F, 200F, (int) (75*Game.SCALE), (int) (50*Game.SCALE),this);
        player.loadLvlData(lm.getCurrentLevel().getLevelData());
        player.setSpawn(lm.getCurrentLevel().getPlayerSpawn());
        pauseUI = new PauseUI(this);
        gameOverLay = new GameOverLay(this);
        levelCompleteUI = new LevelCompleteUI(this);

    }

    public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    @Override
    public void update() {
        if (paused) {
            pauseUI.update();
        } else if (lvlCompleted) {
            levelCompleteUI.update();
        } else if (gameOver) {
            gameOverLay.update();
        } else if (playerDying) {
            player.update();
        } else {
            lm.update();
            player.update();
            enemyManager.update(lm.getCurrentLevel().getLevelData(), player);
            checkCloseToBorder();
        }

    }
    private void checkCloseToBorder() {
        int playerX = (int) player.getHitBox().x;
        int diff = playerX - xLevelOffset;

        if (diff > rightBorder)
            xLevelOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLevelOffset += diff - leftBorder;

        if (xLevelOffset > maxLvlOffsetX)
            xLevelOffset = maxLvlOffsetX;
        else if (xLevelOffset < 0)
            xLevelOffset = 0;

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(getBackgroundImage1, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        //I moved the second layer utilizing my xLevelOffset to make the trees move with the character.
        g.drawImage(getBackgroundImage2, 0 - (int)(xLevelOffset * 0.3), 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        lm.draw(g,xLevelOffset);
        player.render(g,xLevelOffset);
        enemyManager.draw(g, xLevelOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseUI.draw(g);
        }
        else if (gameOver)
            gameOverLay.draw(g);
        else if (lvlCompleted)
            levelCompleteUI.draw(g);
    }

    public void resetAll() {
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        playerDying = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    public void mouseDragged(MouseEvent e) {
        if (!gameOver)
            if (paused)
                pauseUI.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseUI.mousePressed(e);
            else if (lvlCompleted)
                levelCompleteUI.mousePressed(e);
        } else
            gameOverLay.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseUI.mouseReleased(e);
            else if (lvlCompleted)
                levelCompleteUI.mouseReleased(e);
        } else
            gameOverLay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseUI.mouseMoved(e);
            else if (lvlCompleted)
                levelCompleteUI.mouseMoved(e);
        } else
            gameOverLay.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver)
            gameOverLay.keyPressed(e);
        else
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                    case KeyEvent.VK_J:
                        player.setAttacking(true);
                        playSoundEffect(2);
                        break;
                case KeyEvent.VK_P:
                    paused = !paused;
                    break;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
    }

    public void checkSpikesTouched(Player p) {
        objectManager.checkSpikesTouched(p);
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.lvlCompleted = levelCompleted;
    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    public void unpauseGame() {
        paused = false;
    }


    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public LevelManager getLevelManager() {
        return lm;
    }
    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;

    }

    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();

    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int i){
        music.setFile(i);
        music.play();
    }

}
