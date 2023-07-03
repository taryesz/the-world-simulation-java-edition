import javax.swing.*;
import java.awt.*;

public class Menu {

    private final Size size;
    private final Game game;
    private JButton start;
    private JButton load;
    private JButton sizeBtn;
    private JPanel panel;

    public Menu(Game game, Size size) {
        createButtons();
        createPanel();
        createFrame();
        this.game = game;
        this.size = size;
    }

    private void createButtons() {
        start = new JButton("Play");
        load = new JButton("Load Game");
        sizeBtn = new JButton("Choose Board Size");

        start.setPreferredSize(new Dimension(200, 50));
        load.setPreferredSize(new Dimension(200, 50));
        sizeBtn.setPreferredSize(new Dimension(200, 50));

        start.addActionListener(e -> {
            int[] idx = new int[] {0};

            Organism fox = new Fox(game, size);
            fox.spawnOrganisms(idx, false);

            Organism wolf = new Wolf(game, size);
            wolf.spawnOrganisms(idx, false);

            Organism human = new Human(game, size);
            human.spawnOrganisms(idx, true);

            Organism sheep = new Sheep(game, size);
            sheep.spawnOrganisms(idx, false);

            Organism antelope = new Antelope(game, size);
            antelope.spawnOrganisms(idx, false);

            Organism turtle = new Turtle(game, size);
            turtle.spawnOrganisms(idx, false);

            Organism grass = new Grass(game, size);
            grass.spawnOrganisms(idx, false);

            Organism dandelion = new Dandelion(game, size);
            dandelion.spawnOrganisms(idx, false);

            Organism guarana = new Guarana(game, size);
            guarana.spawnOrganisms(idx, false);

            Organism nightshade = new Nightshade(game, size);
            nightshade.spawnOrganisms(idx, false);

            Organism hogweed = new Hogweed(game, size);
            hogweed.spawnOrganisms(idx, false);

            game.createWindow();
            game.createMessages();

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(start);
            frame.dispose();
        });
        load.addActionListener(e -> game.loadGame());
        sizeBtn.addActionListener(e -> size.createWindow());
    }

    private void createPanel() {
        panel = new JPanel(new GridLayout(3, 1));
        panel.add(start);
        panel.add(load);
        panel.add(sizeBtn);
    }

    private void createFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("The World Simulation");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
    }
}
