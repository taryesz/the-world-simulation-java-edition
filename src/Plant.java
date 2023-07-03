import java.awt.*;
import java.util.Random;
import java.util.Vector;

public abstract class Plant extends Organism {

    public Plant(Game game, Size size) {
        super(game, size);
        setInitiative(0);
        setPower(0);
        setIsPlant(true);
    }
    public void action(int[] x, int[] y, Vector<Organism> organismsCopy) {
        Random random1 = new Random();
        int try1 = random1.nextInt(500);

        int attempts = 0;
        while (attempts < 3 && try1 == 0) {
            for (int i = 0; i < getGame().getOrganisms().size(); i++) {
                int otherX = getGame().getOrganisms().get(i).getPos_x();
                int otherY = getGame().getOrganisms().get(i).getPos_y();

                boolean left = (otherX == this.getPos_x() - 10 && otherY == this.getPos_y());
                boolean right = (otherX == this.getPos_x() + 10 && otherY == this.getPos_y());
                boolean top = (otherX == this.getPos_x() && otherY == this.getPos_y() - 10);
                boolean bottom = (otherX == this.getPos_x() && otherY == this.getPos_y() + 10);
                boolean adjacent = (left && right && top && bottom);

                if (!adjacent) {
                    Random target = new Random();
                    int targetR = target.nextInt(4);
                    if (targetR == 0 && !left) {
                        multiply(-10,0, organismsCopy);
                        break;
                    } else if (targetR == 1 && !right) {
                        multiply(10,0, organismsCopy);
                        break;
                    } else if (targetR == 2 && !top) {
                        multiply(0,-10, organismsCopy);
                        break;
                    } else if (targetR == 3 && !bottom) {
                        multiply(0,10, organismsCopy);
                        break;
                    }
                }
            }
            attempts++;
        }

    }

    public abstract void drawOrg(Graphics g, int boardPosX, int boardPosY);

    public void multiply(int newX, int newY, Vector<Organism> organismsCopy) {
        int initPosX = this.getPos_x() + newX;
        int initPosY = this.getPos_y() + newY;

        if ((initPosY >= 0 && initPosY < getGame().getBoardHeight() - 5) && (initPosX >= 0 && initPosX < getGame().getBoardWidth() - 5)) {
            Organism newOrg = null;
            if (this instanceof Dandelion) {
                newOrg = new Dandelion(getGame(), getSize());
            } else if (this instanceof Grass) {
                newOrg = new Grass(getGame(), getSize());
            } else if (this instanceof Guarana) {
                newOrg = new Guarana(getGame(), getSize());
            } else if (this instanceof Nightshade) {
                newOrg = new Nightshade(getGame(), getSize());
            }

            if (newOrg != null) {
                newOrg.setPos_x(initPosX);
                newOrg.setPos_y(initPosY);
                newOrg.setAge(1);
                newOrg.getSquare().setLocation(initPosX, initPosY);
                newOrg.getSquare().setSize(getSize().getWidth(), getSize().getHeight());
                newOrg.setCollided(true);
                organismsCopy.add(newOrg);
            }

        }
    }
}
