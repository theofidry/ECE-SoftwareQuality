/*
 * Created by JFormDesigner on Sun Dec 07 15:02:43 CET 2014
 */

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
 * @author unknown
 */
public class PilotInterface extends JPanel {

    /**
     * Inertia of the handle in ms (simulation of the handle -> switch).
     */
    public static final int HANDLE_INERTIA = 1000;
    private java.util.Timer timer = new java.util.Timer();


    Plane plane;


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

    public JPanel getDoorsPanel() {
        return doorsContainer;
    }

    public JPanel getLandingGearsPanel() {
        return landingGearsContainer;
    }

    public HandlePanel getHandlePanel() {
        return (HandlePanel) handleContainer.getComponent(0);
    }

    /**
     * When the slider changes of value, process the software.
     */
    private class HandleChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent evt) {

            JSlider source = (JSlider) evt.getSource();

            if (!source.getValueIsAdjusting()) {

                if (source.getValue() == 1) {
                    plane.getHandle().pushUp();
                } else {
                    plane.getHandle().pushDown();
                }

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        plane.getSoftware().process();
                    }
                }, HANDLE_INERTIA);
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - ThÃ©o Fidry
        panel2 = new JPanel();
        panel1 = new JPanel();
        doorsContainer = new JPanel();
        vSpacer2 = new JPanel(null);
        DoorsPanelTitle = new JLabel();
        vSpacer4 = new JPanel(null);
        landingGearsContainer = new JPanel();
        vSpacer1 = new JPanel(null);
        separator1 = new JSeparator();
        vSpacer3 = new JPanel(null);
        GearsPanelTitle = new JLabel();
        vSpacer5 = new JPanel(null);
        FrontGearPanel = new JPanel();
        LeftGearPanel = new JPanel();
        RightGearPanel = new JPanel();
        hSpacer2 = new JPanel(null);
        lightsContainer = new JPanel();
        hSpacer1 = new JPanel(null);
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

                    //---- DoorsPanelTitle ----
                    DoorsPanelTitle.setText("Doors");
                    DoorsPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
                    doorsContainer.add(DoorsPanelTitle);
                    doorsContainer.add(vSpacer4);
                }
                panel1.add(doorsContainer);

                //======== landingGearsContainer ========
                {
                    landingGearsContainer.setLayout(new VerticalLayout());
                    landingGearsContainer.add(vSpacer1);
                    landingGearsContainer.add(separator1);
                    landingGearsContainer.add(vSpacer3);

                    //---- GearsPanelTitle ----
                    GearsPanelTitle.setText("Landing Gears");
                    GearsPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
                    landingGearsContainer.add(GearsPanelTitle);
                    landingGearsContainer.add(vSpacer5);

                    //======== FrontGearPanel ========
                    {
                        FrontGearPanel.setLayout(new GridLayout());
                    }
                    landingGearsContainer.add(FrontGearPanel);

                    //======== LeftGearPanel ========
                    {
                        LeftGearPanel.setLayout(new GridLayout());
                    }
                    landingGearsContainer.add(LeftGearPanel);

                    //======== RightGearPanel ========
                    {
                        RightGearPanel.setLayout(new GridLayout());
                    }
                    landingGearsContainer.add(RightGearPanel);
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
    private JPanel panel2;
    private JPanel panel1;
    private JPanel doorsContainer;
    private JPanel vSpacer2;
    private JLabel DoorsPanelTitle;
    private JPanel vSpacer4;
    private JPanel landingGearsContainer;
    private JPanel vSpacer1;
    private JSeparator separator1;
    private JPanel vSpacer3;
    private JLabel GearsPanelTitle;
    private JPanel vSpacer5;
    private JPanel FrontGearPanel;
    private JPanel LeftGearPanel;
    private JPanel RightGearPanel;
    private JPanel hSpacer2;
    private JPanel lightsContainer;
    private JPanel hSpacer1;
    private JPanel handleContainer;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
