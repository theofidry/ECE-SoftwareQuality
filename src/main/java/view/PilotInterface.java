/*
 * Created by JFormDesigner on Sun Dec 07 15:02:43 CET 2014
 */

package view;

import model.Plane;
import model.Software;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
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
        lightsPanel.add(new LightsPanel(this.plane.getLights()));

        // Doors
        doorsPanel.add(new DoorPanel(this.plane.getDoors()[0], "Front Door"));
        doorsPanel.add(new DoorPanel(this.plane.getDoors()[1], "Left Door"));
        doorsPanel.add(new DoorPanel(this.plane.getDoors()[2], "Right Door"));

        // Gears
        landingGearsPanel.add(new LandingGearPanel(this.plane.getLandingGears()[0], "Front Gear"));
        landingGearsPanel.add(new LandingGearPanel(this.plane.getLandingGears()[1], "Left Gear"));
        landingGearsPanel.add(new LandingGearPanel(this.plane.getLandingGears()[2], "Right Gear"));

        // Handle
        this.add(new HandlePanel(plane.getHandle(), new HandleChangeListener()));

        /*
        TODO:
        - if and only if all gears are in deployed state -> gears locked down true displayed
        - gears manoeuvring: if and only if at least one door or one gear is maneuvering, i.e., at least one door is not locked in closed position or one gear is not locked in extension or retraction position.
         */

    }

    public JPanel getDoorsPanel() {
        return doorsPanel;
    }

    public JPanel getLandingGearsPanel() {
        return landingGearsPanel;
    }

    public JPanel getHandlePanel() {
        return handlePanel;
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
        doorsPanel = new JPanel();
        vSpacer2 = new JPanel(null);
        DoorsPanelTitle = new JLabel();
        vSpacer4 = new JPanel(null);
        landingGearsPanel = new JPanel();
        vSpacer1 = new JPanel(null);
        separator1 = new JSeparator();
        vSpacer3 = new JPanel(null);
        GearsPanelTitle = new JLabel();
        vSpacer5 = new JPanel(null);
        FrontGearPanel = new JPanel();
        LeftGearPanel = new JPanel();
        RightGearPanel = new JPanel();
        hSpacer2 = new JPanel(null);
        lightsPanel = new JPanel();
        hSpacer1 = new JPanel(null);
        handlePanel = new JPanel();

        //======== this ========

        // JFormDesigner evaluation mark
        setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                        "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                        java.awt.Color.red), getBorder()));
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                if ("border".equals(e.getPropertyName())) throw new RuntimeException();
            }
        });

        setLayout(new FlowLayout());

        //======== panel2 ========
        {
            panel2.setLayout(new BorderLayout());

            //======== panel1 ========
            {
                panel1.setLayout(new VerticalLayout());

                //======== doorsPanel ========
                {
                    doorsPanel.setLayout(new VerticalLayout());
                    doorsPanel.add(vSpacer2);

                    //---- DoorsPanelTitle ----
                    DoorsPanelTitle.setText("Doors");
                    DoorsPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
                    doorsPanel.add(DoorsPanelTitle);
                    doorsPanel.add(vSpacer4);
                }
                panel1.add(doorsPanel);

                //======== landingGearsPanel ========
                {
                    landingGearsPanel.setLayout(new VerticalLayout());
                    landingGearsPanel.add(vSpacer1);
                    landingGearsPanel.add(separator1);
                    landingGearsPanel.add(vSpacer3);

                    //---- GearsPanelTitle ----
                    GearsPanelTitle.setText("Landing Gears");
                    GearsPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
                    landingGearsPanel.add(GearsPanelTitle);
                    landingGearsPanel.add(vSpacer5);

                    //======== FrontGearPanel ========
                    {
                        FrontGearPanel.setLayout(new GridLayout());
                    }
                    landingGearsPanel.add(FrontGearPanel);

                    //======== LeftGearPanel ========
                    {
                        LeftGearPanel.setLayout(new GridLayout());
                    }
                    landingGearsPanel.add(LeftGearPanel);

                    //======== RightGearPanel ========
                    {
                        RightGearPanel.setLayout(new GridLayout());
                    }
                    landingGearsPanel.add(RightGearPanel);
                }
                panel1.add(landingGearsPanel);
            }
            panel2.add(panel1, BorderLayout.CENTER);
        }
        add(panel2);
        add(hSpacer2);

        //======== lightsPanel ========
        {
            lightsPanel.setLayout(new VerticalLayout());
        }
        add(lightsPanel);
        add(hSpacer1);

        //======== handlePanel ========
        {
            handlePanel.setLayout(new BorderLayout());
        }
        add(handlePanel);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - ThÃ©o Fidry
    private JPanel panel2;
    private JPanel panel1;
    private JPanel doorsPanel;
    private JPanel vSpacer2;
    private JLabel DoorsPanelTitle;
    private JPanel vSpacer4;
    private JPanel landingGearsPanel;
    private JPanel vSpacer1;
    private JSeparator separator1;
    private JPanel vSpacer3;
    private JLabel GearsPanelTitle;
    private JPanel vSpacer5;
    private JPanel FrontGearPanel;
    private JPanel LeftGearPanel;
    private JPanel RightGearPanel;
    private JPanel hSpacer2;
    private JPanel lightsPanel;
    private JPanel hSpacer1;
    private JPanel handlePanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
