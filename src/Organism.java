import java.awt.*;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;

public abstract class Organism {

    private int power;
    private int initiative;
    private int pos_x;
    private int pos_y;
    private int pos_x_prev;
    private int pos_y_prev;
    private String name;
    private int age;
    private int idx;
    private boolean collided;
    private Size size;
    private Game game;
    private final Rectangle square;
    private int key;
    private boolean ability;
    private int timer;
    private boolean coolDown;
    private boolean isPlant;

    // SETTERS
    public void setCoolDown(boolean coolDown) {
        this.coolDown = coolDown;
    }
    public void setTimer(int timer) {
        this.timer = timer;
    }
    public void setAbility(boolean ability) {
        this.ability = ability;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public void setPos_x_prev(int pos_x_prev) {
        this.pos_x_prev = pos_x_prev;
    }
    public void setPos_y_prev(int pos_y_prev) {
        this.pos_y_prev = pos_y_prev;
    }
    public void setCollided(boolean collided) {
        this.collided = collided;
    }
    public void setIdx(int idx) {
        this.idx = idx;
    }
    public void setPower(int power) {
        this.power = power;
    }
    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }
    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }
    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsPlant(boolean value) {
        this.isPlant = value;
    }

    // GETTERS
    public boolean getIsPlant() {
        return this.isPlant;
    }

    public boolean getCoolDown() {
        return coolDown;
    }
    public int getTimer() {
        return timer;
    }
    public boolean getAbility() {
        return ability;
    }
    public int getKey() {
        return key;
    }
    public int getPos_x_prev() {
        return pos_x_prev;
    }
    public int getPos_y_prev() {
        return pos_y_prev;
    }
    public boolean getCollided() {
        return collided;
    }

    public int getPos_x() {
        return pos_x;
    }
    public int getIdx() {
        return idx;
    }

    public int getPos_y() {
        return pos_y;
    }
    public int getPower() {
        return power;
    }
    public int getInitiative() {
        return initiative;
    }

    public Size getSize() {
        return size;
    }

    public Game getGame() {
        return game;
    }
    public Rectangle getSquare() {
        return square;
    }
    public int getAge() {
        return age;
    }
    public String getName() {
        return name;
    }

    // CONSTRUCTOR
    public Organism(Game game, Size size) {
        this.pos_y = 0;
        this.pos_x = 0;
        this.power = 0;
        this.initiative = 0;
        this.game = game;
        this.size = size;
        this.square = new Rectangle(pos_x, pos_y, size.getWidth(), size.getHeight());
        this.name = "";
        this.collided = false;
        this.ability = false;
        this.timer = -1;
        this.coolDown = false;
        this.isPlant = false;
    }

    // FUNCTIONALITY
    public void generateRandomX(int[] x) {
        Random rand = new Random();
        int newX = (rand.nextInt(2) == 0) ? (x[0] + 10) : (x[0] -10);
        if (newX >= 0 && newX < game.getBoardWidth() - 5) {
            x[0] = newX;
        }
        else {
            generateRandomX(x);
        }
    }

    public void generateRandomY(int[] y) {
        Random rand = new Random();
        int newY = (rand.nextInt(2) == 0) ? (y[0] + 10) : (y[0] -10);
        if (newY >= 0 && newY < game.getBoardHeight() - 5) {
            y[0] = newY;
        }
        else {
            generateRandomY(y);
        }
    }

    public void generateRandomCoordinates(int[] x, int[] y) {
        Random rand = new Random();
        boolean modify_X_Y = rand.nextBoolean();

        if (modify_X_Y) {
            generateRandomX(x);
        }
        else {
            generateRandomY(y);
        }
    }

    public void spawnOrganisms(int[] idx, boolean isHuman) {
        Random rand = new Random();
        int numOfOrgs;

        if (isHuman) {
            numOfOrgs = 1;
        }
        else {
            numOfOrgs = rand.nextInt(2) + 2;
        }

        for (int i = 0; i < numOfOrgs; i++) {
            Random randPosX = new Random();
            int initPosX = (randPosX.nextInt((size.getWidth() - 5) / 10) + 1) * 10;
            Random randPosY = new Random();
            int initPosY = (randPosY.nextInt((size.getHeight() - 5) / 10) + 1) * 10;

            Organism newOrg = spawn();

            if (newOrg != null) {
                newOrg.setPos_x(initPosX);
                newOrg.setPos_y(initPosY);
                newOrg.setAge(1);
                newOrg.setIdx(idx[0]);
                ++(idx[0]);
                newOrg.getSquare().setLocation(initPosX, initPosY);
                newOrg.getSquare().setSize(size.getWidth(), size.getHeight());

                game.addOrganism(newOrg);
            }
        }
    }

    public abstract void drawOrg(Graphics g, int boardPosX, int boardPosY);

    public abstract void action(int[] x, int[] y, Vector<Organism> organismCopy);

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
                    else if (Objects.equals(other.getName(), "human")) {
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
            organismsCopy.remove(this);
            getGame().addMessage("<" + this.getName() + "> died of " + other.getName());
            this.setCollided(true);
            other.setCollided(true);
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
            organismsCopy.remove(other);
            getGame().addMessage("<" + other.getName() + "> died of " + this.getName());
            this.setCollided(true);
            other.setCollided(true);
        } else {
            spreadAnimals(other, organismsCopy);
        }
    }

    public void spreadAnimals(Organism other, Vector<Organism> organismsCopy) {
        if (!this.getCollided() && !other.getCollided() && !this.getIsPlant() && !other.getIsPlant()) {
            int[] a = new int[] {this.getPos_x()};
            int[] b = new int[] {this.getPos_y()};
            generateRandomCoordinates(a, b);
            int a_int = a[0];
            int b_int = b[0];
            Organism newOrg = spawn();

            if (newOrg != null) {
                newOrg.setPos_x(a_int);
                newOrg.setPos_y(b_int);
                newOrg.setAge(1);
                newOrg.setCollided(true);
                newOrg.setIdx(organismsCopy.size());
                newOrg.getSquare().setLocation(a_int, b_int);
                newOrg.getSquare().setSize(size.getWidth(), size.getHeight());
                organismsCopy.add(newOrg);

                getGame().addMessage("<simulation> TWO animals of type " + this.getName() + " have multiplied");
                this.setCollided(true);
                other.setCollided(true);
            }
        }
    }




    public Organism spawn() {
        if (this instanceof Wolf) {
            return new Wolf(game, size);
        }
        else if (this instanceof Sheep) {
            return new Sheep(game, size);
        }
        else if (this instanceof Fox) {
            return new Fox(game, size);
        }
        else if (this instanceof Turtle) {
            return new Turtle(game, size);
        }
        else if (this instanceof Antelope) {
            return new Antelope(game, size);
        }
        else if (this instanceof Grass) {
            return new Grass(game, size);
        }
        else if (this instanceof Dandelion) {
            return new Dandelion(game, size);
        }
        else if (this instanceof Guarana) {
            return new Guarana(game, size);
        }
        else if (this instanceof Nightshade) {
            return new Nightshade(game, size);
        }
        else if (this instanceof Hogweed) {
            return new Hogweed(game, size);
        }
        else if (this instanceof Human) {
            return new Human(game, size);
        }
        else {
            return null;
        }
    }

}
