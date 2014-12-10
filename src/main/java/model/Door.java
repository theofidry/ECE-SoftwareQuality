package model;

import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;
import model.enums.DoorStateEnum;

import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Plane doors.
 */
public class Door {

    //
    // ATTRIBUTES
    //
    /**
     * Inertia of the door in ms.
     */
    public static final int INERTIA = 200;

    /**
     * Time a door takes to fully open or close.
     */
    public static final int MOVING_TIME = 3000;

    /**
     * State of the door.
     */
    public DoorStateEnum state = DoorStateEnum.CLOSED;

    private long start;

    private Timer timer = new Timer();

    private final ExtendedPropertyChangeSupport changeSupport = new ExtendedPropertyChangeSupport(this);


    //
    // METHODS
    //

    /**
     * Check if the door is locked open.
     *
     * @return true if is locked open, false otherwise
     */
    public final boolean isOpen() {
        return (this.state == DoorStateEnum.OPEN);
    }

    /**
     * Check if the door is locked closed.
     *
     * @return true if is locked closed, false otherwise
     */
    public final boolean isClosed() {
        return (this.state == DoorStateEnum.CLOSED);
    }

    /**
     * Check if the door is moving.
     *
     * @return true if is moving, false otherwise
     */
    public final boolean isMoving() {
        return (this.state == DoorStateEnum.MOVING);
    }

    /**
     * Convenience for state.toString()
     *
     * @return state of the door
     */
    public String getState() {
        return this.state.toString();
    }

    /**
     * Close the door.
     */
    public void close() {
        moves(DoorStateEnum.CLOSED);
    }

    /**
     * Open the door.
     */
    public void open() {
        moves(DoorStateEnum.OPEN);
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
    private void moves(final DoorStateEnum finalState) {

        long movingTime = MOVING_TIME;

        // interrupts all tasks and remove them from the scheduler
        if (state == DoorStateEnum.MOVING) {
            timer.cancel();
            timer.purge();
            timer = new Timer();
            movingTime = (System.currentTimeMillis() - start);
        }

        if (state != finalState) {

            timer.schedule(new MovingTask(finalState, movingTime), INERTIA);
        }
    }

    final private class MovingTask extends TimerTask {

        DoorStateEnum finalState;
        private long movingTime;

        public MovingTask(DoorStateEnum finalState, long movingTime) {
            this.finalState = finalState;
            this.movingTime = movingTime;
        }

        @Override
        public void run() {

            start = System.currentTimeMillis();
            state = DoorStateEnum.MOVING;
            changeSupport.firePropertyChange("state", null, state);
            timer.schedule(new LockingTask(finalState), movingTime);
        }
    }

    final private class LockingTask extends TimerTask {

        DoorStateEnum finalState;

        public LockingTask(DoorStateEnum finalState) {
            this.finalState = finalState;
        }

        @Override
        public void run() {

            state = finalState;
            changeSupport.firePropertyChange("state", null, state);
        }
    }
}
