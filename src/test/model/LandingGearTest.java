package model;

import edu.umd.cs.mtc.MultithreadedTestCase;
import model.enums.DoorStateEnum;
import model.enums.LandingGearPositionEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link model.LandingGear}
 */
public class LandingGearTest extends MultithreadedTestCase {

    /**
     * Time error margin in ms
     */
    public static final int ERROR_MARGIN = 100;

    private static LandingGear gear;

    @BeforeClass
    public static void setUp() throws Exception {

        gear = new LandingGear();
    }

    @Test
    public void testIfDefaultDoorStateIsSetProperly_expectIsClosed() {

        Assert.assertTrue(gear.isRetracted());
    }

    @Test
    public void testStateCheckers_testIsOpenMethod() {

        gear.position = LandingGearPositionEnum.DEPLOYED;
        Assert.assertTrue(gear.isDeployed());
        gear.position = LandingGearPositionEnum.RETRACTED;
        Assert.assertFalse(gear.isDeployed());
        gear.position = LandingGearPositionEnum.MOVING;
        Assert.assertFalse(gear.isDeployed());
    }

    @Test
    public void testStateCheckers_testIsClosedMethod() {

        gear.position = LandingGearPositionEnum.RETRACTED;
        Assert.assertTrue(gear.isRetracted());
        gear.position = LandingGearPositionEnum.DEPLOYED;
        Assert.assertFalse(gear.isRetracted());
        gear.position = LandingGearPositionEnum.MOVING;
        Assert.assertFalse(gear.isRetracted());
    }

    @Test
    public void testStateCheckers_testIsMovingMethod() {

        gear.position = LandingGearPositionEnum.MOVING;
        Assert.assertTrue(gear.isMoving());
        gear.position = LandingGearPositionEnum.DEPLOYED;
        Assert.assertFalse(gear.isMoving());
        gear.position = LandingGearPositionEnum.RETRACTED;
        Assert.assertFalse(gear.isMoving());
    }

    @Test
    public void testOpenMethod_normalBehavior_expectDoorOpenAfterMovingTime() {

        try {

            gear.position = LandingGearPositionEnum.RETRACTED;
            gear.deploy();
            Thread.sleep(gear.INERTIA + gear.MOVING_TIME + ERROR_MARGIN);
            assertTrue(gear.isDeployed());
        } catch (InterruptedException e) {
            fail("Unexpected exception.");
        }
    }

    @Test
    public void testOpenMethod_whenAlreadyOpened_expectDoorOpenImmedialty() {

        try {

            gear.position = LandingGearPositionEnum.DEPLOYED;
            gear.deploy();
            assertFalse(gear.isMoving());
            Thread.sleep(gear.INERTIA + ERROR_MARGIN);
            assertFalse(gear.isMoving());

        } catch (InterruptedException e) {
            fail("Unexpected exception.");
        }
    }

    @Test
    public void testOpenMethod_whenClosing_expectDoorOpenAfterMovingTime() {

        try {

            long sleepTime = (long) (gear.INERTIA + (gear.MOVING_TIME * 0.5));
            gear.position = LandingGearPositionEnum.DEPLOYED;
            gear.retract();
            Thread.sleep(sleepTime);
            gear.deploy();
            Thread.sleep(sleepTime + ERROR_MARGIN);
            assertTrue(gear.isDeployed());

        } catch (InterruptedException e) {
            fail("Unexpected exception.");
        }
    }

    @Test
    public void testCloseMethod_normalBehavior_expectDoorOpenAfterMovingTime() {

        try {

            gear.position = LandingGearPositionEnum.DEPLOYED;
            gear.retract();
            Thread.sleep(gear.INERTIA + gear.MOVING_TIME + ERROR_MARGIN);
            assertTrue(gear.isRetracted());
        } catch (InterruptedException e) {
            fail("Unexpected exception.");
        }
    }

    @Test
    public void testCloseMethod_whenAlreadyOpened_expectDoorClosedImmediately() {

        try {

            gear.position = LandingGearPositionEnum.RETRACTED;
            gear.retract();
            assertFalse(gear.isMoving());
            Thread.sleep(gear.INERTIA + ERROR_MARGIN);
            assertFalse(gear.isMoving());

        } catch (InterruptedException e) {
            fail("Unexpected exception.");
        }
    }

    @Test
    public void testCloseMethod_whenClosing_expectDoorOpenAfterMovingTime() {

        try {

            long sleepTime = (long) (gear.INERTIA + (gear.MOVING_TIME * 0.5));
            gear.position = LandingGearPositionEnum.RETRACTED;
            gear.deploy();
            Thread.sleep(sleepTime);
            gear.retract();
            Thread.sleep(sleepTime + ERROR_MARGIN);  // the +100 is the error margin
            assertTrue(gear.isRetracted());

        } catch (InterruptedException e) {
            fail("Unexpected exception.");
        }
    }
}
