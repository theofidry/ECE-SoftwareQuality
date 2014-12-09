package model;

/**
 * Software system.
 */
public class Software {

    /**
     * Actions the wheels and light according to the handle position and the current state fo the lights and wheels.
     *
     * @param handle
     * @param landingGears
     * @param lights
     *
     * @exception java.lang.IllegalStateException Unexpected scenario.
     */
    public void process(Door[] doors, Handle handle, LandingGear[] landingGears, Lights lights)
    throws IllegalStateException {



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
     *
     * @param doors array of doors to open
     */
    public void openDoors(Door[] doors) {

        for (Door door: doors) {
            door.open();
        }
    }

    /**
     * Close all the doors.
     *
     * @param doors array of doors to close
     */
    public void closeDoors(Door[] doors) {

        for (Door door: doors) {
            door.close();
        }
    }
}
