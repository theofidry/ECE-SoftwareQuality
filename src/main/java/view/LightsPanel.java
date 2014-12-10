package view;

import model.Lights;
import model.enums.DoorStateEnum;
import model.enums.LightsColorEnum;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Door panel, is composed of a label and updates its value accordingly to the given model.
 */
public class LightsPanel extends JPanel implements PropertyChangeListener {

    private Lights lights;
    private JLabel label = new JLabel();
    private TextField field = new TextField();

    /**
     * Instantiate a door panel.
     *
     * @param door model to which the view is bound
     * @param name label content, if empty is set to default value
     */
    public LightsPanel(Lights lights) {

        this.lights = lights;

        //
        // Initialize components
        //
        this.setLayout(new GridLayout());

        label.setText("Lights");
        label.setLabelFor(field);
        this.add(label);

        field.setEditable(false);
        this.add(field);

        //
        // Binds model to view
        //
        lights.addPropertyChangeListener(this);
        modelToView(lights.getColor());
    }

    /**
     * {@inheritDoc}
     *
     * @param evt
     */
    public void propertyChange(PropertyChangeEvent evt) {

        //switch on new value
        modelToView((LightsColorEnum) evt.getNewValue());
    }

    /**
     * Helper used to update the new model value to the view.
     *
     * @param state state of the door
     */
    private void modelToView(LightsColorEnum color) {

        // Reformat enum to text in lowercase and with the first letter capitalized
        String text = color.toString().toLowerCase();
        text = text.substring(0, 1).toUpperCase() + text.substring(1);

        field.setText(text);
    }
}
