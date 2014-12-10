/*
 * Created by JFormDesigner on Sun Dec 07 15:02:43 CET 2014
 */

package view;

import model.Plane;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * @author unknown
 */
public class PilotInterface extends JPanel {


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
        // Doors
        DoorsPanel.add(new DoorPanel(this.plane.getDoors()[0], "Front Door"));
        DoorsPanel.add(new DoorPanel(this.plane.getDoors()[1], "Left Door"));
        DoorsPanel.add(new DoorPanel(this.plane.getDoors()[2], "Right Door"));

        // Gears
        LandingGearsPanel.add(new LandingGearPanel(this.plane.getLandingGears()[0], "Front Gear"));
        LandingGearsPanel.add(new LandingGearPanel(this.plane.getLandingGears()[1], "Left Gear"));
        LandingGearsPanel.add(new LandingGearPanel(this.plane.getLandingGears()[2], "Right Gear"));

        // Handle
        this.add(new HandlePanel(plane.getHandle(), plane.getSoftware()));

        /*
        TODO:
        - if and only if all gears are in deployed state -> gears locked down true displayed
        - gears manoeuvring: if and only if at least one door or one gear is maneuvering, i.e., at least one door is not locked in closed position or one gear is not locked in extension or retraction position.
         */

    }

    private class HandleChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent evt) {

            JSlider source = (JSlider) evt.getSource();

            if (!source.getValueIsAdjusting()) {

                if (source.getValue() == 1) {
                    //TODO: need to be replaced
                    plane.getSoftware().openDoors();
                } else {
                    //TODO: need to be replaced
                    plane.getSoftware().closeDoors();
                }
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - ThÃ©o Fidry
        panel2 = new JPanel();
        panel1 = new JPanel();
        DoorsPanel = new JPanel();
        vSpacer2 = new JPanel(null);
        DoorsPanelTitle = new JLabel();
        vSpacer4 = new JPanel(null);
        LandingGearsPanel = new JPanel();
        vSpacer1 = new JPanel(null);
        separator1 = new JSeparator();
        vSpacer3 = new JPanel(null);
        GearsPanelTitle = new JLabel();
        vSpacer5 = new JPanel(null);
        FrontGearPanel = new JPanel();
        FrontGearTitle = new JLabel();
        FrontGearTextFied = new JTextField();
        LeftGearPanel = new JPanel();
        LeftGearTitle = new JLabel();
        LeftGearTextField = new JTextField();
        RightGearPanel = new JPanel();
        RightGearTitle = new JLabel();
        RightGearTextField = new JTextField();
        hSpacer2 = new JPanel(null);
        panel3 = new JPanel();
        LightsTitle = new JLabel();
        LightsState = new JTextField();
        hSpacer1 = new JPanel(null);
        HandlePanel = new JPanel();

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

                //======== DoorsPanel ========
                {
                    DoorsPanel.setLayout(new VerticalLayout());
                    DoorsPanel.add(vSpacer2);

                    //---- DoorsPanelTitle ----
                    DoorsPanelTitle.setText("Doors");
                    DoorsPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
                    DoorsPanel.add(DoorsPanelTitle);
                    DoorsPanel.add(vSpacer4);
                }
                panel1.add(DoorsPanel);

                //======== LandingGearsPanel ========
                {
                    LandingGearsPanel.setLayout(new VerticalLayout());
                    LandingGearsPanel.add(vSpacer1);
                    LandingGearsPanel.add(separator1);
                    LandingGearsPanel.add(vSpacer3);

                    //---- GearsPanelTitle ----
                    GearsPanelTitle.setText("Landing Gears");
                    GearsPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
                    LandingGearsPanel.add(GearsPanelTitle);
                    LandingGearsPanel.add(vSpacer5);

//                    //======== FrontGearPanel ========
//                    {
//                        FrontGearPanel.setLayout(new GridLayout());
//
//                        //---- FrontGearTitle ----
//                        FrontGearTitle.setText("Front Gear");
//                        FrontGearTitle.setHorizontalAlignment(SwingConstants.LEFT);
//                        FrontGearTitle.setLabelFor(FrontGearTextFied);
//                        FrontGearPanel.add(FrontGearTitle);
//
//                        //---- FrontGearTextFied ----
//                        FrontGearTextFied.setText("Closed");
//                        FrontGearTextFied.setEditable(false);
//                        FrontGearPanel.add(FrontGearTextFied);
//                    }
//                    LandingGearsPanel.add(FrontGearPanel);
//
//                    //======== LeftGearPanel ========
//                    {
//                        LeftGearPanel.setLayout(new GridLayout());
//
//                        //---- LeftGearTitle ----
//                        LeftGearTitle.setText("Left Gear");
//                        LeftGearTitle.setHorizontalAlignment(SwingConstants.LEFT);
//                        LeftGearTitle.setLabelFor(LeftGearTextField);
//                        LeftGearPanel.add(LeftGearTitle);
//
//                        //---- LeftGearTextField ----
//                        LeftGearTextField.setText("Closed");
//                        LeftGearTextField.setEditable(false);
//                        LeftGearPanel.add(LeftGearTextField);
//                    }
//                    LandingGearsPanel.add(LeftGearPanel);
//
//                    //======== RightGearPanel ========
//                    {
//                        RightGearPanel.setLayout(new GridLayout());
//
//                        //---- RightGearTitle ----
//                        RightGearTitle.setText("Right Gear");
//                        RightGearTitle.setLabelFor(RightGearTextField);
//                        RightGearPanel.add(RightGearTitle);
//
//                        //---- RightGearTextField ----
//                        RightGearTextField.setText("Closed");
//                        RightGearTextField.setEditable(false);
//                        RightGearPanel.add(RightGearTextField);
//                    }
//                    LandingGearsPanel.add(RightGearPanel);
                }
                panel1.add(LandingGearsPanel);
            }
            panel2.add(panel1, BorderLayout.CENTER);
        }
        add(panel2);
        add(hSpacer2);

        //======== panel3 ========
        {
            panel3.setLayout(new VerticalLayout());

            //---- LightsTitle ----
            LightsTitle.setText("Lights");
            LightsTitle.setLabelFor(LightsState);
            panel3.add(LightsTitle);

            //---- LightsState ----
            LightsState.setText("Off");
            LightsState.setEditable(false);
            panel3.add(LightsState);
        }
        add(panel3);
        add(hSpacer1);

        //======== HandlePanel ========
        {
            HandlePanel.setLayout(new BorderLayout());
        }
        add(HandlePanel);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - ThÃ©o Fidry
    private JPanel panel2;
    private JPanel panel1;
    private JPanel DoorsPanel;
    private JPanel vSpacer2;
    private JLabel DoorsPanelTitle;
    private JPanel vSpacer4;
    private JPanel LandingGearsPanel;
    private JPanel vSpacer1;
    private JSeparator separator1;
    private JPanel vSpacer3;
    private JLabel GearsPanelTitle;
    private JPanel vSpacer5;
    private JPanel FrontGearPanel;
    private JLabel FrontGearTitle;
    private JTextField FrontGearTextFied;
    private JPanel LeftGearPanel;
    private JLabel LeftGearTitle;
    private JTextField LeftGearTextField;
    private JPanel RightGearPanel;
    private JLabel RightGearTitle;
    private JTextField RightGearTextField;
    private JPanel hSpacer2;
    private JPanel panel3;
    private JLabel LightsTitle;
    private JTextField LightsState;
    private JPanel hSpacer1;
    private JPanel HandlePanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
