package controller;

import model.Plane;
import view.PilotInterface;

import javax.swing.JFrame;

/**
 * Main class; Used to start the program.
 */
public final class Main {

    /**
     * Disallow instantiation of this class.
     */
    private Main() {}

    /**
     * Start the program.
     *
     * @param args arguments
     */
    public static void main(String[] args) {

        // Instantiate a frame and add a PilotInterface in it
        JFrame frame = new JFrame("Pilot Interface");
        frame.setContentPane(new PilotInterface(new Plane()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
