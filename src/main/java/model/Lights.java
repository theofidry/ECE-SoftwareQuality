package model;

import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;
import model.enums.LightsColorEnum;

import java.beans.PropertyChangeListener;

/**
 * Lights system
 */
public class Lights {

    /**
     * Color of the lights system.
     */
    private LightsColorEnum color = LightsColorEnum.OFF;

    private final ExtendedPropertyChangeSupport changeSupport = new ExtendedPropertyChangeSupport(this);

    public LightsColorEnum getColor() {
        return color;
    }

    public void setColor(LightsColorEnum color) {
        this.color = color;
        changeSupport.firePropertyChange("color", null, color);
    }

    public void addPropertyChangeListener(PropertyChangeListener x) {
        changeSupport.addPropertyChangeListener(x);
    }

    public void removePropertyChangeListener(PropertyChangeListener x) {
        changeSupport.removePropertyChangeListener(x);
    }
}
