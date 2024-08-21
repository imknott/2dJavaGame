package soulsvania.enitities;


import soulsvania.main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static soulsvania.utils.Constants.Directions.RIGHT;
import static soulsvania.utils.Constants.EnemyConstants.*;

public class Bringer extends Enemy{
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    public Bringer(Float x, Float y) {
        super(x, y, BRINGER_WIDTH, BRINGER_HEIGHT,BRINGER);
        initHitBox(x,y,(int)(22*Game.SCALE),(int)(30*Game.SCALE));
        initAttackBox();
    }

    public void update(int[][] levelData, Player player) {
        updateAnimationTick();
        updateMove(levelData, player);
        updateAnimationTick();
    }
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
        attackBoxOffsetX = (int) (Game.SCALE * 22);
    }

    private void updateAttackBox() {
        attackBox.x = hitBox.x - attackBoxOffsetX;
        attackBox.y = hitBox.y;
    }


    private void updateMove(int[][] lvlData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }

        if (inAir) {
            updateInAir(lvlData);
        } else {
            switch (enemyState) {
                case IDLE:
                    newState(WALK);
                    break;
                case WALK:
                    if (canSeePlayer(lvlData, player))
                        turnTowardsPlayer(player);
                    if (isPlayerCloseForAttack(player))
                        newState(ATTACK);
                   move(lvlData);
                    break;
                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 3 && !attackChecked)
                        checkPlayerHit(attackBox, player);
                    break;
                case HIT:
                    break;
            }
        }

    }
    public void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    public int flipX() {
        if (walkDir == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if (walkDir == RIGHT)
            return -1;
        else
            return 1;
    }


}
