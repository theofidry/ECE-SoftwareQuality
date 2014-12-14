package view;

import model.Door;
import model.enums.DoorStateEnum;

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
public class DoorPanel extends JPanel implements PropertyChangeListener {

    private Door door;
    private JLabel label = new JLabel();
    private TextField field = new TextField();

    /**
     * Default constructor.
     */
    public DoorPanel() {
        super();
    }

    /**
     * Instantiate a door panel.
     *
     * @param door model to which the view is bound
     * @param name label content, if empty is set to default value
     */
    public DoorPanel(Door door, String name) {

        this.door = door;

        if (name.isEmpty())
            name = "Door";

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
        door.addPropertyChangeListener(this);
        modelToView(door.state);
    }

    /**
     * {@inheritDoc}
     *
     * @param evt
     */
    public void propertyChange(PropertyChangeEvent evt) {

        //switch on new value
        modelToView((DoorStateEnum) evt.getNewValue());
    }

    /**
     * Helper used to update the new model value to the view.
     *
     * @param state state of the door
     */
    private void modelToView(DoorStateEnum state) {

        // Reformat enum to text in lowercase and with the first letter capitalized
        String text = state.toString().toLowerCase();
        text = text.substring(0, 1).toUpperCase() + text.substring(1);

        field.setText(text);
    }
}
