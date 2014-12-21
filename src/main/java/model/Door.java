package model;

import model.enums.DoorStateEnum;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Plane landing gears doors.
 */
public class Door extends Model {

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
    public static final int MOVING_TIME = 2500;

    /**
     * State of the door.
     */
    public DoorStateEnum state = DoorStateEnum.CLOSED;

    /**
     * Field used to calculate how long a closing or opening sequence has been going.
     */
    private long start;

    /**
     * Timer used to for delayed threading task.
     */
    private Timer timer = new Timer();


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

        if (!state.equals(finalState)) {

            timer.schedule(new MovingTask(finalState, movingTime), INERTIA);
        }
    }

    /**
     * Timer task used for making the door move.
     */
    private final class MovingTask extends TimerTask {

        /**
         * Final state the door will reach.
         */
        private DoorStateEnum finalState;

        /**
         * Time the door will be moving before reaching its final state.
         */
        private long movingTime;

        /**
         * Instantiate a moving task, used to make the door moving.
         *
         * @param finalState final state the door will reach
         * @param movingTime time the door will be moving before reaching its final state
         */
        public MovingTask(DoorStateEnum finalState, long movingTime) {
            this.finalState = finalState;
            this.movingTime = movingTime;
        }

        /**
         * Makes the door moving.
         */
        @Override
        public void run() {

            start = System.currentTimeMillis();
            state = DoorStateEnum.MOVING;
            changeSupport.firePropertyChange("state", null, state);
            timer.schedule(new LockingTask(finalState), movingTime);
        }
    }

    /**
     * Timer task used for locking the door once it's final state has been reached.
     */
    private final class LockingTask extends TimerTask {

        /**
         * Final state the door will reach.
         */
        private DoorStateEnum finalState;

        /**
         * Instantiate a locking task, used to lock the door once its final state has been reached.
         *
         * @param finalState state in which the door must be to be locked
         */
        public LockingTask(DoorStateEnum finalState) {
            this.finalState = finalState;
        }

        /**
         * Locks the door.
         */
        @Override
        public void run() {

            state = finalState;
            changeSupport.firePropertyChange("state", null, state);
        }
    }
}
