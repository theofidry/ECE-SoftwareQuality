package view;

import model.Lights;
import model.enums.LightsColorEnum;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.TextField;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Door panel, is composed of a label and updates its value accordingly to the given model.
 */
public class LightsPanel extends JPanel implements PropertyChangeListener {

    /**
     * Panel's model.
     */
    private Lights lights;

    /**
     * Panel's label.
     */
    private JLabel label = new JLabel();

    /**
     * Panel's text field.
     */
    private TextField field = new TextField();

    /**
     * Instantiate a door panel.
     *
     * @param lights model to which the view is bound
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
        this.lights.addPropertyChangeListener(this);
        modelToView(lights.getColor());
    }

    /**
     * Updates the view according to the model value.
     *
     * @param evt event triggering the update
     */
    public void propertyChange(PropertyChangeEvent evt) {
        modelToView((LightsColorEnum) evt.getNewValue());
    }

    /**
     * Helper used to update the new model value to the view.
     *
     * @param color state of the door
     */
    private void modelToView(LightsColorEnum color) {

        // Reformat enum to text in lowercase and with the first letter capitalized
        String text = color.toString().toLowerCase();
        text = text.substring(0, 1).toUpperCase() + text.substring(1);

        field.setText(text);
    }
}
