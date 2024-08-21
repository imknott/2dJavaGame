package soulsvania.levels;

import soulsvania.enitities.Bringer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import soulsvania.main.Game;
import soulsvania.object.Spikes;
import soulsvania.utils.HelperMethods;

import static soulsvania.utils.HelperMethods.GetBringers;
import static soulsvania.utils.HelperMethods.GetLevelData;
import static soulsvania.utils.HelperMethods.GetPlayerSpawn;

public class Level {


    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Bringer> bringers;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;
    private ArrayList<Spikes> spikes;

    public Level(BufferedImage img) {
        this.img = img;
        createLevelData();
        createEnemies();
        createSpikes();
        calcLvlOffsets();
        calcPlayerSpawn();
    }

    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }
    private void createSpikes() {
        spikes = HelperMethods.GetSpikes(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        bringers = GetBringers(img);
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    public int getLvlOffset() {
        return maxLvlOffsetX;
    }

    public ArrayList<Bringer> getBringers() {
        return bringers;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Spikes> getSpikes() {
        return spikes;
    }
}
