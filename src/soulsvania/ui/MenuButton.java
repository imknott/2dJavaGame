package soulsvania.ui;

import soulsvania.states.GameState;
import soulsvania.utils.LoadSave;
import soulsvania.utils.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

import static soulsvania.utils.Constants.Buttons.*;

public class MenuButton {

    private int xPos,yPos,index;
    private int xOffsetCenter = B_WIDTH/2;
    private GameState state;
    private int rowIndex;
    private BufferedImage[] imgs;
    private UtilityTool utool = new UtilityTool();
    private boolean mouseOver,mousePressed;
    private Rectangle bounds;

    public MenuButton(int xPos, int yPos, int rowIndex, GameState state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos-xOffsetCenter,yPos,B_WIDTH,B_HEIGHT);
    }

    private void loadImgs(){
        imgs = new BufferedImage[3];

        BufferedImage start = LoadSave.setup("randomimages/startButton");
        BufferedImage exit = LoadSave.setup("randomimages/exitButton");
        BufferedImage options = LoadSave.setup("randomimages/optionsButton");

        utool.scaleImage(start, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        utool.scaleImage(exit, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        utool.scaleImage(options, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);

        imgs[0] = start;
        imgs[1] = exit;
        imgs[2] = options;

    }

    public void draw(Graphics g){
        g.drawImage(imgs[rowIndex], xPos - xOffsetCenter, yPos, B_WIDTH,B_HEIGHT,null);
    }

    public void update(){
        index = 0;
        if(mouseOver){
            index = 1;
        }
        if(mousePressed){
            index = 2;
        }

    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }
    public void applyGameState(){
        GameState.state = state;
    }

    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }
}
