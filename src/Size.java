import javax.swing.*;
import java.awt.*;

public class Size {
    private int width = 200;
    private int height = 200;
    private JTextField field1;
    private JTextField field2;

    public void createWindow() {
        JFrame frame = createFrame();
        JPanel panel = createPanel();
        JPanel buttonPanel = createButtonPanel(frame);
        addComponentsToFrame(frame, panel, buttonPanel);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("Choose Board Size");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        return frame;
    }

    private JPanel createPanel() {
        field1 = new JTextField(10);
        field2 = new JTextField(10);

        JLabel label1 = new JLabel("Width:");
        JLabel label2 = new JLabel("Height:");

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(label1);
        panel.add(field1);
        panel.add(label2);
        panel.add(field2);

        label1.setHorizontalAlignment(JLabel.CENTER);
        field1.setHorizontalAlignment(JTextField.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        field2.setHorizontalAlignment(JTextField.CENTER);

        return panel;
    }

    private JPanel createButtonPanel(JFrame frame) {
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            try {
                width = Integer.parseInt(field1.getText());
                height = Integer.parseInt(field2.getText());
                JOptionPane.showMessageDialog(null, "Setting board size to " + width + "x" + height + "..");
                frame.setVisible(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid values.");
            }
        });

        JButton cancel = new JButton("Default");
        cancel.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Setting the default size 20x20..");
            frame.setVisible(false);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submit);
        buttonPanel.add(cancel);
        return buttonPanel;
    }

    private void addComponentsToFrame(JFrame frame, JPanel panel, JPanel buttonPanel) {
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int value) {
        this.width = value;
    }

    public void setHeight(int value) {
        this.height = value;
    }
}
