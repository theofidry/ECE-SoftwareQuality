package model;

import model.enums.LandingGearPositionEnum;

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
    public static final int MOVING_TIME = 3000;

    /**
     * Landing gear position, can be up (retracted), moving or down (deployed).
     */
    public LandingGearPositionEnum position = LandingGearPositionEnum.RETRACTED;

    private MovingThread thread;


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
    private void moves(LandingGearPositionEnum finalState) {

        if (thread == null) {
            thread = new MovingThread(finalState);
        } else {
            thread.interrupt();
            thread = new MovingThread(finalState, thread.movingTime);
        }

        thread.run();
    }

    /**
     * Thread class used to make the gear moving. Meant to be used as a class singleton (one thread per instance).
     */
    private class MovingThread extends Thread {

        private LandingGearPositionEnum finalPosition;

        /**
         * Time the gear has been moving in ms.
         */
        public long movingTime;

        /**
         * @param finalPosition state the door will finally reach
         */
        public MovingThread(LandingGearPositionEnum finalPosition) {
            this.finalPosition = finalPosition;
        }

        /**
         * When a thread was already working, takes into account its moving time.
         *
         * @param finalPosition state the gear will finally reach
         * @param movingTime    moving time of the previous command working
         */
        public MovingThread(LandingGearPositionEnum finalPosition, long movingTime) {
            this.finalPosition = finalPosition;
            this.movingTime = movingTime;
        }

        /**
         * Make the doors moving.
         */
        public void run() {

            long start = 0;

            try {

                // calculate the moving time
                if (position != LandingGearPositionEnum.MOVING)
                    movingTime = MOVING_TIME;

                // the door starts moving after the inertia time
                sleep(INERTIA);
                position = LandingGearPositionEnum.MOVING;
                start = System.currentTimeMillis();

                // moves a given time to reach the final state
                if (position != finalPosition)
                    sleep(movingTime);

                position = finalPosition;

            } catch (InterruptedException e) {
                movingTime = System.currentTimeMillis() - start;
            }
        }
    }
}
