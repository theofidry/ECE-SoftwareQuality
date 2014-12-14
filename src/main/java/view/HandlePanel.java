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

    private Handle handle;
    private JLabel label = new JLabel();
    private JSlider slider = new JSlider();

    /**
     * Default constructor.
     */
    public HandlePanel() {
        super();
    }

    public HandlePanel(Handle handle, ChangeListener listener) {

        this.handle = handle;

        //
        // Initialize components
        //
        this.setLayout(new BorderLayout());

        label.setText("Handle");
        label.setLabelFor(slider);
        this.add(label);

        slider.setOrientation(SwingConstants.VERTICAL);
        slider.setMaximum(1);
        slider.setValue(1);
        this.add(slider, BorderLayout.CENTER);

        //
        // Binds view to model and vice versa
        //
        slider.addChangeListener(listener);
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
}
