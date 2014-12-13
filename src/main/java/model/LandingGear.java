package model;

import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;
import model.enums.LandingGearPositionEnum;

import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Aircraft landing gear.
 */
public class LandingGear {

    //
    // ATTRIBUTES
    //
    /**
     * Inertia of the gear in ms.
     */
    public static final int INERTIA = 200;

    /**
     * Time a gear takes to fully extend or retract.
     */
    public static final int MOVING_TIME = 3500;

    /**
     * Landing gear position, can be up (retracted), moving or down (deployed).
     */
    public LandingGearPositionEnum position = LandingGearPositionEnum.RETRACTED;

    private long start;

    private Timer timer = new Timer();

    private final ExtendedPropertyChangeSupport changeSupport = new ExtendedPropertyChangeSupport(this);


    //
    // METHODS
    //

    /**
     * Check if the gear is locked up.
     *
     * @return
     */
    public boolean isRetracted() {
        return (position == LandingGearPositionEnum.RETRACTED);
    }

    /**
     * Check if the gear is locked down.
     *
     * @return
     */
    public boolean isDeployed() {
        return (position == LandingGearPositionEnum.DEPLOYED);
    }

    /**
     * Check if the gear is being retracted and deployed.
     *
     * @return
     */
    public boolean isMoving() {
        return (position == LandingGearPositionEnum.MOVING);
    }

    /**
     * Convenience for state.toString()
     *
     * @return state of the door
     */
    public String getPosition() {
        return this.position.toString();
    }

    /**
     * Retracts the landing gear. If deployed moves it, if is already moving becomes retracted.
     */
    public void retract() {
        moves(LandingGearPositionEnum.RETRACTED);
    }

    /**
     * Deploy the landing gear. If retracted moves it, if is already moving becomes deployed.
     */
    public void deploy() {
        moves(LandingGearPositionEnum.DEPLOYED);
    }


    public void addPropertyChangeListener(PropertyChangeListener x) {
        changeSupport.addPropertyChangeListener(x);
    }

    public void removePropertyChangeListener(PropertyChangeListener x) {
        changeSupport.removePropertyChangeListener(x);
    }


    //
    // HELPERS
    //

    /**
     * Function to implement moving.
     *
     * @param finalState final state to reach
     */
    private void moves(final LandingGearPositionEnum finalState) {

        long movingTime = MOVING_TIME;

        // interrupts all tasks and remove them from the scheduler
        if (position == LandingGearPositionEnum.MOVING) {
            timer.cancel();
            timer.purge();
            timer = new Timer();
            movingTime = (System.currentTimeMillis() - start);
        }

        if (position != finalState) {

            timer.schedule(new MovingTask(finalState, movingTime), INERTIA);
        }
    }

    final private class MovingTask extends TimerTask {

        LandingGearPositionEnum finalState;
        private long movingTime;

        public MovingTask(LandingGearPositionEnum finalState, long movingTime) {
            this.finalState = finalState;
            this.movingTime = movingTime;
        }

        @Override
        public void run() {

            start = System.currentTimeMillis();
            position = LandingGearPositionEnum.MOVING;
            changeSupport.firePropertyChange("position", null, position);
            timer.schedule(new LockingTask(finalState), movingTime);
        }
    }

    final private class LockingTask extends TimerTask {

        LandingGearPositionEnum finalState;

        public LockingTask(LandingGearPositionEnum finalState) {
            this.finalState = finalState;
        }

        @Override
        public void run() {

            position = finalState;
            changeSupport.firePropertyChange("position", null, position);
        }
    }
}
