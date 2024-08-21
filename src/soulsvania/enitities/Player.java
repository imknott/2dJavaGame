package soulsvania.enitities;

import soulsvania.main.Game;
import soulsvania.states.Playing;
import soulsvania.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static soulsvania.utils.Constants.ANI_SPEED;
import static soulsvania.utils.Constants.PlayerConstants.*;
import static soulsvania.utils.HelperMethods.*;
import static soulsvania.utils.LoadSave.setup;


public class Player extends Entity{

    private BufferedImage[] playerMove_IdleAni,playerFallAni, playerAttacksAni,playerMove_WalkRightAni, playerJumpAni,deathAni, hurtAni;
    private BufferedImage[] shootAni;
    private int aniTick, aniIndex, aniSpeed=20, aniRow;
    private int playerAction = IDLE;
   // private int playerDirection = -1;//idle
    private boolean moving = false, attacking = false;
    private boolean left,up,right,down,jump;
    private float playerSpeed = 2.0F;
    private int[][] lvlData;
    private float xDrawOffset = 25* Game.SCALE, yDrawOffset =14*Game.SCALE;

    //Jumping and gravity variables.
    private float airSpeed = 0F;
    private float gravity = 0.04F*Game.SCALE;
    private float jumpSpeed = -2.25F*Game.SCALE;
    private float fallSpeedAfterCollision = 0.5F * Game.SCALE;
    private boolean inAir = false;

