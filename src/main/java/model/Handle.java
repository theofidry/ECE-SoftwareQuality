package model;

/**
 * Landing gear handle.
 */
public class Handle {

    /**
     * Position of the handle. If false the handle is down, if true is up.
     */
    public boolean position = true;

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
    }

    /**
     * Push the handle down.
     */
    public void pushDown() {
        position = false;
    }
}
