import java.awt.*;
import java.util.Vector;

public class Fox extends Animal {

    public Fox(Game game, Size size) {
        super(game, size);
        this.setPower(3);
        this.setInitiative(7);
        this.setName("fox");
    }
    public void drawOrg(Graphics g, int boardPosX, int boardPosY) {
        g.setColor(Color.orange);
        g.fillRect(getPos_x() + boardPosX, getPos_y() + boardPosY, 10, 10);
    }

    public void action(int[] x, int[] y, Vector<Organism> organismsCopy) {
        generateRandomCoordinates(x, y);

        boolean danger = false;

        for (int i = 0; i < getGame().getOrganisms().size(); i++) {
            if (x[0] == getGame().getOrganisms().get(i).getPos_x() && y[0] == getGame().getOrganisms().get(i).getPos_y()) {
                if (getGame().getOrganisms().get(i).getPower() > this.getPower()) {
                    getGame().addMessage("<fox> found out that it's dangerous for it there");
                    danger = true;
                }
            }
        }

        if (danger) {
            action(x, y, organismsCopy);
        }
        else {
            getSquare().setLocation(x[0], y[0]);
            getGame().repaint();
        }
    }

}
