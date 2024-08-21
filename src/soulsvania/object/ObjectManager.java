package soulsvania.object;

import soulsvania.enitities.Player;
import soulsvania.levels.Level;
import soulsvania.states.Playing;
import soulsvania.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static soulsvania.utils.Constants.ObjectConstants.SPIKE_HEIGHT;
import static soulsvania.utils.Constants.ObjectConstants.SPIKE_WIDTH;

public class ObjectManager {
    private Playing playing;
    private BufferedImage spikeImg;;
    private ArrayList<Spikes> spikes;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
    }

    public void checkSpikesTouched(Player p) {
        for (Spikes s : spikes)
            if (s.getHitbox().intersects(p.getHitBox()))
                p.kill();
    }


public void loadObjects(Level newLevel) {
    spikes = newLevel.getSpikes();
}

private void loadImgs() {;

    spikeImg = LoadSave.setup("randomimages/trap_atlas");
}

public void update() {
}

public void draw(Graphics g, int xLvlOffset) {
    drawTraps(g, xLvlOffset);
}

private void drawTraps(Graphics g, int xLvlOffset) {
    for (Spikes s : spikes)
        g.drawImage(spikeImg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);

}


}
