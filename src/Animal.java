import java.awt.*;
import java.util.Vector;

public abstract class Animal extends Organism {

    public Animal(Game game, Size size) {
        super(game, size);
    }

    public void action(int[] x, int[] y, Vector<Organism> organismsCopy) {
        generateRandomCoordinates(x, y);
        getSquare().setLocation(x[0], y[0]);
        getGame().repaint();
    }

    public abstract void drawOrg(Graphics g, int boardPosX, int boardPosY);

}
