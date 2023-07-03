import java.awt.*;

public class Wolf extends Animal {

    public Wolf(Game game, Size size) {
        super(game, size);
        this.setPower(9);
        this.setInitiative(5);
        this.setName("wolf");
    }

    public void drawOrg(Graphics g, int boardPosX, int boardPosY) {
        g.setColor(Color.red);
        g.fillRect(getPos_x() + boardPosX, getPos_y() + boardPosY, 10, 10);
    }

}
