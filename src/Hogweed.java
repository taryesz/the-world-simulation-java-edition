import java.awt.*;
import java.util.Vector;

public class Hogweed extends Plant {

    public Hogweed(Game game, Size size) {
        super(game, size);
        this.setPower(10);
        this.setName("hogweed");
    }

    public void drawOrg(Graphics g, int boardPosX, int boardPosY) {
        g.setColor(Color.lightGray);
        g.fillRect(getPos_x() + boardPosX, getPos_y() + boardPosY, 10, 10);
    }

    public void action(int[] x, int[] y, Vector<Organism> organismsCopy) {
        for (int i = 0; i < getGame().getOrganisms().size(); i++) {
            int otherX = getGame().getOrganisms().get(i).getPos_x();
            int otherY = getGame().getOrganisms().get(i).getPos_y();

            boolean left = (otherX == this.getPos_x() - 10 && otherY == this.getPos_y());
            boolean right = (otherX == this.getPos_x() + 10 && otherY == this.getPos_y());
            boolean top = (otherX == this.getPos_x() && otherY == this.getPos_y() - 10);
            boolean bottom = (otherX == this.getPos_x() && otherY == this.getPos_y() + 10);

            boolean[] deletion = new boolean[] {left, right, top, bottom};

            for (int j = 0; j < 4; j++) {
                if (deletion[j] && i <= organismsCopy.size()) {
                    if (!(getGame().getOrganisms().get(i).getAbility())) {
                        getGame().addMessage("<" + getGame().getOrganisms().get(i).getName() + "> died of hogweed");
                        this.setCollided(true);
                        getGame().getOrganisms().get(i).setCollided(true);
                        if (organismsCopy.get(i) instanceof Human) {
                            System.exit(0);
                        }
                        organismsCopy.remove(i);

                    }
                    else {
                        getGame().addMessage("<player> cannot get killed while ability enabled");
                    }
                }
            }

        }

        getSquare().setLocation(x[0], y[0]);
        getGame().repaint();
    }

    }
