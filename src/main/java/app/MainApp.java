package app;


import controller.TCController;

public class MainApp {

    public static void main (String [] args) {

        TCController controller = new TCController();
        controller.start();
    }
}
