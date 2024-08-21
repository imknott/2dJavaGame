package soulsvania.main;
import soulsvania.states.GameState;
import soulsvania.states.OptionsMenu;
import soulsvania.states.Playing;
import soulsvania.states.Menu;
import soulsvania.ui.MusicSfxOptions;

import java.awt.*;

import static soulsvania.states.GameState.*;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gp;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Playing playing;
    private Menu menu;
    private OptionsMenu gameOptions;
    private MusicSfxOptions audioOptions;


    public final static int  TILES_DEFAULT_SIZE = 32;
    public final static int  TILES_WIDTH = 26;
    public final static float SCALE = 1.5f;
    public final static int TILES_HEIGHT = 14;
    public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_HEIGHT;



    public Game(){
        initClasses();
        gp = new GamePanel(this);
        gameWindow = new GameWindow(gp);
        gp.setFocusable(true);
        gp.requestFocus();

        startGameLoop();


    }

    private void initClasses() {
        audioOptions = new MusicSfxOptions();
        menu = new Menu(this);
        playing = new Playing(this);
        gameOptions = new OptionsMenu(this);
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){
        switch (GameState.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
                gameOptions.update();
                break;
            case QUIT:
            default:
                System.exit(0);
                break;

        }

    }

    public void render(Graphics g){

        switch (GameState.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            case OPTIONS:
                gameOptions.draw(g);
                break;
            default:
                break;
        }


    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;//time
        double timePerUpdate = 1000000000.0 / UPS_SET;//frequency of updates

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltau = 0;
        double deltav = 0;

        //infinite loop that runs our game. so we need to set a close eventually.
        while(true){
            //after this is greater than the timePerFrame we want to repaint gamepanel
            long currentTime = System.nanoTime();

            deltau += (currentTime - previousTime) / timePerUpdate;
            deltav += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltau >= 1){
                update();
                updates++;
                deltau--;
            }

//            if(now - lastFrame >= timePerFrame){
//                gp.repaint();
//                lastFrame = now;
//                frames++;
//            }

            if(deltav >= 1){
                gp.repaint();
                frames++;
                deltav--;
            }

            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + "| UPS: " + updates);
                frames =0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost() {
        if(GameState.state == PLAYING){
            playing.getPlayer().resetDirBooleans();
        }
    }

    public Menu getMenu(){
        return menu;
    }

    public Playing getPlaying(){
        return playing;
    }

    public OptionsMenu getOptionsMenu(){
        return gameOptions;
    }

    public MusicSfxOptions getAudioOptions() {
        return audioOptions;
    }
}
