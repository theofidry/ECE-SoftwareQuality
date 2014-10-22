import plane.Plane;

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

    public static final double SLEEPTIME_1 = 1e9;
    public static final double SLEEPTIME_2 = 1e9;


    public GearInterface() {

        GearInterface instance = this;

        upButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                plane.handle.up = true;     // correct the plane handle position

                // wait a moment before taking action
                for (int i = 0; i < SLEEPTIME_1; i++) {
                    // do nothing
                }

                plane.process();            // process the plane software
                updateView();               // update the view

                // wait a moment before taking action
                for (int i = 0; i < SLEEPTIME_2; i++) {
                    // do nothing
                }

                plane.process();            // process the plane software
                updateView();               // update the view
            }
        });

        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                plane.handle.up = false;    // correct the plane handle position

                // wait a moment before taking action
                for (int i = 0; i < SLEEPTIME_1; i++) {
                    // do nothing
                }

                plane.process();            // process the plane software
                updateView();               // update the view

                // wait a moment before taking action
                for (int i = 0; i < SLEEPTIME_2; i++) {
                    // do nothing
                }

                plane.process();            // process the plane software
                updateView();               // update the view
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


    //
    // PUBLIC HELPERS
    //
    /**
     * Upate the view according to the plane current state.
     */
    public void updateView() {

        // update the light

        // turn off all lights
        turnOffLights();

        // turn on the proper light
        switch (plane.lights.color) {

            case GREEN:
                setButtonState(GREENRadioButton, true);
                break;

            case ORANGE:
                setButtonState(ORANGERadioButton, true);
                break;

            case RED:
                setButtonState(REDRadioButton, true);
                break;

            default:
                // case OFF
                // nothing to do since all lights are already turned off
        }


        // update the wheels

        // turn off all lights
        turnOffWheelsLights();

        // turn on the proper light
        switch (plane.wheels.position) {

            case UP:
                setButtonState(UPRadioButton, true);
                break;

            case DOWN:
                setButtonState(DOWNRadioButton, true);
                break;

            case MOVING:
                setButtonState(MOVINGRadioButton, true);
                break;

            default:
                setButtonState(UPRadioButton, true);
        }
    }


    //
    // PRIVATE HELPERS
    //
    /**
     * Helper to turn off all lights
     */
    private void turnOffLights() {

        JRadioButton[] lightsButton = {GREENRadioButton, ORANGERadioButton, REDRadioButton};

        for (JRadioButton lightButton: lightsButton) {
            setButtonState(lightButton, false);
        }
    }

    /**
     * Helper to turn on or off a given button.<br />
     * <br />
     * A turned on button is enabled and selected.<br />
     * A turn off button is disabled and unselected.
     *
     * @param lightButton
     * @param enabled Turn the light on if is true, off otherwise.
     */
    private void setButtonState(JRadioButton lightButton, boolean enabled) {

        lightButton.setEnabled(enabled);
        lightButton.setSelected(enabled);
    }

    /**
     * Turn off all the lights of the wheels.
     */
    private void turnOffWheelsLights() {

        JRadioButton[] wheelsLight = {UPRadioButton, MOVINGRadioButton, DOWNRadioButton};

        for (JRadioButton wheelLightButton: wheelsLight) {
            setButtonState(wheelLightButton, false);
        }
    }
}
