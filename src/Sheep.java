import java.awt.*;

public class Sheep extends Animal {

    public Sheep(Game game, Size size) {
        super(game, size);
        this.setPower(4);
        this.setInitiative(4);
        this.setName("sheep");
    }
    public void drawOrg(Graphics g, int boardPosX, int boardPosY) {
        g.setColor(Color.green);
        g.fillRect(getPos_x() + boardPosX, getPos_y() + boardPosY, 10, 10);
    }

}
