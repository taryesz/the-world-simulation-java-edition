import java.awt.*;

public class Nightshade extends Plant {

    public Nightshade(Game game, Size size) {
        super(game, size);
        setPower(99);
        this.setName("nightshade");
    }

    public void drawOrg(Graphics g, int boardPosX, int boardPosY) {
        g.setColor(Color.black);
        g.fillRect(getPos_x() + boardPosX, getPos_y() + boardPosY, 10, 10);
    }

}
