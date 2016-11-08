package view;


import javax.swing.*;
import java.awt.*;

public class TCView {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel panel;
    private JButton btn;
    private JPanel progPanel;
    private JProgressBar progressBar;
    private JTextArea area;
    private JScrollPane spane;
    private JLabel blogLabel;
    private JLabel tumblrLabel;
    private JTextField blogText;

    public TCView() {
        mainFrame = null;
        mainPanel = null;
        panel = null;
        btn = null;
        progPanel = null;
        progressBar = null;
        area = null;
        spane = null;
    }

    public void start() {
        drawWindow();
    }

    private void drawWindow() {
        mainFrame = new JFrame();
        mainFrame.setTitle("Tumblr Collecter");
        mainFrame.setSize(400, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        drawPanels();

        mainFrame.validate();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void drawPanels() {
        // Initialize panels
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        panel = new JPanel();
        panel.setLayout(null);
        progPanel = new JPanel();
        progPanel.setLayout(null);

        drawComponents();

        // Add panels into grid's main panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.53;
        mainPanel.add(panel, gbc);
        gbc.gridy = 1;
        gbc.weighty = 0.12;
        mainPanel.add(progPanel, gbc);
        gbc.gridy = 2;
        gbc.weighty = 0.35;
        mainPanel.add(spane, gbc);

        mainFrame.add(mainPanel);
    }

    private void drawComponents() {
        // TOP PANEL
        blogLabel = new JLabel("Tumblr blog");
        blogLabel.setBounds(30, 30, 110, 25);

        blogText = new JTextField(30);
        blogText.setBounds(120, 30, 160, 25);

        tumblrLabel = new JLabel(".tumblr.com");
        tumblrLabel.setBounds(285, 30, 120, 25);

        btn = new JButton("Download");
        btn.setBounds(145, 75, 110, 25);

        // CENTER PANEL
        progressBar = new JProgressBar();
        progressBar.setBackground(Color.WHITE);
        progressBar.setForeground(Color.GREEN);
        progressBar.setBounds(5, 0, 383, 12);

        // BOTTOM PANEL
        area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setBackground(Color.BLACK);
        area.setForeground(Color.GREEN);
        spane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        spane.setViewportBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // ADD COMPONENTS INTO PANELS
        panel.add(blogLabel);
        panel.add(blogText);
        panel.add(tumblrLabel);
        panel.add(btn);
        progPanel.add(progressBar);
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

    public void initProgressBar(int min, int max) {
        progressBar.setMinimum(min);
        progressBar.setMaximum(max);
        progressBar.setValue(min);
    }

    public void setProgressBar(int value) {
        progressBar.setValue(value);
    }

    public void updateProgressBar() {
        int updatedPos = progressBar.getValue() + 1;
        progressBar.setValue(updatedPos);
    }

    public void setTextIntoArea(String text) {
        this.area.setText(text);
    }

    public void appendTextIntoArea(String text) {
        this.area.append(text);
        this.area.setCaretPosition(area.getDocument().getLength());
    }
}
