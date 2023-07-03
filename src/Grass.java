import java.awt.*;

public class Grass extends Plant {

    public Grass(Game game, Size size) {
        super(game, size);
        this.setName("grass");
    }

    public void drawOrg(Graphics g, int boardPosX, int boardPosY) {
        g.setColor(Color.cyan);
        g.fillRect(getPos_x() + boardPosX, getPos_y() + boardPosY, 10, 10);
    }

}
