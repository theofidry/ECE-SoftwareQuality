package model;


import edu.umd.cs.mtc.MultithreadedTestCase;
import exceptions.IllegalActionException;
import model.enums.DoorStateEnum;
import model.enums.LandingGearPositionEnum;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for {@link model.Software}
 */
public class SoftwareTest extends MultithreadedTestCase {

    /**
     * Time error margin in ms
     */
    public static final int ERROR_MARGIN = 100;

    public static final long TPS_CSTR1 = 15000l;

    public static Plane plane;
    public static Door[] doors = {new Door(), new Door(), new Door()};
    public static LandingGear[] gears = {new LandingGear(), new LandingGear(), new LandingGear()};
    public static Software software;

    @BeforeClass
    public static void setUp() throws Exception {

        plane = new Plane();

        doors = plane.getDoors();
        gears = plane.getLandingGears();
        software = plane.getSoftware();
    }

    @Test
    public void testCloseDoorsMethod_withAllDoorsOpen_expectAllClosed() throws Exception {

        for (Door door : doors) {
            door.state = DoorStateEnum.OPEN;
        }

        software = new Software(doors, new Handle(), new Lights(), gears);
        software.closeDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);

        for (Door door : software.getDoors()) {
            assertTrue(door.isClosed());
        }
    }

    @Test
    public void testCloseDoorsMethod_withVariousStates_expectAllClosedImmediately() throws Exception {

        DoorStateEnum[] states = {DoorStateEnum.OPEN, DoorStateEnum.MOVING, DoorStateEnum.CLOSED};

        for (int i = 0; i < 3; i++) {
            doors[0].state = states[i];
            for (int j = 0; j < 3; j++) {
                doors[1].state = states[j];
                for (int k = 0; k < 3; k++) {
                    doors[2].state = states[k];
                }
            }
        }

        software = new Software(doors, new Handle(), new Lights(), gears);
        software.closeDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);

        for (Door door : software.getDoors()) {
            assertTrue(door.isClosed());
        }
    }

    @Test
    public void testCloseDoorsMethod_withAllDoorsClosed_expectAllClosedImmediately() throws Exception {

        for (Door door : doors) {
            door.state = DoorStateEnum.CLOSED;
        }

        software = new Software(doors, new Handle(), new Lights(), gears);
        software.closeDoors();

        for (Door door : software.getDoors()) {
            assertTrue(door.isClosed());
        }
    }

    @Test
    public void testOpenDoorsMethod_withAllDoorsClosed_expectAllOpen() throws Exception {

        for (Door door : doors) {
            door.state = DoorStateEnum.CLOSED;
        }

        software = new Software(doors, new Handle(), new Lights(), gears);
        software.openDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);

        for (Door door : software.getDoors()) {
            assertTrue(door.isOpen());
        }
    }

    @Test
    public void testOpenDoorsMethod_withVariousStates_expectAllOpenImmediately() throws Exception {

        DoorStateEnum[] states = {DoorStateEnum.OPEN, DoorStateEnum.MOVING, DoorStateEnum.CLOSED};

        for (int i = 0; i < 3; i++) {
            doors[0].state = states[i];
            for (int j = 0; j < 3; j++) {
                doors[1].state = states[j];
                for (int k = 0; k < 3; k++) {
                    doors[2].state = states[k];
                }
            }
        }

        software = new Software(doors, new Handle(), new Lights(), gears);
        software.openDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);

        for (Door door : software.getDoors()) {
            assertTrue(door.isOpen());
        }
    }

    @Test
    public void testOpenDoorsMethod_withAllDoorsOpen_expectAllOpenImmetiately() throws Exception {

        for (Door door : doors) {
            door.state = DoorStateEnum.OPEN;
        }

        software = new Software(doors, new Handle(), new Lights(), gears);
        software.openDoors();

        for (Door door : software.getDoors()) {
            assertTrue(door.isOpen());
        }
    }

    @Test
    public void testRetractGearsMethod_withAllGearsDeployed_expectAllRetracted() throws Exception {

        for (LandingGear gear : gears) {
            gear.position = LandingGearPositionEnum.DEPLOYED;
        }

        software = new Software(doors, new Handle(), new Lights(), gears);
        software.retractGears();
        Thread.sleep(LandingGear.INERTIA + LandingGear.MOVING_TIME + ERROR_MARGIN);

        for (LandingGear gear : software.getLandingGears()) {
            assertTrue(gear.isRetracted());
        }
    }

    @Test
    public void testCloseDoorsMethod_withVariousStates_expectAllRetractedImmediately() throws Exception {

        LandingGearPositionEnum[] positions = {LandingGearPositionEnum.DEPLOYED, LandingGearPositionEnum.MOVING, LandingGearPositionEnum.RETRACTED};

        for (int i = 0; i < 3; i++) {
            gears[0].position = positions[i];
            for (int j = 0; j < 3; j++) {
                gears[1].position = positions[j];
                for (int k = 0; k < 3; k++) {
                    gears[2].position = positions[k];
                }
            }
        }

        software = new Software(doors, new Handle(), new Lights(), gears);
        software.retractGears();
        Thread.sleep(LandingGear.INERTIA + LandingGear.MOVING_TIME + ERROR_MARGIN);

        for (LandingGear gear : software.getLandingGears()) {
            assertTrue(gear.isRetracted());
        }
    }

    @Test
    public void testCloseDoorsMethod_withAllGearsRetractedexpectAllRetractedImmetiately() throws Exception {

        for (LandingGear gear : gears) {
            gear.position = LandingGearPositionEnum.RETRACTED;
        }

        software = new Software(doors, new Handle(), new Lights(), gears);
        software.retractGears();

        for (LandingGear gear : software.getLandingGears()) {
            assertTrue(gear.isRetracted());
        }
    }

    @Test
    public void testDeployGearsMethod_withAllGearsRetracted_expectAllDeployed() throws Exception {

        for (Door door : doors) {
            door.state = DoorStateEnum.CLOSED;
        }

        software = new Software(doors, new Handle(), new Lights(), gears);
        software.deployGears();
        Thread.sleep(LandingGear.INERTIA + LandingGear.MOVING_TIME + ERROR_MARGIN);

        for (LandingGear gear : software.getLandingGears()) {
            assertTrue(gear.isDeployed());
        }
    }

    @Test
    public void testDeployGearsMethod_withVariousStates_expectAllDeployedImmediately() throws Exception {

        LandingGearPositionEnum[] positions = {LandingGearPositionEnum.DEPLOYED, LandingGearPositionEnum.MOVING, LandingGearPositionEnum.RETRACTED};

        for (int i = 0; i < 3; i++) {
            gears[0].position = positions[i];
            for (int j = 0; j < 3; j++) {
                gears[1].position = positions[j];
                for (int k = 0; k < 3; k++) {
                    gears[2].position = positions[k];
                }
            }
        }

        software = new Software(doors, new Handle(), new Lights(), gears);
        software.deployGears();
        Thread.sleep(LandingGear.INERTIA + LandingGear.MOVING_TIME + ERROR_MARGIN);

        for (LandingGear gear : software.getLandingGears()) {
            assertTrue(gear.isDeployed());
        }
    }

    @Test
    public void testDeployGearsMethod_withAllGearsDeployed_expectAllDeployedImmetiately() throws Exception {

        for (LandingGear gear : gears) {
            gear.position = LandingGearPositionEnum.DEPLOYED;
        }

        software = new Software(doors, new Handle(), new Lights(), gears);
        software.deployGears();

        for (LandingGear gear : software.getLandingGears()) {
            assertTrue(gear.isDeployed());
        }
    }

    /**
     * R11: handle pushed down and stay down, all gears deployed and doors closed within 15s.
     */
    @Test
    public void testR11() throws Exception {

        // Worst case scenario: gears retracted and doors closed
        setUp();
        if (!plane.getHandle().isUp()) {
            plane.getHandle().pushUp();
            Thread.sleep(TPS_CSTR1 + ERROR_MARGIN);
        }
        assertTrue(plane.getHandle().isUp());
        assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.RETRACTED));
        assertTrue(plane.getSoftware().areDoorsLocked(DoorStateEnum.CLOSED));

        plane.getHandle().pushDown();
        Thread.sleep(TPS_CSTR1 + ERROR_MARGIN);
        assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.DEPLOYED)
                && plane.getSoftware().areDoorsLocked(DoorStateEnum.CLOSED));
    }

    /**
     * R12: handle pushed up and stay yup, all gears retracted and doors closed within 15s.
     */
    @Test
    public void testR12() throws Exception {

        // Worst case scenario: gears deployed and doors closed
        setUp();
        if (plane.getHandle().isUp()) {
            plane.getHandle().pushDown();
            Thread.sleep(TPS_CSTR1 + ERROR_MARGIN);
        }
        assertFalse(plane.getHandle().isUp());
        assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.DEPLOYED));
        assertTrue(plane.getSoftware().areDoorsLocked(DoorStateEnum.CLOSED));

        try {
            plane.getHandle().pushUp();
        } catch (IllegalActionException e) {
            // do nothing
        } finally {
            Thread.sleep(TPS_CSTR1 + ERROR_MARGIN);
            assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.RETRACTED) && plane.getSoftware()
                    .areDoorsLocked(DoorStateEnum.CLOSED));
        }
    }

    /**
     * R21: when handle is down, cannot retract gears.
     */
    @Test
    public void testR21() throws Exception {

        setUp();
        if (plane.getHandle().isUp()) {
            plane.getHandle().pushDown();
            Thread.sleep(TPS_CSTR1 + ERROR_MARGIN);
        }
        assertFalse(plane.getHandle().isUp());

        try {
            plane.getSoftware().retractGears();
            fail();
        } catch (IllegalActionException e) {
            // is the expected result
        }
    }

    /**
     * R31: cannot stimulate gears to deploy/retract when doors are not locked open.
     */
    @Test
    public void testR31() throws Exception {

        // Worst case scenario: gears deployed and doors closed
        setUp();
        if (plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN)) {
            plane.getDoors()[0].state = DoorStateEnum.CLOSED;
        }
        assertFalse(plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN));

        // Stimulate deployment
        // Case 1: already deployed
        // Case 2: are deploying
        // Case 3: are retracted
        try {
            plane.getSoftware().deployGears();
        } catch (IllegalActionException e) {
            // do nothing
        } finally {
            Thread.sleep(TPS_CSTR1 + ERROR_MARGIN);
            assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.RETRACTED) && plane.getSoftware()
                    .areDoorsLocked(DoorStateEnum.CLOSED));
        }

        // Stimulate retracting
        // Case 1: already deployed
        // Case 2: are deploying
        // Case 3: are retracted
    }

    /**
     * R32: cannot stimulate doors to open/close when gears are not locked (up or down).
     */
    @Test
    public void testR32() {
        //TODO
    }

    /**
     * R41: cannot stimulate gears to deploy/retract when doors are opened
     */
    @Test
    public void testR41() {
        //TODO
    }

    /**
     * R42: cannot stimulate deployment and retraction of gears simultaneously
     */
    @Test
    public void testR42() {
        //TODO
    }

    /**
     * R61: if one of the three doors is still seen locked in the closed position more than 7 seconds after
     * stimulating the opening, then the boolean output normal mode is set to false.
     */
    @Test
    public void testR61() {
        //TODO
    }

    /**
     * R62:  If one of the three doors is still seen locked in the open position more than 7 seconds after stimulating
     * the closure electro-valve, then the boolean output normal mode is set to false.
     */
    @Test
    public void testR62() {
        //TODO
    }

    /**
     * R63: If one of the three gears is still seen locked in the down position more than 7 seconds after stimulating the retraction electro-valve, then the boolean output normal mode is set to false.
     */
    @Test
    public void test63() {
        //TODO
    }

    /**
     * R64: If one of the three gears is still seen locked in the up position more than 7 seconds after stimulating the outgoing electro-valve, then the boolean output normal mode is set to false.
     */
    @Test
    public void testR64() {
        //TODO
    }

    /**
     * R71: If one of the three doors is not seen locked in the open position more than 7 seconds after stimulating the opening electro-valve, then the boolean output normal mode is set to false.
     */
    @Test
    public void testR71() {
        //TODO
    }

    /**
     * R72: If one of the three doors is not seen locked in the closed position more than 7 seconds after stimulating
     * the closure electro-valve, then the boolean output normal mode is set to false.
     */
    @Test
    public void testR72() {
        //TODO
    }

    /**
     * R73: If one of the three gears is not seen locked in the up position more than 10 seconds after stimulating the retraction electro-valve, then the boolean output normal mode is set to false.
     */
    @Test
    public void testR73() {
        //TODO
    }

    /**
     * R74: If one of the three gears is not seen locked in the down position more than 10 seconds after stimulating
     * the outgoing electro-valve, then the boolean output normal mode is set to false.
     */
    @Test
    public void testR74() {
        //TODO
    }

    /**
     * R81: When at least one computing module is working, if the landing gear command handle has been DOWN for 15 seconds, and if the gears are not locked down after 15 seconds, then the red light ”landing gear system failure” is on.
     */
    @Test
    public void testR81() {
        //TODO
    }

    /**
     * R82: When at least one computing module is working, if the landing gear command handle has been UP for 15 seconds, and if the gears are not locked retracted after 15 seconds, then the red light ”landing gear system failure” is on.
     */
    @Test
    public void testR82() {
        //TODO
    }

