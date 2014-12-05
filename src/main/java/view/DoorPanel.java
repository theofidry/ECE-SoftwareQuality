package view;

import model.Door;
import model.enums.DoorStateEnum;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by Theo on 05/12/14.
 */
public class DoorPanel extends JPanel implements PropertyChangeListener {

    public Door doorTest = new Door();

    JRadioButton doorRadioButton = new JRadioButton();

    public DoorPanel() {

        this.setToolTipText("DOOR");
        this.add(doorRadioButton);
        doorTest.addPropertyChangeListener(this);
        modelToView(doorTest.state, true);
    }

    public void propertyChange(PropertyChangeEvent evt) {

        //switch on new value
        modelToView((DoorStateEnum) evt.getNewValue(), true);
    }

    private void modelToView(DoorStateEnum state, boolean value) {

        switch (state) {

            case CLOSED:
                doorRadioButton.setText("CLOSED");
                break;
            case MOVING:
                doorRadioButton.setText("MOVING");
                break;
            case OPEN:
                doorRadioButton.setText("OPEN");
                break;
        }
    }
}