    // StatusBarUI
    private BufferedImage statusBarImg;

    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);

    private int maxHealth = 10;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    // AttackBox
    private Rectangle2D.Float attackBox;

    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked;
    private Playing playing;

    int tileY = 0;




    public Player(Float x, Float y, Integer width, Integer height, Playing playing) {
        super(x, y,width,height);
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = 100;
        this.walkSpeed = Game.SCALE * 1.0f;
        loadAnimations();
        initHitBox(x,y, (int) (18*Game.SCALE), (int) (30*Game.SCALE));
        initAttackBox();
    }

    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;
        hitBox.x = x;
        hitBox.y = y;
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (18 * Game.SCALE), (int) (20 * Game.SCALE));
    }

    public void update(){
        updateHealthBar();

        if (currentHealth <= 0) {
            if (state != DEAD) {
                state = DEAD;
                aniTick = 0;
                aniIndex = 0;
                playing.setPlayerDying(true);
            } else if (aniIndex == GetSpriteAmount(DEAD) - 1 && aniTick >= ANI_SPEED - 1) {
                playing.setGameOver(true);
            } else
                updateAnimationTick();

            return;
        }

        updateAttackBox();

        updatePos();
        if (moving) {
            checkSpikesTouched();
            tileY = (int) (hitBox.y / Game.TILES_SIZE);
        }
        if (attacking)
            checkAttack();

        updateAnimationTick();
        setAnimation();

    }

    private void checkSpikesTouched() {
        playing.checkSpikesTouched(this);

    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 1)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);

    }

    private void updateAttackBox() {
        if (right)
            attackBox.x = hitBox.x + hitBox.width + (int) (Game.SCALE * 10);
        else if (left)
            attackBox.x = hitBox.x - hitBox.width - (int) (Game.SCALE * 10);

        attackBox.y = hitBox.y + (Game.SCALE * 10);
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }


    public void render(Graphics g, int levelOffset){
        if(state == IDLE){
            g.drawImage(playerMove_IdleAni[aniIndex], (int) (hitBox.x - xDrawOffset) - levelOffset + flipX, (int) (hitBox.y - yDrawOffset),width*flipW,height,null);
        }else if(state == RUN){
            g.drawImage(playerMove_WalkRightAni[aniIndex], (int) (hitBox.x - xDrawOffset) - levelOffset + flipX, (int) (hitBox.y - yDrawOffset),width*flipW,height,null);;
        }else if(state == ATTACK_1){
            g.drawImage(playerAttacksAni[aniIndex], (int) (hitBox.x - xDrawOffset) - levelOffset + flipX, (int) (hitBox.y - yDrawOffset),width*flipW,height,null);;
        }else if(state == JUMP){
            g.drawImage(playerJumpAni[aniIndex], (int) (hitBox.x - xDrawOffset) - levelOffset + flipX, (int) (hitBox.y - yDrawOffset),width*flipW,height,null);
        }else if(state == FALL){
            g.drawImage(playerFallAni[aniIndex], (int) (hitBox.x - xDrawOffset) - levelOffset + flipX, (int) (hitBox.y - yDrawOffset),width*flipW,height,null);
        } else if (state == DEAD) {
            g.drawImage(deathAni[aniIndex], (int) (hitBox.x - xDrawOffset) - levelOffset + flipX, (int) (hitBox.y - yDrawOffset),width*flipW,height,null);
        }else if (state == HURT) {
            g.drawImage(hurtAni[aniIndex], (int) (hitBox.x - xDrawOffset) - levelOffset + flipX, (int) (hitBox.y - yDrawOffset),width*flipW,height,null);
        }
        drawHitBox(g,levelOffset);
        drawAttackBox(g,levelOffset);
        drawUI(g);
    }

    private void drawAttackBox(Graphics g, int lvlOffsetX) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - lvlOffsetX, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);

    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }



    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(state)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    private void setAnimation() {
        int startAni = state;

        if (moving)
            state = RUN;
        else
            state = IDLE;

        if (inAir) {
            if (airSpeed < 0)
                state = JUMP;
            else
                state = FALL;
        }

        if (attacking) {
            state = ATTACK_1;
            if (startAni != ATTACK_1) {
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }
        if (startAni != state){
            resetAniTick();
            }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = false;

        if (jump)
            jump();

        if (!inAir)
            if ((!left && !right) || (right && left))
                return;

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir)
            if (!IsEntityOnFloor(hitBox, lvlData))
                inAir = true;

        if (inAir) {
            if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else
            updateXPos(xSpeed);
        moving = true;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
            hitBox.x += xSpeed;
        } else {
            hitBox.x = GetEntityXPosNextToWall(hitBox, xSpeed);
        }

    }

    public void changeHealth(int value) {
        currentHealth += value;

        if (currentHealth <= 0)
            currentHealth = 0;
        else if (currentHealth >= maxHealth)
            currentHealth = maxHealth;
    }

    public void kill() {
        currentHealth = 0;
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!IsEntityOnFloor(hitBox, lvlData))
            inAir = true;

    }

    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;

    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;

    }

    public void setAttacking(boolean attacking){
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up=false;
        down=false;
    }


    private void loadAnimations() {



        BufferedImage wlkRightImg = setup("Run/Warrior_Run_1");
        BufferedImage wlkRightImg1 = setup("Run/Warrior_Run_2");
        BufferedImage wlkRightImg2 = setup("Run/Warrior_Run_3");
        BufferedImage wlkRightImg3 = setup("Run/Warrior_Run_4");
        BufferedImage wlkRightImg4 = setup("Run/Warrior_Run_5");
        BufferedImage wlkRightImg5 = setup("Run/Warrior_Run_6");
        BufferedImage wlkRightImg6 = setup("Run/Warrior_Run_7");
        BufferedImage wlkRightImg7 = setup("Run/Warrior_Run_8");

        BufferedImage jumpImage = setup("Jump/Warrior_Jump_1");
        BufferedImage jumpImage2 = setup("Jump/Warrior_Jump_2");
        BufferedImage jumpImage3 = setup("Jump/Warrior_Jump_3");

        BufferedImage fallImage = setup("Fall/Warrior_Fall_1");
        BufferedImage fallImage2 = setup("Fall/Warrior_Fall_2");
        BufferedImage fallImage3 = setup("Fall/Warrior_Fall_3");



        BufferedImage idleImg = setup("Idle/Warrior_Idle_1");
        BufferedImage idleImg2 = setup("Idle/Warrior_Idle_2");
        BufferedImage idleImg3 = setup("Idle/Warrior_Idle_3");
        BufferedImage idleImg4 = setup("Idle/Warrior_Idle_4");
        BufferedImage idleImg5 = setup("Idle/Warrior_Idle_5");
        BufferedImage idleImg6 = setup("Idle/Warrior_Idle_6");

        BufferedImage attackImg = setup("Attack/Warrior_Attack_1");
        BufferedImage attackImg2 = setup("Attack/Warrior_Attack_2");
        BufferedImage attackImg3 = setup("Attack/Warrior_Attack_3");
        BufferedImage attackImg4 = setup("Attack/Warrior_Attack_4");
        BufferedImage attackImg5 = setup("Attack/Warrior_Attack_5");
        BufferedImage attackImg6 = setup("Attack/Warrior_Attack_6");
        BufferedImage attackImg7 = setup("Attack/Warrior_Attack_7");
        BufferedImage attackImg8 = setup("Attack/Warrior_Attack_8");
        BufferedImage attackImg9 = setup("Attack/Warrior_Attack_9");
        BufferedImage attackImg10 = setup("Attack/Warrior_Attack_10");
        BufferedImage attackImg11 = setup("Attack/Warrior_Attack_11");
        BufferedImage attackImg12 = setup("Attack/Warrior_Attack_12");

//        BufferedImage hurtImage1 = setup("Hurt-Effect/Warrior_Hurt_1");
//        BufferedImage hurtImage2 = setup("Hurt-Effect/Warrior_Hurt_2");
//        BufferedImage hurtImage3 = setup("Hurt-Effect/Warrior_Hurt_3");
//        BufferedImage hurtImage4 = setup("Hurt-Effect/Warrior_Hurt_4");

        BufferedImage deathImage1 = setup("Death-Effect/Warrior_Death_1");
        BufferedImage deathImage2 = setup("Death-Effect/Warrior_Death_2");
        BufferedImage deathImage3 = setup("Death-Effect/Warrior_Death_3");
        BufferedImage deathImage4 = setup("Death-Effect/Warrior_Death_4");
        BufferedImage deathImage5 = setup("Death-Effect/Warrior_Death_5");
        BufferedImage deathImage6 = setup("Death-Effect/Warrior_Death_6");
        BufferedImage deathImage7 = setup("Death-Effect/Warrior_Death_7");
        BufferedImage deathImage8 = setup("Death-Effect/Warrior_Death_8");
        BufferedImage deathImage9 = setup("Death-Effect/Warrior_Death_9");
        BufferedImage deathImage10 = setup("Death-Effect/Warrior_Death_10");
        BufferedImage deathImage11 = setup("Death-Effect/Warrior_Death_11");



        playerMove_IdleAni = new BufferedImage[8];
        playerJumpAni = new BufferedImage[6];
        playerFallAni = new BufferedImage[6];
        playerAttacksAni = new BufferedImage[12];
        playerMove_WalkRightAni = new BufferedImage[8];
        shootAni = new BufferedImage[14];
        hurtAni = new BufferedImage[12];
        deathAni = new BufferedImage[13];

        playerMove_IdleAni[0] = idleImg;
        playerMove_IdleAni[1] = idleImg2;
        playerMove_IdleAni[2] = idleImg3;
        playerMove_IdleAni[3] = idleImg4;
        playerMove_IdleAni[4] = idleImg5;
        playerMove_IdleAni[5] = idleImg6;

        playerMove_WalkRightAni[0] = wlkRightImg;
        playerMove_WalkRightAni[1] = wlkRightImg1;
        playerMove_WalkRightAni[2] = wlkRightImg2;
        playerMove_WalkRightAni[3] = wlkRightImg3;
        playerMove_WalkRightAni[4] = wlkRightImg4;
        playerMove_WalkRightAni[5] = wlkRightImg5;
        playerMove_WalkRightAni[6] = wlkRightImg6;
        playerMove_WalkRightAni[7] = wlkRightImg7;

        playerJumpAni[0] = jumpImage;
        playerJumpAni[1] = jumpImage2;
        playerJumpAni[2] = jumpImage3;

        playerFallAni[0] = fallImage;
        playerFallAni[1] = fallImage2;
        playerFallAni[2] = fallImage3;

           playerAttacksAni[0] = attackImg;
           playerAttacksAni[1] = attackImg2;
           playerAttacksAni[2] = attackImg3;
           playerAttacksAni[3] = attackImg4;
           playerAttacksAni[4] = attackImg5;
        playerAttacksAni[5] = attackImg6;
        playerAttacksAni[6] = attackImg7;
        playerAttacksAni[7] = attackImg8;
        playerAttacksAni[8] = attackImg9;
        playerAttacksAni[9] = attackImg10;
        playerAttacksAni[10] = attackImg11;
        playerAttacksAni[11] = attackImg12;

//        hurtAni[0] = hurtImage1;
//        hurtAni[1] = hurtImage2;
//        hurtAni[2] = hurtImage3;
//        hurtAni[3] = hurtImage4;

        deathAni[0] = deathImage1;
        deathAni[1] = deathImage2;
        deathAni[2] = deathImage3;
        deathAni[3] = deathImage4;
        deathAni[4] = deathImage5;
        deathAni[5] = deathImage6;
        deathAni[6] = deathImage7;
        deathAni[7] = deathImage8;
        deathAni[8] = deathImage9;
        deathAni[9] = deathImage10;
        deathAni[10] = deathImage11;

        statusBarImg = setup("randomimages/health_power_bar");



    }


    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        state = IDLE;
        currentHealth = maxHealth;

        hitBox.x = x;
        hitBox.y = y;

        if (!IsEntityOnFloor(hitBox, lvlData))
            inAir = true;
    }

    public int getTileY() {
        return tileY;
    }

}