//Doors motion monitoring.
//        – if the control software does not see the value door closed[x] = false for all x 2 {front, left, right} 7 seconds after stimulation of the opening electro-valve, then the doors are considered as blocked and an anomaly is detected.
//        – if the control software does not see the value door open[x] = true for all x 2 {front,left,right} 7 seconds after stimulation of the opening electro- valve, then the doors are considered as blocked and an anomaly is detected.
//        – if the control software does not see the value door open[x] = false for all x 2 {front, left, right} 7 seconds after stimulation of the closure electro-valve, then the doors are considered as blocked and an anomaly is detected.
//        – if the control software does not see the value door closed[x] = true for all x 2 {front, left, right} 7 seconds after stimulation of the closure electro-valve, then the doors are considered as blocked and an anomaly is detected.
//        Gears motion monitoring.
//        – if the control software does not see the value gear extended[x] = false for all x 2 {front,left,right} 7 seconds after stimulation of the retraction electro- valve, then the gears are considered as blocked and an anomaly is detected.
//        – if the control software does not see the value gear retracted[x] = true for all x 2 {front, left, right} 10 seconds after stimulation of the retraction electro- valve, then the gears are considered as blocked and an anomaly is detected.
//        – if the control software does not see the value gear retracted[x] = false for all x 2 {front,left,right} 7 seconds after stimulation of the extension electro- valve, then the gears are considered as blocked and an anomaly is detected.
//        – if the control software does not see the value gear extended[x] = true for all x 2 {front, left, right} 10 seconds after stimulation of the extension electro- valve, then the gears are considered as blocked and an anomaly is detected.
}
