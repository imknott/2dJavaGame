package soulsvania.levels;

import soulsvania.enitities.EnemyManager;
import soulsvania.enitities.Player;
import soulsvania.main.Game;
import soulsvania.states.GameState;
import soulsvania.ui.GameOverLay;
import soulsvania.ui.LevelCompleteUI;
import soulsvania.ui.PauseUI;
import soulsvania.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {

    private Game game;
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseUI pauseUI;
    private GameOverLay gameOverLay;
    private LevelCompleteUI levelCompleteUI;
    private boolean paused = false;

    private BufferedImage[] levelImage;
    private ArrayList<Level> levels;
    private int lvlIndex = 0;


    public LevelManager(Game game){
        this.game = game;
        //levelImage = LoadSave.setup("oak_woods_tileset");
        importTileSprites();
        levels = new ArrayList<>();
        buildAllLevels();

    }

    public void loadNextLevel() {
        lvlIndex++;
        if (lvlIndex >= levels.size()) {
            lvlIndex = 0;
            System.out.println("No more soulsvania.levels! Game Completed!");
            GameState.state = GameState.MENU;
        }

        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for (BufferedImage img : allLevels)
            levels.add(new Level(img));
    }

    private void importTileSprites() {
        BufferedImage image =  LoadSave.setup("randomimages/outside_sprites");
        levelImage = new BufferedImage[48];
        for(int j = 0; j < 4; j++){
            for(int k = 0; k < 12; k++){
                int index = j * 12 + k;
                levelImage[index] = image.getSubimage(k*32,j*32,32,32);
            }
        }
    }

    public void draw(Graphics g,int levelOffset){
        for (int j = 0; j < Game.TILES_HEIGHT; j++)
            for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                g.drawImage(levelImage[index], Game.TILES_SIZE * i - levelOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }

    }

    public void update(){

    }

    public Level getCurrentLevel() {
        return levels.get(lvlIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }


}
