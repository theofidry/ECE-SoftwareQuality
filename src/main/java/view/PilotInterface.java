package view;

import model.Plane;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.TimerTask;

/**
 * Plane pilot interface.
 */
public class PilotInterface extends JPanel {

    /**
     * Inertia of the handle in ms (simulation of the handle -> switch).
     */
    public static final int HANDLE_INERTIA = 1000;

    /**
     * Timer used to make threaded actions with delays.
     */
    private java.util.Timer timer = new java.util.Timer();

    /**
     * Panel's model.
     */
    private Plane plane;

    /**
     * Get the handle panel.
     *
     * @return handle panel
     */
    public HandlePanel getHandlePanel() {
        return (HandlePanel) handleContainer.getComponent(0);
    }

    /**
     * Plane pilot interface.
     *
     * @param plane pass the plane model to the view
     */
    public PilotInterface(Plane plane) {

        this.plane = plane;

        // Form init
        initComponents();


        /*
         Customisation
          */
        // Lights
        lightsContainer.add(new LightsPanel(this.plane.getLights()));

        // Doors
        doorsContainer.add(new DoorPanel(this.plane.getDoors()[0], "Front Door"));
        doorsContainer.add(new DoorPanel(this.plane.getDoors()[1], "Left Door"));
        doorsContainer.add(new DoorPanel(this.plane.getDoors()[2], "Right Door"));

        // Gears
        landingGearsContainer.add(new LandingGearPanel(this.plane.getLandingGears()[0], "Front Gear"));
        landingGearsContainer.add(new LandingGearPanel(this.plane.getLandingGears()[1], "Left Gear"));
        landingGearsContainer.add(new LandingGearPanel(this.plane.getLandingGears()[2], "Right Gear"));

        // Handle
        handleContainer.add(new HandlePanel(this.plane.getHandle(), new HandleChangeListener()));

        /*
        TODO:
        - if and only if all gears are in deployed state -> gears locked down true displayed
        - gears manoeuvring: if and only if at least one door or one gear is maneuvering, i.e., at least one door is not locked in closed position or one gear is not locked in extension or retraction position.
         */

    }

    /**
     * When the slider changes of value, process the software.
     */
    private class HandleChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent evt) {

            JSlider source = (JSlider) evt.getSource();
            TimerTask task;

            if (!source.getValueIsAdjusting()) {

                if (source.getValue() == 1) {
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            plane.getHandle().pushUp();
                        }
                    };
                } else {
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            plane.getHandle().pushDown();
                        }
                    };
                }

                // Change the plane's handle with a little delay to simulate the time of transmission
                timer.schedule(task, HANDLE_INERTIA);
            }
        }
    }

    /**
     * JForm designer initializer.
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - ThÃ©o Fidry
        JPanel panel2 = new JPanel();
        panel1 = new JPanel();
        doorsContainer = new JPanel();
        JPanel vSpacer2 = new JPanel(null);
        JLabel doorsPanelTitle = new JLabel();
        JPanel vSpacer4 = new JPanel(null);
        landingGearsContainer = new JPanel();
        JPanel vSpacer1 = new JPanel(null);
        JSeparator separator = new JSeparator();
        JPanel vSpacer3 = new JPanel(null);
        JLabel gearsPanelTitle = new JLabel();
        JPanel vSpacer5 = new JPanel(null);
        JPanel hSpacer2 = new JPanel(null);
        lightsContainer = new JPanel();
        JPanel hSpacer1 = new JPanel(null);
        handleContainer = new JPanel();

        //======== this ========

        // JFormDesigner evaluation mark
        setBorder(new javax.swing.border.CompoundBorder(
            new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

        setLayout(new FlowLayout());

        //======== panel2 ========
        {
            panel2.setLayout(new BorderLayout());

            //======== panel1 ========
            {
                panel1.setLayout(new VerticalLayout());

                //======== doorsContainer ========
                {
                    doorsContainer.setLayout(new VerticalLayout());
                    doorsContainer.add(vSpacer2);

                    //---- doorsPanelTitle ----
                    doorsPanelTitle.setText("Doors");
                    doorsPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
                    doorsContainer.add(doorsPanelTitle);
                    doorsContainer.add(vSpacer4);
                }
                panel1.add(doorsContainer);

                //======== landingGearsContainer ========
                {
                    landingGearsContainer.setLayout(new VerticalLayout());
                    landingGearsContainer.add(vSpacer1);
                    landingGearsContainer.add(separator);
                    landingGearsContainer.add(vSpacer3);

                    //---- gearsPanelTitle ----
                    gearsPanelTitle.setText("Landing Gears");
                    gearsPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
                    landingGearsContainer.add(gearsPanelTitle);
                    landingGearsContainer.add(vSpacer5);
                }
                panel1.add(landingGearsContainer);
            }
            panel2.add(panel1, BorderLayout.CENTER);
        }
        add(panel2);
        add(hSpacer2);

        //======== lightsContainer ========
        {
            lightsContainer.setLayout(new BorderLayout());
        }
        add(lightsContainer);
        add(hSpacer1);

        //======== handleContainer ========
        {
            handleContainer.setLayout(new BorderLayout());
        }
        add(handleContainer);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - ThÃ©o Fidry
    private JPanel panel1;
    private JPanel doorsContainer;
    private JPanel landingGearsContainer;
    private JPanel lightsContainer;
    private JPanel handleContainer;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
