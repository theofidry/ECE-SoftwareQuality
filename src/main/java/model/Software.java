package model;

/**
 * Software system.
 */
public class Software {

    private Door[] doors;
    private Handle handle;
    private Lights lights;
    private LandingGear[] landingGears;

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
    }

    /**
     * Actions the wheels and light according to the handle position and the current state fo the lights and wheels.
     *
     * @throws java.lang.IllegalStateException Unexpected scenario.
     */
    public void process()
            throws IllegalStateException {

        // For testing
        if (handle.isUp())
            openDoors();
        else
            closeDoors();

        // OUTGOING SEQUENCE
        //  when gears retracted and doors open
        //  if handle goes down
        //      doors opening
        //      when doors open, deploying the gears
        //      wait for gears deployed
        //
        //      closing doors
        //      wait for doors closed,

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
}
