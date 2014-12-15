package model;

/**
 * Landing gear handle.
 */
public class Handle extends Model {

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
}
