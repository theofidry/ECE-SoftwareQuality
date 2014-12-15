package model;

import model.enums.DoorStateEnum;
import model.enums.LandingGearPositionEnum;
import model.enums.LightsColorEnum;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Software system.
 */
public class Software implements PropertyChangeListener {

    //
    // ATTRIBUTES
    //
    /**
     * Landing gears doors, in order: front, right, left.
     */
    private Door[] doors;

    /**
     * Handle of the planed, used to initiate the outgoing or retracting sequence.
     */
    private Handle handle;

    /**
     * Landing gears, in order: front, right, left.
     */
    private LandingGear[] landingGears;

    /**
     * Lights indicator.
     */
    private Lights lights;

    /**
     * Sequence checkers: used to control each steps of the outgoing and retracting sequence.
     */
    private SequenceChecker outgoingSC = new SequenceChecker(),
            retractingSC = new SequenceChecker();

    /**
     * If true mean the ongoing sequence is the outgoing sequence, otherwise is the retracting sequence.
     */
    private boolean outgoing = true;

    /**
     * Case where the the system failed.
     */
    private boolean failure;

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


    //
    // CONSTRUCTORS
    //

    /**
     * Instantiate a software.
     *
     * @param doors        plane landing gears doors
     * @param handle       plane handle
     * @param lights       plane lights
     * @param landingGears plane landing gears
     */
    public Software(Door[] doors, Handle handle, Lights lights, LandingGear[] landingGears) {

        this.doors = doors;
        this.handle = handle;
        this.lights = lights;
        this.landingGears = landingGears;

        // Add listener to each door
        for (Door door : doors) {
            door.addPropertyChangeListener(this);
        }

        // Add listener to each gear
        for (LandingGear gear : landingGears) {
            gear.addPropertyChangeListener(this);
        }
    }


    //
    // METHODS
    //

    /**
     * Actions the wheels and light according to the handle position and the current state fo the lights and wheels.
     */
    public void process() throws IllegalStateException {

        try {
            if (handle.isUp()) {

                // RETRACTING SEQUENCE

                if (areGearsLocked(LandingGearPositionEnum.DEPLOYED) && areDoorsLocked(DoorStateEnum.CLOSED)) {

                    outgoing = false;

                    openDoors();
                    retractingSC.validateStep(0);
                } else {

                    // Interrupted an outgoing sequence
                    retractWhenOutgoing();
                }


            } else {

                // OUTGOING SEQUENCE
                if (areGearsLocked(LandingGearPositionEnum.RETRACTED) && areDoorsLocked(DoorStateEnum.CLOSED)) {

                    outgoing = true;

                    openDoors();
                    outgoingSC.validateStep(0);
                } else {

                    // Interrupted an retracting sequence
                    deployWhenRetracting();
                }
            }
        } catch (IllegalStateException exception) {

            //TODO: urgency sequence (not asked for the TP)
            failure = true;
            throw exception;
        }
    }

    /**
     * Helper used for when the retracting sequence has been started when the outgoing sequence was already started.
     */
    private void retractWhenOutgoing() {

        switch (outgoingSC.getLastStepValidated()) {

            case 0:
                // opening of the doors already requested
                retractingSC.validateStep(0);
                break;
            case 1:
                // deploying of the gears has been requested
                if (areDoorsLocked(DoorStateEnum.OPEN)) {
                    retractGears();
                    retractingSC.validateStep(1);
                } else {
                    throw new IllegalStateException("Expected doors opened");
                }
                break;
            case 2:
                // closing of the doors already requested
                if (areGearsLocked(LandingGearPositionEnum.DEPLOYED)) {

                    openDoors();
                    retractingSC.validateStep(0);
                } else {
                    throw new IllegalStateException("Expected gears deployed");
                }
                break;

            default:
                throw new IllegalStateException("Unexpected situation");
        }

        outgoingSC.reset();
    }

