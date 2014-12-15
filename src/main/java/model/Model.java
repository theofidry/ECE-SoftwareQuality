package model;

import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;

import java.beans.PropertyChangeListener;

/**
 * Model class. Implements helpers to notify a view of it's properties change.
 */
abstract public class Model {

    /**
     * Used to notify the view/controller of a change of state.
     */
    protected final ExtendedPropertyChangeSupport changeSupport = new ExtendedPropertyChangeSupport(this);

    /**
     * Add a property change listener.
     *
     * @param x listener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener x) {
        changeSupport.addPropertyChangeListener(x);
    }

    /**
     * Remove a property change listener.
     *
     * @param x listener to be removed
     */
    public void removePropertyChangeListener(PropertyChangeListener x) {
        changeSupport.removePropertyChangeListener(x);
    }
}
