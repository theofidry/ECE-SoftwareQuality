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
    public static Software software = new Software();

    @BeforeClass
    public static void setUp() throws Exception {

        for (Door door: doors) {
            door = new Door();
        }
    }

    @Test
    public void testCloseDoorsMethod_withAllDorsOpen_expectAllCLosed() throws Exception {

        for (Door door: doors) {
            door.state = DoorStateEnum.OPEN;
        }

        software.closeDoors(doors);
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + 100);

        for (Door door: doors) {
            assertTrue(door.isClosed());
        }
    }

    @Test
    public void testCloseDoorsMethod_withVariousStates_expectAllClosedImmediatly() throws Exception {

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

        software.closeDoors(doors);
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + 100);

        for (Door door: doors) {
            assertTrue(door.isClosed());
        }
    }

    @Test
    public void testCloseDoorsMethod_withAllDorsClosed_expectAllClosedImmetiatly() throws Exception {

        for (Door door: doors) {
            door.state = DoorStateEnum.CLOSED;
        }

        software.closeDoors(doors);

        for (Door door: doors) {
            assertTrue(door.isClosed());
        }
    }

    @Test
    public void testOpenDoorsMethod_withAllDorsClosed_expectAllOpen() throws Exception {

        for (Door door: doors) {
            door.state = DoorStateEnum.CLOSED;
        }

        software.openDoors(doors);
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + 100);

        for (Door door: doors) {
            assertTrue(door.isOpen());
        }
    }

    @Test
    public void testOpenDoorsMethod_withVariousStates_expectAllOpenImmediatly() throws Exception {

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

        software.openDoors(doors);
        Thread.sleep(Door.INERTIA + Door.MOVING_TIME + 100);

        for (Door door: doors) {
            assertTrue(door.isOpen());
        }
    }

    @Test
    public void testOpenDoorsMethod_withAllDorsOpen_expectAllOpenImmetiatly() throws Exception {

        for (Door door: doors) {
            door.state = DoorStateEnum.OPEN;
        }

        software.openDoors(doors);

        for (Door door: doors) {
            assertTrue(door.isOpen());
        }
    }
}
