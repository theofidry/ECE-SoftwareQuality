package model;


import edu.umd.cs.mtc.MultithreadedTestCase;
import model.enums.DoorStateEnum;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for {@link model.Software}
 */
public class SoftwareTest extends MultithreadedTestCase {

    public static Door[] doors = {new Door(), new Door(), new Door()};
    public static Software software;

    @BeforeClass
    public static void setUp() throws Exception {

        for (Door door : doors) {
            door = new Door();
        }

        software = new Software(doors, new Handle(), new Lights(), new LandingGear[]{});
    }

    @Test
    public void testCloseDoorsMethod_withAllDoorsOpen_expectAllClosed() throws Exception {

        for (Door door : doors) {
            door.state = DoorStateEnum.OPEN;
        }

        software = new Software(doors, new Handle(), new Lights(), new LandingGear[]{});
        software.closeDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + 100);

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

        software = new Software(doors, new Handle(), new Lights(), new LandingGear[]{});
        software.closeDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + 100);

        for (Door door : software.getDoors()) {
            assertTrue(door.isClosed());
        }
    }

    @Test
    public void testCloseDoorsMethod_withAllDoorsClosed_expectAllClosedImmetiately() throws Exception {

        for (Door door : doors) {
            door.state = DoorStateEnum.CLOSED;
        }

        software = new Software(doors, new Handle(), new Lights(), new LandingGear[]{});
        software.closeDoors();

        for (Door door : software.getDoors()) {
            assertTrue(door.isClosed());
        }
    }

    @Test
    public void testOpenDoorsMethod_withAllDorsClosed_expectAllOpen() throws Exception {

        for (Door door : doors) {
            door.state = DoorStateEnum.CLOSED;
        }

        software = new Software(doors, new Handle(), new Lights(), new LandingGear[]{});
        software.openDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + 100);

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

        software = new Software(doors, new Handle(), new Lights(), new LandingGear[]{});
        software.openDoors();
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + 100);

        for (Door door : software.getDoors()) {
            assertTrue(door.isOpen());
        }
    }

    @Test
    public void testOpenDoorsMethod_withAllDoorsOpen_expectAllOpenImmetiately() throws Exception {

        for (Door door : doors) {
            door.state = DoorStateEnum.OPEN;
        }

        software = new Software(doors, new Handle(), new Lights(), new LandingGear[]{});
        software.openDoors();

        for (Door door : software.getDoors()) {
            assertTrue(door.isOpen());
        }
    }
}
