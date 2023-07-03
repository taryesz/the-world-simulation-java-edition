import java.awt.*;
import java.util.Vector;

public class Guarana extends Plant {

    public Guarana(Game game, Size size) {
        super(game, size);
        this.setName("guarana");
    }

    public void drawOrg(Graphics g, int boardPosX, int boardPosY) {
        g.setColor(Color.magenta);
        g.fillRect(getPos_x() + boardPosX, getPos_y() + boardPosY, 10, 10);
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
        } else if (this.getPower() < other.getPower()) {
            organismsCopy.remove(this);
            getGame().addMessage("<" + this.getName() + "> died of " + other.getName() + ". increasing creature's power from " + other.getPower() + " to " + (3 + other.getPower()));
            other.setPower(3 + other.getPower());
            this.setCollided(true);
            other.setCollided(true);
        } else {
            compareInitiatives(other, organismsCopy);
        }
    }

    public void compareInitiatives(Organism other, Vector<Organism> organismsCopy) {
        if (this.getInitiative() > other.getInitiative()) {
            organismsCopy.remove(this);
            getGame().addMessage("<" + this.getName() + "> died of " + other.getName() + ". increasing creature's power from " + other.getPower() + " to " + (3 + other.getPower()));
            other.setPower(3 + other.getPower());
            this.setCollided(true);
            other.setCollided(true);
        } else if (this.getInitiative() < other.getInitiative()) {
            organismsCopy.remove(other);
            getGame().addMessage("<" + other.getName() + "> died of " + this.getName());
            this.setCollided(true);
            other.setCollided(true);
        }
    }

}
