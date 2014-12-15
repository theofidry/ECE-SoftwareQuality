package view;

import model.LandingGear;
import model.enums.LandingGearPositionEnum;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.TextField;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Door panel, is composed of a label and updates its value accordingly to the given model.
 */
public class LandingGearPanel extends JPanel implements PropertyChangeListener {

    /**
     * Panel's model.
     */
    private LandingGear gear;

    /**
     * Panel's view.
     */
    private JLabel label = new JLabel();

    /**
     * Panel's text field, used to display the gear position.
     */
    private TextField field = new TextField();

    /**
     * Instantiate a gear panel.
     *
     * @param gear model to which the view is bound
     * @param name label content, if empty is set to default value
     */
    public LandingGearPanel(LandingGear gear, String name) {

        this.gear = gear;

        if (name.isEmpty()) {
            name = "Gear";
        }

        //
        // Initialize components
        //
        this.setLayout(new GridLayout());

        label.setText(name);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setLabelFor(field);
        this.add(label);

        field.setEditable(false);
        this.add(field);

        //
        // Binds model to view
        //
        this.gear.addPropertyChangeListener(this);
        modelToView(gear.position);
    }

    /**
     * Updates the view according to the model value.
     *
     * @param evt event triggering the update
     */
    public void propertyChange(PropertyChangeEvent evt) {
        modelToView((LandingGearPositionEnum) evt.getNewValue());
    }

    /**
     * Helper used to update the new model value to the view.
     *
     * @param position state of the gear
     */
    private void modelToView(LandingGearPositionEnum position) {

        // Reformat enum to text in lowercase and with the first letter capitalized
        String text = position.toString().toLowerCase();
        text = text.substring(0, 1).toUpperCase() + text.substring(1);

        field.setText(text);
    }
}
