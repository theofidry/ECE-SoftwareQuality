package plane;

import plane.enums.LightsEnum;
import plane.enums.WheelsPositionEnum;

/**
 * Software system.
 */
public class Software {

    /**
     * Actions the wheels and light according to the handle position and the current state fo the lights and wheels.
     *
     * @param handle
     * @param wheels
     * @param lights
     *
     * @exception java.lang.IllegalStateException Unexpected scenario.
     */
    public static void process(Handle handle, Wheels wheels, Lights lights) throws IllegalStateException {

        if (handle.up && wheels.position == WheelsPositionEnum.UP)
            lights.color = LightsEnum.OFF;

        if (handle.up && wheels.position == WheelsPositionEnum.DOWN)
            throw new IllegalStateException("Unsupported state.");

        if (handle.up && wheels.position == WheelsPositionEnum.MOVING) {

            lights.color = LightsEnum.ORANGE;
            wheels.position = WheelsPositionEnum.UP;
        }

        if (!handle.up && wheels.position == WheelsPositionEnum.UP)
            lights.color = LightsEnum.ORANGE;

        if (!handle.up && wheels.position == WheelsPositionEnum.DOWN)
            lights.color = LightsEnum.GREEN;

        if (!handle.up && wheels.position == WheelsPositionEnum.MOVING) {

            lights.color = LightsEnum.ORANGE;
            wheels.position = WheelsPositionEnum.DOWN;
        }
    }
}
