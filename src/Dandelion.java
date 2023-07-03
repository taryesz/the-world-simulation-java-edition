import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class Dandelion extends Plant {

    public Dandelion(Game game, Size size) {
        super(game, size);
        this.setName("dandelion");
    }

    public void drawOrg(Graphics g, int boardPosX, int boardPosY) {
        g.setColor(Color.darkGray);
        g.fillRect(getPos_x() + boardPosX, getPos_y() + boardPosY, 10, 10);
    }

}
