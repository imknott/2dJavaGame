package soulsvania.enitities;

import soulsvania.levels.Level;
import soulsvania.states.Playing;
import soulsvania.utils.Constants;
import soulsvania.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static soulsvania.utils.Constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private Bringer c;
    private BufferedImage[] bringerOfDeathAttack, boddeath, bodhurt, bodwalk, bodidle;
    private ArrayList<Bringer> bringers = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        bringers = level.getBringers();
    }

    public void update(int[][] levelData,Player player) {
        boolean isAnyActive = false;
        for (Bringer c : bringers)
            if (c.isActive()) {
                c.update(levelData, player);
                isAnyActive = true;
            }
        if(!isAnyActive)
            playing.setLevelCompleted(true);
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawBringers(g, xLvlOffset);
    }

    private void drawBringers(Graphics g, int xLvlOffset) {
        for (Bringer c : bringers) {
            if (c.isActive()) {
                g.drawImage(setArraytoRender(c.getEnemyState(), c.getAniIndex()), (int) c.getHitBox().x - xLvlOffset - BRINGER_DRAWOFFSET_X + c.flipX(), (int) c.getHitBox().y - BRINGER_DRAWOFFSET_Y, BRINGER_WIDTH* c.flipW(), BRINGER_HEIGHT, null);
                c.drawHitBox(g, xLvlOffset);
            }
        }

    }

    public BufferedImage setArraytoRender(int enemyState, int aniIndex){
        if(enemyState == Constants.EnemyConstants.IDLE){
            return bodidle[aniIndex];
        }else if(enemyState == Constants.EnemyConstants.WALK){
            return bodwalk[aniIndex];
        }else if(enemyState == Constants.EnemyConstants.ATTACK){
            return bringerOfDeathAttack[aniIndex];
        }else if(enemyState == Constants.EnemyConstants.HIT){
            return bodhurt[aniIndex];
        }else if(enemyState == Constants.EnemyConstants.DEAD){
            return boddeath[aniIndex];
        }
        return null;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Bringer c : bringers)
            if (c.isActive())
                if (attackBox.intersects(c.getHitBox())) {
                    playing.playSoundEffect(3);
                    c.hurt(10);
                    return;
                }
    }

    private void loadEnemyImgs() {

        //set arrrays to use for animations
        bringerOfDeathAttack = new BufferedImage[10];
        boddeath = new BufferedImage[10];
        bodhurt = new BufferedImage[4];
        bodidle = new BufferedImage[8];
        bodwalk = new BufferedImage[8];

        //hurt images
        BufferedImage bringerHurtImage1 = LoadSave.setup("Enemy_Hurt/Bringer-of-Death_Hurt_1");
        BufferedImage bringerHurtImage2 = LoadSave.setup("Enemy_Hurt/Bringer-of-Death_Hurt_2");
        BufferedImage bringerHurtImage3 = LoadSave.setup("Enemy_Hurt/Bringer-of-Death_Hurt_3");
//idle images
        BufferedImage bringerIdleImage1 = LoadSave.setup("Enemy_Idle/Bringer-of-Death_Idle_1");
        BufferedImage bringerIdleImage2 = LoadSave.setup("Enemy_Idle/Bringer-of-Death_Idle_2");
        BufferedImage bringerIdleImage3 = LoadSave.setup("Enemy_Idle/Bringer-of-Death_Idle_3");
        BufferedImage bringerIdleImage4 = LoadSave.setup("Enemy_Idle/Bringer-of-Death_Idle_4");
        BufferedImage bringerIdleImage5 = LoadSave.setup("Enemy_Idle/Bringer-of-Death_Idle_5");
        BufferedImage bringerIdleImage6 = LoadSave.setup("Enemy_Idle/Bringer-of-Death_Idle_6");
        BufferedImage bringerIdleImage7 = LoadSave.setup("Enemy_Idle/Bringer-of-Death_Idle_7");
        BufferedImage bringerIdleImage8 = LoadSave.setup("Enemy_Idle/Bringer-of-Death_Idle_8");
//walk images
        BufferedImage bringerWalkImage1 = LoadSave.setup("Enemy_Walk/Bringer-of-Death_Walk_1");
        BufferedImage bringerWalkImage2 = LoadSave.setup("Enemy_Walk/Bringer-of-Death_Walk_2");
        BufferedImage bringerWalkImage3 = LoadSave.setup("Enemy_Walk/Bringer-of-Death_Walk_3");
        BufferedImage bringerWalkImage4 = LoadSave.setup("Enemy_Walk/Bringer-of-Death_Walk_4");
        BufferedImage bringerWalkImage5 = LoadSave.setup("Enemy_Walk/Bringer-of-Death_Walk_5");
        BufferedImage bringerWalkImage6 = LoadSave.setup("Enemy_Walk/Bringer-of-Death_Walk_6");
        BufferedImage bringerWalkImage7 = LoadSave.setup("Enemy_Walk/Bringer-of-Death_Walk_7");
        BufferedImage bringerWalkImage8 = LoadSave.setup("Enemy_Walk/Bringer-of-Death_Walk_8");
// Attack images
        BufferedImage bringerAttackImage1 = LoadSave.setup("Enemy_Attack/Bringer-of-Death_Attack_1");
        BufferedImage bringerAttackImage2 = LoadSave.setup("Enemy_Attack/Bringer-of-Death_Attack_2");
        BufferedImage bringerAttackImage3 = LoadSave.setup("Enemy_Attack/Bringer-of-Death_Attack_3");
        BufferedImage bringerAttackImage4 = LoadSave.setup("Enemy_Attack/Bringer-of-Death_Attack_4");
        BufferedImage bringerAttackImage5 = LoadSave.setup("Enemy_Attack/Bringer-of-Death_Attack_5");
        BufferedImage bringerAttackImage6 = LoadSave.setup("Enemy_Attack/Bringer-of-Death_Attack_6");
        BufferedImage bringerAttackImage7 = LoadSave.setup("Enemy_Attack/Bringer-of-Death_Attack_7");
        BufferedImage bringerAttackImage8 = LoadSave.setup("Enemy_Attack/Bringer-of-Death_Attack_8");
        BufferedImage bringerAttackImage9 = LoadSave.setup("Enemy_Attack/Bringer-of-Death_Attack_9");
        BufferedImage bringerAttackImage10 = LoadSave.setup("Enemy_Attack/Bringer-of-Death_Attack_10");
        //death images
        BufferedImage bringerDeathImage1 = LoadSave.setup("Enemy_Death/Bringer-of-Death_Death_1");
        BufferedImage bringerDeathImage2 = LoadSave.setup("Enemy_Death/Bringer-of-Death_Death_2");
        BufferedImage bringerDeathImage3 = LoadSave.setup("Enemy_Death/Bringer-of-Death_Death_3");
        BufferedImage bringerDeathImage4 = LoadSave.setup("Enemy_Death/Bringer-of-Death_Death_4");
        BufferedImage bringerDeathImage5 = LoadSave.setup("Enemy_Death/Bringer-of-Death_Death_5");
        BufferedImage bringerDeathImage6 = LoadSave.setup("Enemy_Death/Bringer-of-Death_Death_6");
        BufferedImage bringerDeathImage7 = LoadSave.setup("Enemy_Death/Bringer-of-Death_Death_7");
        BufferedImage bringerDeathImage8 = LoadSave.setup("Enemy_Death/Bringer-of-Death_Death_8");
        BufferedImage bringerDeathImage9 = LoadSave.setup("Enemy_Death/Bringer-of-Death_Death_9");
        BufferedImage bringerDeathImage10 = LoadSave.setup("Enemy_Death/Bringer-of-Death_Death_10");



        //initialize enemey arrays below these will be used for the different soulsvania.states the enemy will be in
        bodwalk[0] =bringerWalkImage1;
        bodwalk[1] =bringerWalkImage2;
        bodwalk[2] =bringerWalkImage3;
        bodwalk[3] =bringerWalkImage4;
        bodwalk[4] =bringerWalkImage5;
        bodwalk[5] =bringerWalkImage6;
        bodwalk[6] =bringerWalkImage7;
        bodwalk[7] =bringerWalkImage8;

        boddeath[0] = bringerDeathImage1;
        boddeath[1] = bringerDeathImage2;
        boddeath[2] = bringerDeathImage3;
        boddeath[3] = bringerDeathImage4;
        boddeath[4] = bringerDeathImage5;
        boddeath[5] = bringerDeathImage6;
        boddeath[6] = bringerDeathImage7;
        boddeath[7] = bringerDeathImage8;
        boddeath[8] = bringerDeathImage9;
        boddeath[9] = bringerDeathImage10;

        bodhurt[0] = bringerHurtImage1;
        bodhurt[1] = bringerHurtImage2;
        bodhurt[2] = bringerHurtImage3;

        bringerOfDeathAttack[0] =bringerAttackImage1;
        bringerOfDeathAttack[1] = bringerAttackImage2;
        bringerOfDeathAttack[2] = bringerAttackImage3;
        bringerOfDeathAttack[3] = bringerAttackImage4;
        bringerOfDeathAttack[4] = bringerAttackImage5;
        bringerOfDeathAttack[5] = bringerAttackImage6;
        bringerOfDeathAttack[6] = bringerAttackImage7;
        bringerOfDeathAttack[7] = bringerAttackImage8;
        bringerOfDeathAttack[8] = bringerAttackImage9;
        bringerOfDeathAttack[9] = bringerAttackImage10;

        bodidle[0] =bringerIdleImage1;
        bodidle[1] =bringerIdleImage2;
        bodidle[2] =bringerIdleImage3;
        bodidle[3] =bringerIdleImage4;
        bodidle[4] =bringerIdleImage5;
        bodidle[5] =bringerIdleImage6;
        bodidle[6] =bringerIdleImage7;
        bodidle[7] =bringerIdleImage8;

    }

    public void resetAllEnemies() {
        for (Bringer c : bringers)
            c.resetEnemy();
    }

}


