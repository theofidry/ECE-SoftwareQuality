package view;


import model.Handle;
import model.Software;
import model.enums.DoorStateEnum;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class HandlePanel extends JPanel implements PropertyChangeListener {

    /**
     * Inertia of the handle in ms (simulation of the handle -> switch).
     */
    public static final int INERTIA = 1000;
    private java.util.Timer timer = new java.util.Timer();

    private Handle handle;
    private JLabel label = new JLabel();
    private JSlider slider = new JSlider();

    public HandlePanel(Handle handle, Software software) {

        this.handle = handle;

        //
        // Initialize components
        //
        this.setLayout(new BorderLayout());

        label.setText("Handle");
        label.setLabelFor(slider);
        this.add(label);

        slider.setOrientation(SwingConstants.VERTICAL);
        slider.setValue(0);
        slider.setMaximum(1);
        this.add(slider, BorderLayout.CENTER);

        //
        // Binds view to model and vice versa
        //
        slider.addChangeListener(new HandleChangeListener(software));
        handle.addPropertyChangeListener(this);
        modelToView(handle);
    }

    /**
     * {@inheritDoc}
     *
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        modelToView(handle);
    }

    private void modelToView(Handle handle) {

        if (handle.isUp())
            slider.setValue(1);
        else
            slider.setValue(0);
    }

    /**
     * When the slider changes of value, process the software.
     */
    private class HandleChangeListener implements ChangeListener {

        Software software;

        public HandleChangeListener(Software software) {
            this.software = software;
        }

        @Override
        public void stateChanged(ChangeEvent evt) {

            JSlider source = (JSlider) evt.getSource();

            if (!source.getValueIsAdjusting()) {

                if (source.getValue() == 1) {
                    handle.pushUp();
                } else {
                    handle.pushDown();
                }

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        software.process();
                    }
                }, INERTIA);
            }
        }
    }
}
