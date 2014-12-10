package model;


import edu.umd.cs.mtc.MultithreadedTestCase;
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

    public static Door[] doors = {new Door(), new Door(), new Door()};
    public static LandingGear[] gears = {new LandingGear(), new LandingGear(), new LandingGear()};
    public static Software software;

    @BeforeClass
    public static void setUp() throws Exception {

        for (Door door : doors) {
            door = new Door();
        }

        for (LandingGear gear: gears) {
            gear = new LandingGear();
        }

        software = new Software(doors, new Handle(), new Lights(), gears);
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
}
