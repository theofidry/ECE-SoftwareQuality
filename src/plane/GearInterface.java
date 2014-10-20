package plane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GearInterface {

    private JPanel panel1;
    private JRadioButton REDRadioButton;
    private JRadioButton ORANGERadioButton;
    private JRadioButton GREENRadioButton;
    private JRadioButton UPRadioButton;
    private JRadioButton DOWNRadioButton;
    private JRadioButton MOVINGRadioButton;
    private JButton upButton;
    private JButton downButton;

    public Plane plane = new Plane();

    public static final int WAIT1 = 2000;
    public static final int WAIT2 = 3000;

    private Thread threadUp   = new Thread(),
                   threadDown = new Thread();

    public GearInterface() {

        upButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    wait(WAIT1);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                plane.process();

                //TODO: wait 3sec
                //TODO: update the view
            }
        });
        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //TODO
            }
        });
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Interface");
        frame.setContentPane(new GearInterface().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
    }


}
