package model;

import model.enums.DoorStateEnum;
import model.enums.LandingGearPositionEnum;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Software system.
 */
public class Software implements PropertyChangeListener {

    private Door[] doors;
    private Handle handle;
    private Lights lights;
    private LandingGear[] landingGears;

    private SequenceChecker outgoingSC = new SequenceChecker(),
            retractingSC = new SequenceChecker();

    private boolean outgoing = true;

    public Door[] getDoors() {
        return doors;
    }

    public Handle getHandle() {
        return handle;
    }

    public Lights getLights() {
        return lights;
    }

    public LandingGear[] getLandingGears() {
        return landingGears;
    }

    public Software(Door[] doors, Handle handle, Lights lights, LandingGear[] landingGears) {
        this.doors = doors;
        this.handle = handle;
        this.lights = lights;
        this.landingGears = landingGears;

        for (Door door : doors) {
            door.addPropertyChangeListener(this);
        }
    }

    /**
     * Actions the wheels and light according to the handle position and the current state fo the lights and wheels.
     *
     * @throws java.lang.IllegalStateException Unexpected scenario.
     */
    public void process()
            throws IllegalStateException {


        if (handle.isUp()) {

            // RETRACTING SEQUENCE

            if (areGearsLocked(LandingGearPositionEnum.DEPLOYED) && areDoorsLocked(DoorStateEnum.CLOSED)) {

                outgoing = false;

                openDoors();
                retractingSC.validateStep(0);
            }

        } else {

            // OUTGOING SEQUENCE
            if (areGearsLocked(LandingGearPositionEnum.RETRACTED) && areDoorsLocked(DoorStateEnum.OPEN)) {

                outgoing = true;

                openDoors();
                outgoingSC.validateStep(0);
            }
        }


        // RETRACTION SEQUENCE
        //  when gears deployed and doors closed
        //  if handle goes up
        //      doors opening
        //      when doors open, gears retraction
        //      wait for gears tracted
        //
        //      closing doors
        //      wait for doors closed


        // INTERRUPTION
        //
//        The previous sequences should be interruptible by counter orders (a retrac-
//                tion order occurs during the let down sequence and conversely) at any time. In that case, the scenario continues from the point where it was interrupted. For instance, if an outgoing sequence is interrupted in the door closure phase (step 6 of the outgoing sequence) by an “Up” order, then the stimulation of the door closure electro-valve is stopped, and the retraction sequence is executed from step 2: the door opening electro-valve is stimulated and the doors begin opening again. Afterwards, the scenario continues up to the final step or up to a new interruption.

        //TODO
//        // Case with the handle up
//        if (handle.up && wheels.position == WheelsPositionEnum.RETRACTED) {
//            lights.color = LightsEnum.OFF;
//            return;
//        }
//
//        if (handle.up && wheels.position == WheelsPositionEnum.DEPLOYED)
//            throw new IllegalStateException("Unsupported state.");
//
//        if (handle.up && wheels.position == WheelsPositionEnum.MOVING) {
//            lights.color = LightsEnum.ORANGE;
//            wheels.position = WheelsPositionEnum.RETRACTED;
//            return;
//        }
//
//        // Case with the handle down
//        if (!handle.up && wheels.position == WheelsPositionEnum.RETRACTED) {
//            lights.color = LightsEnum.ORANGE;
//            wheels.position = WheelsPositionEnum.MOVING;
//            return;
//        }
//
//        if (!handle.up && wheels.position == WheelsPositionEnum.DEPLOYED) {
//            lights.color = LightsEnum.GREEN;
//            return;
//        }
//
//        if (!handle.up && wheels.position == WheelsPositionEnum.MOVING) {
//            lights.color = LightsEnum.GREEN;
//            wheels.position = WheelsPositionEnum.DEPLOYED;
//            return;
//        }
    }

    /**
     * {@inheritDoc}
     *
     * @param evt
     */
    public void propertyChange(PropertyChangeEvent evt) {

        if (outgoing) {

            // Outgoing sequence started

            if (outgoingSC.getLastStepValidated() == 0 && areDoorsLocked(DoorStateEnum.OPEN)) {

                // Proceed to step 1
                deployGears();
                outgoingSC.validateStep(1);
            } else if (outgoingSC.getLastStepValidated() == 1 && areGearsLocked(LandingGearPositionEnum.DEPLOYED)) {

                // Proceed to step 2
                closeDoors();
                outgoingSC.validateStep(2);
            }
        } else {

            // Retracting sequence started

            if (retractingSC.getLastStepValidated() == 0 && areDoorsLocked(DoorStateEnum.OPEN)) {

                // Proceed to step 1
                retractGears();
                retractingSC.validateStep(1);
            } else if (retractingSC.getLastStepValidated() == 2 && areGearsLocked(LandingGearPositionEnum.RETRACTED)) {
                // Proceed to step 2
                closeDoors();
                outgoingSC.validateStep(2);
            }
        }
    }

    /**
     * Check if the doors are all in the given locked state.
     *
     * @param lockedState state the doors must be locked in
     * @return
     */
    public boolean areDoorsLocked(DoorStateEnum lockedState) {

        boolean value = true;

        if (lockedState == DoorStateEnum.MOVING)
            return false;

        for (Door door : doors) {

            if (door.state != lockedState) {
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
     * @return
     */
    public boolean areGearsLocked(LandingGearPositionEnum lockedPosition) {

        boolean value = true;

        if (lockedPosition == LandingGearPositionEnum.MOVING)
            return false;

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

            if (step2)
                return 2;
            if (step1)
                return 1;
            if (step0)
                return 0;

            return -1;
        }


        /**
         * Validate the given step.
         *
         * @param stepNbr step number. Range: 0 to 2 (included)
         * @throws IndexOutOfBoundsException
         */
        public void validateStep(int stepNbr) throws IndexOutOfBoundsException {

            switch (stepNbr) {
                case 0:
                    step0 = true;
                    return;
                case 1:
                    step1 = true;
                    return;
                case 2:
                    step2 = true;
                    return;
                default:
                    throw new IndexOutOfBoundsException();
            }
        }
    }
}
