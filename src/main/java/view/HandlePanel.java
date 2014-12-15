package view;


import model.Handle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HandlePanel extends JPanel implements PropertyChangeListener {

    /**
     * Panel's model.
     */
    private Handle handle;

    /**
     * Panel's label.
     */
    private JLabel label = new JLabel();

    /**
     * Panel's slider, representing the handle.
     */
    private JSlider slider = new JSlider();

    /**
     * Instantiate a handle panel.
     *
     * @param handle   model to which the view is bound
     * @param listener listener in which the actions for the slider are implemented
     */
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
     * Updates the view according to the model value.
     *
     * @param evt event triggering the update
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        modelToView(handle);
    }

    /**
     * Helper used to update the new model value to the view.
     *
     * @param handle value of the handle
     */
    private void modelToView(Handle handle) {

        if (handle.isUp())
            slider.setValue(1);
        else
            slider.setValue(0);
    }
}
