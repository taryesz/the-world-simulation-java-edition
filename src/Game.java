import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;

public class Game extends JPanel implements KeyListener, ActionListener, MouseListener {

    private int boardWidth;
    private int boardHeight;
    private Size size;
    private Vector<Organism> organisms;
    private JTextArea messageArea;


    // SETTERS


    public void setSize(Size size) {
        this.size = size;
    }


    // GETTERS

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public Vector<Organism> getOrganisms() {
        return organisms;
    }

    // CONSTRUCTOR
    public Game(Size size) {
        this.boardWidth = size.getWidth();
        this.boardHeight = size.getHeight();
        this.size = size;
        this.organisms = new Vector<>();
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    // FUNCTIONALITY
    public void addMessage(String message) {
        messageArea.append(message + "\n");
    }
    public void createMessages() {
        JFrame messageFrame = new JFrame();
        messageFrame.setTitle("Messages");
        messageFrame.setPreferredSize(new Dimension(400,600));
        messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        messageFrame.setLocationRelativeTo(null);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        messageFrame.add(scrollPane);

        messageFrame.pack();
        messageFrame.setVisible(true);
    }

    public void createWindow() {
        initSize();

        JButton save = new JButton("Save Game And Exit");
        save.setPreferredSize(new Dimension(150, 25));
        save.addActionListener(e -> {
            saveGame();
            System.exit(0);
        });

        setPreferredSize(new Dimension(boardWidth + 10, boardHeight + 10));

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        bottomPanel.setBackground(Color.GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        bottomPanel.add(save, gbc);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(boardWidth + 10, boardHeight + 10));
        topPanel.add(this, BorderLayout.CENTER);

        JFrame frame = new JFrame();
        frame.setTitle("The World Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.add(topPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }
    public void initSize() {
        if (size != null) {
            boardWidth = size.getWidth();
            boardHeight = size.getHeight();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the board border
        int boardPosX = 5;
        int boardPosY = 5;
        g.drawRect(boardPosX, boardPosY, boardWidth, boardHeight);

        // Draw each organism
        for (Organism org : organisms) {
            org.drawOrg(g, boardPosX, boardPosY);
        }

        // Draw the messages
        g.setColor(Color.BLACK);
        for (Organism org : organisms) {
            if (org instanceof Wolf) {
                addMessage("<wolf> changing position to (" + (org.getPos_x() + boardPosX) + ", " + (org.getPos_y() + boardPosY) + ")");
            } else if (org instanceof Sheep) {
                addMessage("<sheep> changing position to (" + (org.getPos_x() + boardPosX) + ", " + (org.getPos_y() + boardPosY) + ")");
            } else if (org instanceof Fox) {
                addMessage("<fox> changing position to (" + (org.getPos_x() + boardPosX) + ", " + (org.getPos_y() + boardPosY) + ")");
            } else if (org instanceof Turtle) {
                addMessage("<turtle> changing position to (" + (org.getPos_x() + boardPosX) + ", " + (org.getPos_y() + boardPosY) + ")");
            } else if (org instanceof Antelope) {
                addMessage("<antelope> changing position to (" + (org.getPos_x() + boardPosX) + ", " + (org.getPos_y() + boardPosY) + ")");
            } else if (org instanceof Human) {
                addMessage("<player> changing position to (" + (org.getPos_x() + boardPosX) + ", " + (org.getPos_y() + boardPosY) + ")");
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        Vector<Organism> newOrganisms = new Vector<>(organisms.size());
        for (Organism org : organisms) {
            int age = org.getAge();
            org.setAge(++age);
            org.setPos_x_prev(org.getPos_x());
            org.setPos_y_prev(org.getPos_y());
            int[] x = new int[]{org.getPos_x()};
            int[] y = new int[]{org.getPos_y()};
            org.action(x, y, newOrganisms);
            org.setPos_x(x[0]);
            org.setPos_y(y[0]);
            newOrganisms.add(org);
        }
        for (Organism org : organisms) {
            org.collide(newOrganisms);
            org.setCollided(false);
        }
        organisms = newOrganisms;
        bubbleSort(organisms);
    }
    public void addOrganism(Organism org) {
        this.organisms.add(org);
    }
    public static void bubbleSort(Vector<Organism> organisms) {
        int n = organisms.size();
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                if (organisms.get(i).getInitiative() < organisms.get(i + 1).getInitiative()) {
                    Organism temp = organisms.get(i);
                    organisms.set(i, organisms.get(i + 1));
                    organisms.set(i + 1, temp);
                    swapped = true;
                } else if (organisms.get(i).getInitiative() == organisms.get(i + 1).getInitiative()) {
                    if (organisms.get(i).getAge() < organisms.get(i + 1).getAge()) {
                        Organism temp = organisms.get(i);
                        organisms.set(i, organisms.get(i + 1));
                        organisms.set(i + 1, temp);
                        swapped = true;
                    }
                }
            }
            n--;
        } while (swapped);
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN ||
                keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            for (Organism org : organisms) {
                switch (keyCode) {
                    case KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> {
                        org.setKey(keyCode);
                        if (org.getCoolDown()) {
                            int timer = org.getTimer();
                            org.setTimer(++timer);
                            if (org.getTimer() == 10) {
                                org.setTimer(0);
                                org.setCoolDown(false);
                                org.setAbility(false);
                                addMessage("<player> ability cooldown ended");
                            }
                        } else {
                            if (org.getAbility()) {
                                addMessage("<player> using ability");
                                org.setCoolDown(true);
                                org.setTimer(0);
                            }
                        }
                    }
                }
            }
            actionPerformed(null);
        } else if (keyCode == KeyEvent.VK_SPACE) {
            boolean humanFound = false;
            for (Organism org : organisms) {
                if (org instanceof Human) {
                    humanFound = true;
                    if (org.getCoolDown()) {
                        addMessage("<human> cooldown, cannot use ability");
                    } else {
                        addMessage("<player> ability activated for 5 turns");
                        org.setAbility(true);
                        org.setTimer(0);
                        org.setCoolDown(true);
                    }
                    break;
                }
            }
            if (!humanFound) {
                // ...
            }
        }
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
            int x = e.getX();
            int y = e.getY();
            int newX = x;
            int newY = y;

            newX = findNearestCoordinate(x);
            newY = findNearestCoordinate(y);

            createChooseWindow(newX, newY);
        }
    }

    private int findNearestCoordinate(int coordinate) {
        if (coordinate % 10 == 0) {
            return coordinate;
        } else {
            int lowerCoordinate = (coordinate / 10) * 10;
            int upperCoordinate = lowerCoordinate + 10;
            int lowerDistance = Math.abs(coordinate - lowerCoordinate);
            int upperDistance = Math.abs(coordinate - upperCoordinate);

            return (lowerDistance < upperDistance) ? lowerCoordinate : upperCoordinate;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public void createChooseWindow(int x, int y) {
        JFrame choiceFrame = new JFrame();
        choiceFrame.setTitle("Choose");
        choiceFrame.setPreferredSize(new Dimension(200, 300));
        choiceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        choiceFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JRadioButton wolfButton = new JRadioButton("Wolf");
        panel.add(wolfButton);

        JRadioButton sheepButton = new JRadioButton("Sheep");
        panel.add(sheepButton);

        JRadioButton foxButton = new JRadioButton("Fox");
        panel.add(foxButton);

        JRadioButton turtleButton = new JRadioButton("Turtle");
        panel.add(turtleButton);

        JRadioButton antelopeButton = new JRadioButton("Antelope");
        panel.add(antelopeButton);

        JRadioButton grassButton = new JRadioButton("Grass");
        panel.add(grassButton);

        JRadioButton dandelionButton = new JRadioButton("Dandelion");
        panel.add(dandelionButton);

        JRadioButton guaranaButton = new JRadioButton("Guarana");
        panel.add(guaranaButton);

        JRadioButton nightshadeButton = new JRadioButton("Nightshade");
        panel.add(nightshadeButton);

        JRadioButton hogweedButton = new JRadioButton("Sosnowski's hogweed");
        panel.add(hogweedButton);

        JButton submit = new JButton("Spawn");
        panel.add(submit);

        ButtonGroup group = new ButtonGroup();
        group.add(wolfButton);
        group.add(sheepButton);
        group.add(foxButton);
        group.add(turtleButton);
        group.add(antelopeButton);
        group.add(grassButton);
        group.add(dandelionButton);
        group.add(guaranaButton);
        group.add(nightshadeButton);
        group.add(hogweedButton);

        JPanel wrapper = new JPanel();
        wrapper.add(panel);

        choiceFrame.add(wrapper, BorderLayout.WEST);

        choiceFrame.pack();
        choiceFrame.setVisible(true);

        submit.addActionListener(e -> {
            Organism creature = null;

            if (wolfButton.isSelected()) {
                creature = new Wolf(this, size);
            } else if (sheepButton.isSelected()) {
                creature = new Sheep(this, size);
            } else if (foxButton.isSelected()) {
                creature = new Fox(this, size);
            } else if (turtleButton.isSelected()) {
                creature = new Turtle(this, size);
            } else if (antelopeButton.isSelected()) {
                creature = new Antelope(this, size);
            } else if (grassButton.isSelected()) {
                creature = new Grass(this, size);
            } else if (dandelionButton.isSelected()) {
                creature = new Dandelion(this, size);
            } else if (guaranaButton.isSelected()) {
                creature = new Guarana(this, size);
            } else if (nightshadeButton.isSelected()) {
                creature = new Nightshade(this, size);
            } else if (hogweedButton.isSelected()) {
                creature = new Hogweed(this, size);
            }

            if (creature != null) {
                System.out.println(x + ", " + y);
                creature.setPos_x(x);
                creature.setPos_y(y);
                creature.setAge(1);
                int idx = getOrganisms().size() + 1;
                creature.setIdx(idx);
                creature.getSquare().setLocation(x, y);
                creature.getSquare().setSize(size.getWidth(), size.getHeight());
                addMessage("<simulation> added " + creature.getName() + " at coords (" + creature.getPos_x() + ", " + creature.getPos_y() + ")");

                this.addOrganism(creature);
            }

            choiceFrame.dispose(); // Close the choose window

        });
    }
    public void saveGame() {
        try {
            FileWriter writer = new FileWriter("saved.txt");
            FileWriter worldWriter = new FileWriter("world.txt");
            int worldWidth = getSize().width;
            int worldHeight = getSize().height;
            worldWriter.write(worldWidth + " " + worldHeight + '\n');
            worldWriter.close();
            for (Organism org : organisms) {
                int x = org.getPos_x();
                int y = org.getPos_y();
                int age = org.getAge();
                int idx = org.getIdx();
                String name = org.getName();
                writer.write(name + " " + idx + " " + age + " " + x + " " + y + '\n');
            }
            addMessage("<simulation> saved game to file");
            writer.close();
        } catch (IOException e) {
            addMessage("<simulation> an error occured while trying to save the game to file");
            e.printStackTrace();
        }
    }
    public void loadGame() {
        this.organisms = new Vector<>();
        try {
            BufferedReader worldReader = new BufferedReader(new FileReader("world.txt"));
            String world = worldReader.readLine();
            if (world != null) {
                String[] sizes = world.split(" ");
                int worldWidth = Integer.parseInt(sizes[0]);
                int worldHeight = Integer.parseInt(sizes[1]);
                System.out.println(worldWidth + ", " + worldHeight);
                this.boardWidth = worldWidth - 10;
                this.boardHeight = worldHeight - 10;
                this.size.setWidth(boardWidth);
                this.size.setHeight(boardHeight);
                initSize();
            }
            worldReader.close();

            BufferedReader reader = new BufferedReader(new FileReader("saved.txt"));
            String line = reader.readLine(); // read the first line

            while (line != null) {
                // split the line into individual pieces of data
                String[] parts = line.split(" ");
                String name = parts[0];
                int idx = Integer.parseInt(parts[1]);
                int age = Integer.parseInt(parts[2]);
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);

                Organism newOrg = null;
                if (Objects.equals(name, "wolf")) {
                    newOrg = new Wolf(this, size);
                }
                else if (Objects.equals(name, "sheep")) {
                    newOrg = new Sheep(this, size);
                }
                else if (Objects.equals(name, "fox")) {
                    newOrg = new Fox(this, size);
                }
                else if (Objects.equals(name, "turtle")) {
                    newOrg = new Turtle(this, size);
                }
                else if (Objects.equals(name, "antelope")) {
                    newOrg = new Antelope(this, size);
                }
                else if (Objects.equals(name, "grass")) {
                    newOrg = new Grass(this, size);
                }
                else if (Objects.equals(name, "dandelion")) {
                    newOrg = new Dandelion(this, size);
                }
                else if (Objects.equals(name, "guarana")) {
                    newOrg = new Guarana(this, size);
                }
                else if (Objects.equals(name, "nightshade")) {
                    newOrg = new Nightshade(this, size);
                }
                else if (Objects.equals(name, "hogweed")) {
                    newOrg = new Hogweed(this, size);
                }
                else if (Objects.equals(name, "human")) {
                    newOrg = new Human(this, size);
                }

                if (newOrg != null) {
                    newOrg.setPos_x(x);
                    newOrg.setPos_y(y);
                    newOrg.setAge(age);
                    newOrg.setIdx(idx);
                    newOrg.getSquare().setLocation(x, y);
                    newOrg.getSquare().setSize(size.getWidth(), size.getHeight());

                    this.addOrganism(newOrg);
                }

                line = reader.readLine(); // read the next line
            }

            createWindow();
            createMessages();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
