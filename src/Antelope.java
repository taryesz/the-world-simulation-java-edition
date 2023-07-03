import java.awt.*;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;

public class Antelope extends Animal {

    public Antelope(Game game, Size size) {
        super(game, size);
        this.setPower(4);
        this.setInitiative(4);
        this.setName("antelope");
    }

    public void drawOrg(Graphics g, int boardPosX, int boardPosY) {
        g.setColor(Color.pink);
        g.fillRect(getPos_x() + boardPosX, getPos_y() + boardPosY, 10, 10);
    }

    public void generateRandomX(int[] x) {
        Random rand = new Random();
        int newX = (rand.nextInt(2) == 0) ? (x[0] + 20) : (x[0] - 20);
        if (newX >= 0 && newX < getGame().getBoardWidth() - 5) {
            x[0] = newX;
        }
        else {
            generateRandomX(x);
        }
    }

    public void generateRandomY(int[] y) {
        Random rand = new Random();
        int newY = (rand.nextInt(2) == 0) ? (y[0] + 20) : (y[0] - 20);
        if (newY >= 0 && newY < getGame().getBoardHeight() - 5) {
            y[0] = newY;
        }
        else {
            generateRandomY(y);
        }
    }

    public void escape(Vector<Organism> organismsCopy, Organism other) {
        Random rand = new Random();
        boolean chance = rand.nextBoolean();

        if (chance) {
            int[] x = new int[] {getPos_x()};
            int[] y = new int[] {getPos_y()};
            this.generateRandomCoordinates(x, y);
            this.setPos_x(x[0]);
            this.setPos_y(y[0]);
            this.setCollided(true);
            other.setCollided(true);
            getGame().addMessage("<antelope> managed to escape death..");
        }
        else {
            organismsCopy.remove(this);
            getGame().addMessage("<" + this.getName() + "> died of " + other.getName());
            this.setCollided(true);
            other.setCollided(true);
        }
    }

    public void collide(Vector<Organism> organismsCopy) {
        for (Organism other : getGame().getOrganisms()) {
            if (this.getIdx() != other.getIdx()) {
                if (this.getPos_x() == other.getPos_x() && this.getPos_y() == other.getPos_y() && (!this.getCollided() || !other.getCollided())) {
                    if (Objects.equals(other.getName(), "turtle")) {
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
            escape(organismsCopy, other);
        } else {
            compareInitiatives(other, organismsCopy);
        }
    }

    public void compareInitiatives(Organism other, Vector<Organism> organismsCopy) {
        if (this.getInitiative() > other.getInitiative()) {
            organismsCopy.remove(this);
            getGame().addMessage("<" + this.getName() + "> died of " + other.getName());
            this.setCollided(true);
            other.setCollided(true);

        } else if (this.getInitiative() < other.getInitiative()) {
            escape(organismsCopy, other);
        } else {
            spreadAnimals(other, organismsCopy);
        }
    }

}
