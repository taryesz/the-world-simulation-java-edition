import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Vector;

public class Human extends Animal {

    public Human(Game game, Size size) {
        super(game, size);
        this.setPower(5);
        this.setInitiative(4);
        this.setName("human");
    }

    public void drawOrg(Graphics g, int boardPosX, int boardPosY) {
        g.setColor(new Color(42, 76, 89));
        g.fillRect(getPos_x() + boardPosX, getPos_y() + boardPosY, 10, 10);
    }

    public void action(int[] x, int[] y, Vector<Organism> organismsCopy) {
        if (this.getKey() == KeyEvent.VK_LEFT) {
            if (!(x[0] - 10 < 0)) {
                x[0] = x[0] - 10;
            }
        }
        else if (this.getKey() == KeyEvent.VK_RIGHT) {
            if (!(x[0] + 10 > getGame().getBoardWidth() - 5)) {
                x[0] = x[0] + 10;
            }
        }
        else if (this.getKey() == KeyEvent.VK_UP) {
            if (!(y[0] - 10 < 0)) {
                y[0] = y[0] - 10;
            }
        }
        else if (this.getKey() == KeyEvent.VK_DOWN) {
            if (!(y[0] + 10 > getGame().getBoardHeight() - 5)) {
                y[0] = y[0] + 10;
            }
        }
        getSquare().setLocation(x[0], y[0]);
        getGame().repaint();
    }

    public void collide(Vector<Organism> organismsCopy) {
        for (Organism other : getGame().getOrganisms()) {
            if (this.getIdx() != other.getIdx()) {
                if (this.getPos_x() == other.getPos_x() && this.getPos_y() == other.getPos_y() && (!this.getCollided() && !other.getCollided())) {
                    if (Objects.equals(other.getName(), "turtle"))  {
                        other.collide(organismsCopy);
                    }
                    else if (Objects.equals(other.getName(), "antelope")) {
                        other.collide(organismsCopy);
                    }
                    else if (Objects.equals(other.getName(), "guarana")) {
                        other.collide(organismsCopy);
                    }
                    else {
                        comparePowers(other, organismsCopy);
                    }
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
            if (this.getAbility()) {
                activateAbility();
            }
            else {
                organismsCopy.remove(this);
                getGame().addMessage("<" + this.getName() + "> died of " + other.getName());
                System.exit(0);
            }
            this.setCollided(true);
            other.setCollided(true);
        } else {
            compareInitiatives(other, organismsCopy);
        }
    }

    public void compareInitiatives(Organism other, Vector<Organism> organismsCopy) {
        if (this.getInitiative() > other.getInitiative()) {
            if (this.getAbility()) {
                activateAbility();
            }
            else {
                organismsCopy.remove(this);
                getGame().addMessage("<" + this.getName() + "> died of " + other.getName());
                System.exit(0);
            }
            this.setCollided(true);
            other.setCollided(true);
        } else if (this.getInitiative() < other.getInitiative()) {
            organismsCopy.remove(other);
            getGame().addMessage("<" + other.getName() + "> died of " + this.getName());
            this.setCollided(true);
            other.setCollided(true);
        }
    }

    public void activateAbility() {
        int[] x = new int[] {getPos_x()};
        int[] y = new int[] {getPos_y()};
        this.generateRandomCoordinates(x, y);
        this.setPos_x(x[0]);
        this.setPos_y(y[0]);
        getGame().addMessage("<player> cannot get killed while ability enabled");
    }

}
