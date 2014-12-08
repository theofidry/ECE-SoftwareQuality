package model;

import edu.umd.cs.mtc.MultithreadedTestCase;
import model.enums.DoorStateEnum;
import org.junit.Assert;
import org.junit.Test;

/**
* Tests for {@link model.Door}.
*/
public class DoorTest extends MultithreadedTestCase {

    @Test
    public void testIfDefaultDoorStateIsSetProperly_expectIsClosed() {

        Door door = new Door();
        Assert.assertTrue(door.isClosed());
    }

    @Test
    public void testStateCheckers_testIsOpenMethod() {

        Door door = new Door();

        door.state = DoorStateEnum.OPEN;
        Assert.assertTrue(door.isOpen());
        door.state = DoorStateEnum.CLOSED;
        Assert.assertFalse(door.isOpen());
        door.state = DoorStateEnum.MOVING;
        Assert.assertFalse(door.isOpen());
    }

    @Test
    public void testStateCheckers_testIsClosedMethod() {

        Door door = new Door();

        door.state = DoorStateEnum.CLOSED;
        Assert.assertTrue(door.isClosed());
        door.state = DoorStateEnum.OPEN;
        Assert.assertFalse(door.isClosed());
        door.state = DoorStateEnum.MOVING;
        Assert.assertFalse(door.isClosed());
    }

    @Test
    public void testStateCheckers_testIsMovingMethod() {

        Door door = new Door();

        door.state = DoorStateEnum.MOVING;
        Assert.assertTrue(door.isMoving());
        door.state = DoorStateEnum.OPEN;
        Assert.assertFalse(door.isMoving());
        door.state = DoorStateEnum.CLOSED;
        Assert.assertFalse(door.isMoving());
    }

    @Test
    public void testOpenMethod_normalBehavior_expectDoorOpenAfterMovingTime() {

        Door door = new Door();

        try {

            door.state = DoorStateEnum.CLOSED;
            door.open();
            Thread.sleep(Door.INERTIA + Door.MOVING_TIME + 100);
            assertTrue(door.isOpen());
        } catch (InterruptedException e) {
            fail("Unexpected exception.");
        }
    }

    @Test
    public void testOpenMethod_whenAlreadyOpened_expectDoorOpenImmedialty() {

        Door door = new Door();

        try {

            door.state = DoorStateEnum.OPEN;
            door.open();
            assertFalse(door.isMoving());
            Thread.sleep(Door.INERTIA + 100);
            assertFalse(door.isMoving());

        } catch (InterruptedException e) {
            fail("Unexpected exception.");
        }
    }

    @Test
    public void testOpenMethod_whenClosing_expectDoorOpenAfterMovingTime() {

        Door door = new Door();

        try {

            long sleepTime = (long)(Door.INERTIA + (Door.MOVING_TIME * 0.5));
            door.state = DoorStateEnum.OPEN;
            door.close();
            Thread.sleep(sleepTime);
            door.open();
            Thread.sleep(Door.INERTIA + Door.MOVING_TIME + 100);
            assertTrue(door.isOpen());

        } catch (InterruptedException e) {
            fail("Unexpected exception.");
        }
    }

    @Test
    public void testCloseMethod_normalBehavior_expectDoorOpenAfterMovingTime() {

        Door door = new Door();

        try {

            door.state = DoorStateEnum.OPEN;
            door.close();
            Thread.sleep(Door.INERTIA + Door.MOVING_TIME + 100);
            assertTrue(door.isClosed());
        } catch (InterruptedException e) {
            fail("Unexpected exception.");
        }
    }

    @Test
    public void testCloseMethod_whenAlreadyOpened_expectDoorClosedImmedialty() {

        Door door = new Door();

        try {

            door.state = DoorStateEnum.CLOSED;
            door.close();
            assertFalse(door.isMoving());
            Thread.sleep(Door.INERTIA + 100);
            assertFalse(door.isMoving());

        } catch (InterruptedException e) {
            fail("Unexpected exception.");
        }
    }

    @Test
    public void testCloseMethod_whenClosing_expectDoorOpenAfterMovingTime() {

        Door door = new Door();

        try {

            long sleepTime = (long)(Door.INERTIA + (Door.MOVING_TIME * 0.5));
            door.state = DoorStateEnum.CLOSED;
            door.open();
            Thread.sleep(sleepTime);
            door.close();
            Thread.sleep(Door.INERTIA + Door.MOVING_TIME + 100);
            assertTrue(door.isClosed());

        } catch (InterruptedException e) {
            fail("Unexpected exception.");
        }
    }
}
