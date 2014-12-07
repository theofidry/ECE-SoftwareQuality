package controller;


import model.Door;
import model.Plane;
import view.PilotInterface;
import view.DoorPanel;

import javax.swing.JFrame;

/**
 * Main class; Used to start the program.
 */
public class Main {

    /**
     * Start the program.
     *
     * @param args arguments
     */
    public static void main(String[] args) {

        JFrame frame = new JFrame("Pilot Interface");
        frame.setContentPane(new PilotInterface(new Plane()));
        frame.getContentPane().add(new DoorPanel(new Door(), "Test door"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
