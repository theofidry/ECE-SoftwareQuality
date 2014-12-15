package model;

import model.enums.LandingGearPositionEnum;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Aircraft landing gear.
 */
public class LandingGear extends Model {

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

    /**
     * Timer task used for making the door move.
     */
    private final class MovingTask extends TimerTask {

        /**
         * Final position the gear will reach.
         */
        LandingGearPositionEnum finalPosition;

        /**
         * Time the gear will be moving before reaching its final position.
         */
        private long movingTime;

        /**
         * Instantiate a moving task, used to make the gear moving.
         *
         * @param finalPosition final position the gear will reach
         * @param movingTime    time the gear will be moving before reaching its final state
         */
        public MovingTask(LandingGearPositionEnum finalPosition, long movingTime) {
            this.finalPosition = finalPosition;
            this.movingTime = movingTime;
        }

        /**
         * Makes the gear moving.
         */
        @Override
        public void run() {

            start = System.currentTimeMillis();
            position = LandingGearPositionEnum.MOVING;
            changeSupport.firePropertyChange("position", null, position);
            timer.schedule(new LockingTask(finalPosition), movingTime);
        }
    }

    /**
     * Timer task used for locking the gear once it's position state has been reached.
     */
    private final class LockingTask extends TimerTask {

        /**
         * Final position the gear will reach.
         */
        private LandingGearPositionEnum finalPosition;

        /**
         * Instantiate a locking task, used to lock the gear once its final position has been reached.
         *
         * @param finalPosition position in which the gear must be to be locked
         */
        public LockingTask(LandingGearPositionEnum finalPosition) {
            this.finalPosition = finalPosition;
        }

        /**
         * Locks the gear.
         */
        @Override
        public void run() {

            position = finalPosition;
            changeSupport.firePropertyChange("position", null, position);
        }
    }
}
