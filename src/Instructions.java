import javax.swing.*;
import java.awt.*;

public class Instructions {

    private JPanel panel;

    public Instructions() {
        createPanel();
        createText();
        createFrame();
    }

    private void createText() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setText("Welcome to The World Simulation game!\nThis is a game where you move around the world trying to survive and not get killed by stronger animals.\n\nSpeaking about animals, sheep, antelopes and turtle are not going to attack you. Foxes will even escape you, but... look out for wolves and animals that have already eaten guarana (you don't wanna get killed by a sheep, do you?!\n\nIn addition to animals, there are also passive plants: grass, dandelions. There are plants that you wanna avoid: nightshade and Sosnowski's hogweed. And there is a plant that you definitely want to eat - guarana: it gives you +3 to your strength!\n\nList of markings:\n\nred - wolf\nyellow - fox\nblue - turtle\ngreen - sheep\npink - antelope\nblack - nightshade\ncyan - grass\ndark gray - dandelion\nlight gray - Sosnowski's hogweed\nmagenta - guarana\n\nThat's all from me, have a nice game!\n");
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(280, 400));
        panel.add(scrollPane);
        //panel.setPreferredSize(new Dimension(400, 400));
    }

    private void createPanel() {
        panel = new JPanel();
    }

    private void createFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("How to play");
        frame.setSize(300, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
    }
}
