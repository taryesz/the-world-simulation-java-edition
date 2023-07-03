import java.awt.*;
import java.util.Vector;

public class Turtle extends Animal {

    public Turtle(Game game, Size size) {
        super(game, size);
        this.setPower(2);
        this.setInitiative(1);
        this.setName("turtle");
    }

    public void drawOrg(Graphics g, int boardPosX, int boardPosY) {
        g.setColor(Color.blue);
        g.fillRect(getPos_x() + boardPosX, getPos_y() + boardPosY, 10, 10);
    }

    public void action(int[] x, int[] y, Vector<Organism> organismsCopy) {
        int desireToMove = (int) (Math.random() * 100);

        if (desireToMove > 75) {
            generateRandomCoordinates(x, y);
            getSquare().setLocation(x[0], y[0]);
        }
    }

    public void resist(Vector<Organism> organismsCopy, Organism other) {
        if (other.getPower() < 5) {
            other.setPos_x(other.getPos_x_prev());
            other.setPos_y(other.getPos_y_prev());
            getGame().addMessage("<" + other.getName() + "> was kicked away by the turtle. old coords of" + other.getName() + ": (" + other.getPos_x_prev() + ", " + other.getPos_y_prev() + "), new coords: (" + other.getPos_x() + ", " + other.getPos_y() + ")");
        }
        else {
            organismsCopy.remove(this);
            getGame().addMessage("<" + this.getName() + "> died of " + other.getName());
        }
        this.setCollided(true);
        other.setCollided(true);
    }

    public void collide(Vector<Organism> organismsCopy) {
        for (Organism other : getGame().getOrganisms()) {
            if (this.getIdx() != other.getIdx()) {
                if (this.getPos_x() == other.getPos_x() && this.getPos_y() == other.getPos_y() && (!this.getCollided() && !other.getCollided())) {
                    comparePowers(other, organismsCopy);
                }
            }
        }
    }

    public void comparePowers(Organism other, Vector<Organism> organismsCopy) {
        if (this.getPower() > other.getPower()) {
            organismsCopy.remove(other);
            getGame().addMessage("<" + other.getName() + "> died of " + this.getName());
            this.setCollided(true);
            other.setCollided(true);
        }
        else if (this.getPower() < other.getPower()) {
            resist(organismsCopy, other);
        }
        else {
            compareInitiatives(other, organismsCopy);
        }
    }

    public void compareInitiatives(Organism other, Vector<Organism> organismsCopy) {
        if (this.getInitiative() > other.getInitiative()) {
            organismsCopy.remove(this);
            getGame().addMessage("<" + this.getName() + "> died of " + other.getName());
            this.setCollided(true);
            other.setCollided(true);
        }
        else if (this.getInitiative() < other.getInitiative()) {
            resist(organismsCopy, other);
        } else {
            spreadAnimals(other, organismsCopy);
        }
    }

}
