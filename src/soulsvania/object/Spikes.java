package soulsvania.object;
import soulsvania.main.Game;
public class Spikes extends GameObject {

    public Spikes(int x, int y, int objType) {
        super(x, y, objType);

        initHitbox(32, 16);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * 16);
        hitbox.y += yDrawOffset;
    }
}