    /**
     * Helper used for when the retracting sequence has been started when the outgoing sequence was already started.
     */
    private void deployWhenRetracting() {

        switch (retractingSC.getLastStepValidated()) {

            case 0:
                // opening of the doors already requested
                outgoingSC.validateStep(0);
                break;
            case 1:
                // retracting of the gears has been requested
                if (areDoorsLocked(DoorStateEnum.OPEN)) {
                    deployGears();
                    outgoingSC.validateStep(1);
                } else {
                    throw new IllegalStateException("Expected doors opened");
                }
                break;
            case 2:
                // closing of the doors already requested
                if (areGearsLocked(LandingGearPositionEnum.RETRACTED)) {

                    openDoors();
                    outgoingSC.validateStep(0);
                } else {
                    throw new IllegalStateException("Expected gears retracted");
                }
                break;

            default:
                throw new IllegalStateException("Unexpected situation");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param evt
     */
    public void propertyChange(PropertyChangeEvent evt) {

        setLights(evt);

        // Proceed to outgoing/retracting sequence
        if (outgoing) {

            // Outgoing sequence started
            proceedOutoingSeq();
        } else {

            // Retracting sequence started
            proceedRetractingSeq();
        }
    }

    /**
     * Determine which lights color should be set.
     *
     * @param evt
     */
    private void setLights(PropertyChangeEvent evt) {

        if (failure) {
            lights.setColor(LightsColorEnum.RED);
        } else if (evt.getSource() instanceof LandingGear) {

            // Case where there is no failure and the event is triggered by a gear
            if (areGearsLocked(LandingGearPositionEnum.RETRACTED)) {
                lights.setColor(LightsColorEnum.GREEN);
            } else if (areGearsLocked(LandingGearPositionEnum.DEPLOYED)) {
                lights.setColor(LightsColorEnum.OFF);
            } else {
                lights.setColor(LightsColorEnum.ORANGE);
            }
        }
    }

    /**
     * Proceed the outgoing sequence.
     */
    private void proceedOutoingSeq() {

        //TODO: move the first step from process here

        if (outgoingSC.getLastStepValidated() == 0 && areDoorsLocked(DoorStateEnum.OPEN)) {

            // Proceed to step 1
            deployGears();
            outgoingSC.validateStep(1);
        } else if (outgoingSC.getLastStepValidated() == 1 && areGearsLocked(LandingGearPositionEnum.DEPLOYED)) {

            // Proceed to step 2
            closeDoors();
            outgoingSC.validateStep(2);
        } else if (outgoingSC.getLastStepValidated() == 2 && areDoorsLocked(DoorStateEnum.CLOSED)) {

            // End of outgoing sequence
            outgoingSC.reset();
        }
    }

    /**
     * Proceed to the retracting sequence.
     */
    private void proceedRetractingSeq() {

        //TODO: move the first step from process here.

        if (retractingSC.getLastStepValidated() == 0 && areDoorsLocked(DoorStateEnum.OPEN)) {

            // Proceed to step 1
            retractGears();
            retractingSC.validateStep(1);
        } else if (retractingSC.getLastStepValidated() == 1 && areGearsLocked(LandingGearPositionEnum.RETRACTED)) {

            // Proceed to step 2
            closeDoors();
            retractingSC.validateStep(2);
        } else if (retractingSC.getLastStepValidated() == 2 && areDoorsLocked(DoorStateEnum.CLOSED)) {

            // End of retracting sequence
            retractingSC.reset();
        }
    }

    /**
     * Check if the doors are all in the given locked state.
     *
     * @param lockedState state the doors must be locked in
     * @return true of all doors are locked, false otherwise
     */
    public boolean areDoorsLocked(DoorStateEnum lockedState) {

        boolean value = true;

        if (lockedState == DoorStateEnum.MOVING) {
            return false;
        }

        for (Door door : doors) {

            if (!door.state.equals(lockedState)) {
                value = false;
                break;
            }
        }

        return value;
    }

    /**
     * Check if the gears are all in the given locked state.
     *
     * @param lockedPosition state the gears must be locked in
     * @return true of all gears are locked, false otherwise
     */
    public boolean areGearsLocked(LandingGearPositionEnum lockedPosition) {

        boolean value = true;

        if (lockedPosition == LandingGearPositionEnum.MOVING) {
            return false;
        }

        for (LandingGear gear : landingGears) {

            if (gear.position != lockedPosition) {
                value = false;
                break;
            }
        }

        return value;
    }

    /**
     * Open all the doors.
     */
    public void openDoors() {

        for (Door door : doors) {
            door.open();
        }
    }

    /**
     * Close all the doors.
     */
    public void closeDoors() {

        for (Door door : doors) {
            door.close();
        }
    }

    /**
     * Open all the landing gears.
     */
    public void deployGears() {

        for (LandingGear gear : landingGears) {
            gear.deploy();
        }
    }

    /**
     * Close all the doors.
     */
    public void retractGears() {

        for (LandingGear gear : landingGears) {
            gear.retract();
        }
    }

    /**
     * Helper used to watch each steps of the outgoing and retracting sequence.
     */
    private class SequenceChecker {

        /**
         * Stimulate the opening of the doors.
         */
        private boolean step0;

        /**
         * For the outgoing sequence: stimulate the deployment of the gears.
         * For the retracting sequence: stimulate the retraction of the gears.
         */
        private boolean step1;

        /**
         * Stimulate the doors closure.
         */
        private boolean step2;

        /**
         * Reset all steps.
         */
        public void reset() {
            step0 = false;
            step1 = false;
            step2 = false;
        }

        /**
         * Get the number of the last test passed.
         *
         * @return Step number (0 to 2) or -1 when no step validated
         */
        public int getLastStepValidated() {

            if (step2) {
                return 2;
            }
            if (step1) {
                return 1;
            }
            if (step0) {
                return 0;
            }

            return -1;
        }

        /**
         * Validate the given step.
         *
         * @param stepNbr step number. Range: 0 to 2 (included)
         */
        public void validateStep(int stepNbr) {

            if (stepNbr == 0) {
                step0 = true;
                return;
            } else if (stepNbr == 1) {
                step1 = true;
                return;
            } else if (stepNbr == 2) {
                step2 = true;
            }
        }
    }
}
