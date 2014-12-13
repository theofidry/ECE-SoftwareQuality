package model;

import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;

import java.beans.PropertyChangeListener;

/**
 * Landing gear handle.
 */
public class Handle {

    private final ExtendedPropertyChangeSupport changeSupport = new ExtendedPropertyChangeSupport(this);

    /**
     * Position of the handle. If false the handle is down, if true is up.
     */
    private boolean position = true;

    /**
     * Test if the handle is up.
     */
    public boolean isUp() {
        return position;
    }

    /**
     * Push the handle up.
     */
    public void pushUp() {
        position = true;
        changeSupport.firePropertyChange("position", null, position);
    }

    /**
     * Push the handle down.
     */
    public void pushDown() {
        position = false;
        changeSupport.firePropertyChange("position", null, position);
    }

    public void addPropertyChangeListener(PropertyChangeListener x) {
        changeSupport.addPropertyChangeListener(x);
    }

    public void removePropertyChangeListener(PropertyChangeListener x) {
        changeSupport.removePropertyChangeListener(x);
    }
}
