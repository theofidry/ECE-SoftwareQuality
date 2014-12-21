package model;


import edu.umd.cs.mtc.MultithreadedTestCase;
import exceptions.IllegalActionException;
import model.enums.DoorStateEnum;
import model.enums.LandingGearPositionEnum;
import org.junit.Before;
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

    /**
     * Time for R1X
     */
    public static final long TPS_CSTR1 = 15000L;

    @Test
    public void testCloseDoorsMethod_withAllDoorsOpen_expectAllClosed() throws Exception {

        Plane plane = new Plane();
        for (Door door : plane.getDoors()) {
            door.state = DoorStateEnum.OPEN;
        }
        assertTrue(plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN));

        plane.getSoftware().closeDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);
        assertTrue(plane.getSoftware().areDoorsLocked(DoorStateEnum.CLOSED));
    }

    @Test
    public void testCloseDoorsMethod_withAllDoorsClosed_expectAllClosedImmediately() throws Exception {

        Plane plane = new Plane();
        for (Door door : plane.getDoors()) {
            door.state = DoorStateEnum.CLOSED;
        }
        assertTrue(plane.getSoftware().areDoorsLocked(DoorStateEnum.CLOSED));

        plane.getSoftware().closeDoors();
        Thread.sleep(Door.INERTIA + ERROR_MARGIN);
        assertTrue(plane.getSoftware().areDoorsLocked(DoorStateEnum.CLOSED));
    }

    @Test
    public void testOpenDoorsMethod_withAllDoorsClosed_expectAllOpen() throws Exception {

        Plane plane = new Plane();
        for (Door door : plane.getDoors()) {
            door.state = DoorStateEnum.CLOSED;
        }
        assertTrue(plane.getSoftware().areDoorsLocked(DoorStateEnum.CLOSED));

        plane.getSoftware().openDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);
        assertTrue(plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN));
    }

    @Test
    public void testOpenDoorsMethod_withAllDoorsOpen_expectAllOpenImmediately() throws Exception {

        Plane plane = new Plane();
        for (Door door : plane.getDoors()) {
            door.state = DoorStateEnum.OPEN;
        }
        assertTrue(plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN));

        plane.getSoftware().openDoors();
        Thread.sleep(Door.INERTIA + ERROR_MARGIN);
        assertTrue(plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN));
    }

    @Test
    public void testRetractGearsMethod_withAllGearsDeployed_expectAllRetracted() throws Exception {

        Plane plane = new Plane();
        plane.getSoftware().openDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);
        for (LandingGear gear : plane.getLandingGears()) {
            gear.position = LandingGearPositionEnum.DEPLOYED;
        }
        assertTrue(plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN));
        assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.DEPLOYED));

        plane.getSoftware().retractGears();
        Thread.sleep(LandingGear.INERTIA + LandingGear.MOVING_TIME + ERROR_MARGIN);

        for (LandingGear gear : plane.getLandingGears()) {
            assertTrue(gear.isRetracted());
        }
    }


    @Test
    public void testCloseDoorsMethod_withAllGearsRetracted_expectAllRetractedImmediately() throws Exception {

        Plane plane = new Plane();
        if (!plane.getHandle().isUp()) {
            plane.getHandle().pushUp();
            Thread.sleep(TPS_CSTR1 + ERROR_MARGIN);
        }
        plane.getSoftware().openDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);
        assertTrue(plane.getHandle().isUp());
        assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.RETRACTED));
        assertTrue(plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN));

        plane.getSoftware().retractGears();
        Thread.sleep(LandingGear.INERTIA + ERROR_MARGIN);
        assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.RETRACTED));
    }

    @Test
    public void testDeployGearsMethod_withAllGearsRetracted_expectAllDeployed() throws Exception {

        Plane plane = new Plane();
        for (LandingGear gear : plane.getLandingGears()) {
            gear.position = LandingGearPositionEnum.RETRACTED;
        }
        plane.getSoftware().openDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);
        assertTrue(plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN));
        assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.RETRACTED));

        plane.getSoftware().deployGears();
        Thread.sleep(LandingGear.INERTIA + LandingGear.MOVING_TIME + ERROR_MARGIN);
        assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.DEPLOYED));
    }

    @Test
    public void testDeployGearsMethod_withAllGearsDeployed_expectAllDeployedImmediately() throws Exception {

        Plane plane = new Plane();
        plane.getSoftware().openDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);
        assertTrue(plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN));
        for (LandingGear gear : plane.getLandingGears()) {
            gear.position = LandingGearPositionEnum.DEPLOYED;
        }
        assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.DEPLOYED));

        plane.getSoftware().deployGears();
        Thread.sleep(LandingGear.INERTIA + ERROR_MARGIN);
        assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.DEPLOYED));
    }

    /**
     * R11: handle pushed down and stay down, all gears deployed and doors closed within 15s.
     */
    @Test
    public void testR11() throws Exception {

        // Worst case scenario: gears retracted and doors closed
        Plane plane = new Plane();
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
        Plane plane = new Plane();

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

        Plane plane = new Plane();

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
    public void testR31_stimulateDeployment_gearsDeployed() throws Exception {

        // Worst case scenario: gears deployed and doors closed
        Plane plane = new Plane();
        plane.getSoftware().openDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);
        plane.getSoftware().deployGears();
        Thread.sleep(LandingGear.INERTIA + LandingGear.MOVING_TIME + ERROR_MARGIN);
        assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.DEPLOYED));
        plane.getSoftware().closeDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);
        assertFalse(plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN));

        try {
            plane.getSoftware().deployGears();
            fail();
        } catch (IllegalActionException e) {
            // is the expected result
        }
    }

    /**
     * R31: cannot stimulate gears to deploy/retract when doors are not locked open.
     */
    @Test
    public void testR31_stimulateDeployment_gearsDeploying() throws Exception {

        // Worst case scenario: gears deployed and doors closed
        Plane plane = new Plane();
        plane.getSoftware().openDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);
        plane.getSoftware().deployGears();
        Thread.sleep(LandingGear.INERTIA + ERROR_MARGIN);
        assertFalse(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.RETRACTED));
        assertFalse(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.DEPLOYED));
        if (plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN)) {
            plane.getSoftware().getDoors()[0].state = DoorStateEnum.CLOSED;
        }
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);
        assertFalse(plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN));

        try {
            plane.getSoftware().deployGears();
            fail();
        } catch (IllegalActionException e) {
            // is the expected result
        }
    }

    /**
     * R31: cannot stimulate gears to deploy/retract when doors are not locked open.
     */
    @Test
    public void testR31_stimulateDeployment_gearsRetracted() throws Exception {

        // Worst case scenario: gears deployed and doors closed
        Plane plane = new Plane();
        plane.getSoftware().openDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);
        plane.getSoftware().retractGears();
        Thread.sleep(LandingGear.INERTIA + LandingGear.MOVING_TIME + ERROR_MARGIN);
        assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.RETRACTED));
        if (plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN)) {
            plane.getSoftware().getDoors()[0].state = DoorStateEnum.CLOSED;
        }
        assertFalse(plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN));

        try {
            plane.getSoftware().deployGears();
            fail();
        } catch (IllegalActionException e) {
            // is the expected result
        }
    }

    /**
     * R31: cannot stimulate gears to deploy/retract when doors are not locked open.
     */
    @Test
    public void testR31_stimulateRetracting_gearsDeployed() throws Exception {

        // Worst case scenario: gears deployed and doors closed
        Plane plane = new Plane();
        plane.getSoftware().openDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);
        plane.getSoftware().deployGears();
        Thread.sleep(LandingGear.INERTIA + LandingGear.MOVING_TIME + ERROR_MARGIN);
        assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.DEPLOYED));
        plane.getSoftware().closeDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);
        assertFalse(plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN));

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
    public void testR31_stimulateRetracting_gearsDeploying() throws Exception {

        // Worst case scenario: gears deployed and doors closed
        Plane plane = new Plane();
        plane.getSoftware().openDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);
        plane.getSoftware().deployGears();
        Thread.sleep(LandingGear.INERTIA + ERROR_MARGIN);
        assertFalse(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.RETRACTED));
        assertFalse(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.DEPLOYED));
        if (plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN)) {
            plane.getSoftware().getDoors()[0].state = DoorStateEnum.CLOSED;
        }
        assertFalse(plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN));

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
    public void testR31_stimulateRetracting_gearsRetracted() throws Exception {

        // Worst case scenario: gears deployed and doors closed
        Plane plane = new Plane();
        plane.getSoftware().openDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + ERROR_MARGIN);
        plane.getSoftware().retractGears();
        Thread.sleep(LandingGear.INERTIA + LandingGear.MOVING_TIME + ERROR_MARGIN);
        assertTrue(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.RETRACTED));
        if (plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN)) {
            plane.getSoftware().getDoors()[0].state = DoorStateEnum.CLOSED;
        }
        assertFalse(plane.getSoftware().areDoorsLocked(DoorStateEnum.OPEN));

        try {
            plane.getSoftware().retractGears();
            fail();
        } catch (IllegalActionException e) {
            // is the expected result
        }
    }

    /**
     * R32: cannot stimulate doors to open/close when gears are not locked (up or down).
     */
    @Test
    public void testR32_stimulateOpening() throws Exception {

        Plane plane = new Plane();
        plane.getSoftware().getLandingGears()[0].position = LandingGearPositionEnum.MOVING;
        assertFalse(plane.getSoftware().areGearsLocked(LandingGearPositionEnum.DEPLOYED)
                || plane.getSoftware().areGearsLocked(LandingGearPositionEnum.RETRACTED));

        try {
            plane.getSoftware().openDoors();
            fail();
        } catch (IllegalActionException e) {
            // is the expected result
        }
    }

    /**
     * R32: cannot stimulate doors to open/close when gears are not locked (up or down).
     */
    @Test
    public void testR32_stimulateClosing() {
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
