package app;


import controller.TCController;
import view.TCView;

import javax.swing.*;

public class MainApp {

    public static void main (String [] args) {
        prepareGUI();
    }

    private static void prepareGUI() {
        SwingUtilities.invokeLater(() -> {
            TCView view = new TCView();
            view.start();
            TCController controller = new TCController(view);
            controller.control();
        });
    }
}
