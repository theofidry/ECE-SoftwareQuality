package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Plane.
 */
public class Plane implements PropertyChangeListener {


    //
    // ATTRIBUTES
    //
    /**
     * Landing gears doors, in order: front, right, left.
     */
    private Door[] doors = {new Door(), new Door(), new Door()};

    /**
     * Handle of the planed, used to initiate the outgoing or retracting sequence.
     */
    private Handle handle = new Handle();

    /**
     * Landing gears, in order: front, right, left.
     */
    private LandingGear[] landingGears = {new LandingGear(), new LandingGear(), new LandingGear()};

    /**
     * Lights indicator.
     */
    private Lights lights = new Lights();

    /**
     * Software, controls everything.
     */
    private Software software;


    //
    // GETTERS
    //

    /**
     * Get the plane landing gears doors. In order: front, right, left.
     *
     * @return landing gears doors
     */
    public Door[] getDoors() {
        return doors;
    }

    /**
     * Get the plane handle.
     *
     * @return handle
     */
    public Handle getHandle() {
        return handle;
    }

    /**
     * Get the plane landing gears. In order: front, right, left.
     *
     * @return landing gears
     */
    public LandingGear[] getLandingGears() {
        return landingGears;
    }

    /**
     * Get the plane lights.
     *
     * @return lights system
     */
    public Lights getLights() {
        return lights;
    }

    /**
     * Get the plane software.
     *
     * @return software
     */
    public Software getSoftware() {
        return software;
    }


    //
    // CONSTRUCTORS
    //

    /**
     * Default constructor.
     */
    public Plane() {

        software = new Software(doors, handle, lights, landingGears);
        handle.addPropertyChangeListener(this);
    }

    /**
     * {@inheritDoc}
     *
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        software.process();
    }
}
