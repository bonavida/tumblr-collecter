package view;


import javax.swing.*;
import java.awt.*;

public class TCView {
    private JFrame mainFrame;
    private JPanel panel;
    private JButton btn;
    private JTextArea area;
    private JScrollPane spane;
    private JLabel blogLabel;
    private JLabel tumblrLabel;
    private JTextField blogText;

    public TCView() {
        mainFrame = null;
        panel = null;
        btn = null;
        area = null;
        spane = null;
    }

    public void start() {
        drawWindow();
    }

    private void drawWindow() {
        mainFrame = new JFrame();
        mainFrame.setLayout(new GridLayout(2, 1));
        mainFrame.setTitle("Tumblr Collecter");
        mainFrame.setSize(400, 275);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        drawPanel();
        mainFrame.validate();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void drawPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        drawComponents();
        mainFrame.add(panel);
        mainFrame.add(spane);
    }

    private void drawComponents() {
        blogLabel = new JLabel("Tumblr blog");
        blogLabel.setBounds(30, 30, 80, 25);

        blogText = new JTextField(30);
        blogText.setBounds(120, 30, 160, 25);

        tumblrLabel = new JLabel(".tumblr.com");
        tumblrLabel.setBounds(285, 30, 90, 25);

        btn = new JButton("Download");
        btn.setBounds(150, 75, 100, 25);

        area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setBackground(Color.BLACK);
        area.setForeground(Color.GREEN);
        spane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        spane.setViewportBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        panel.add(blogLabel);
        panel.add(blogText);
        panel.add(tumblrLabel);
        panel.add(btn);
    }

    public void setTextToVoid() {
        area.setText("");
        blogText.setText("");
        btn.setEnabled(false);
    }

    public JButton getButton() {
        return this.btn;
    }

    public JTextField getBlogText() {
        return this.blogText;
    }

    public void setTextIntoArea(String text) {
        this.area.setText(text);
    }

    public void appendTextIntoArea(String text) {
        this.area.append(text);
        this.area.setCaretPosition(area.getDocument().getLength());
    }
}
