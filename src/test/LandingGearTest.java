//import edu.umd.cs.mtc.MultithreadedTestCase;
//import model.LandingGear;
//import model.enums.LandingGearPositionEnum;
//import org.junit.Assert;
//import org.junit.Test;
//
///**
// * Tests for {@link model.LandingGear}
// */
//public class LandingGearTest extends MultithreadedTestCase {
//
//    @Tests
//    public void testDefaultValue() {
//
//        LandingGear landingGear = new LandingGear();
//        Assert.assertTrue(landingGear.isRetracted());
//    }
//
//    @Test
//    public void testPositionsCheckers() {
//
//        LandingGear gear = new LandingGear();
//
//        // test isOpen()
//        gear.position = LandingGearPositionEnum.DEPLOYED;
//        Assert.assertTrue(gear.isDeployed());
//        gear.position = LandingGearPositionEnum.RETRACTED;
//        Assert.assertFalse(gear.isDeployed());
//        gear.position = LandingGearPositionEnum.MOVING;
//        Assert.assertFalse(gear.isDeployed());
//
//        // test isClosed()
//        gear.position = LandingGearPositionEnum.RETRACTED;
//        Assert.assertTrue(gear.isRetracted());
//        gear.position = LandingGearPositionEnum.DEPLOYED;
//        Assert.assertFalse(gear.isRetracted());
//        gear.position = LandingGearPositionEnum.MOVING;
//        Assert.assertFalse(gear.isRetracted());
//
//        // test isMoving()
//        gear.position = LandingGearPositionEnum.MOVING;
//        Assert.assertTrue(gear.isMoving());
//        gear.position = LandingGearPositionEnum.DEPLOYED;
//        Assert.assertFalse(gear.isMoving());
//        gear.position = LandingGearPositionEnum.RETRACTED;
//        Assert.assertFalse(gear.isMoving());
//    }
//
//    @Test
//    public void testDeploy() {
//
//        LandingGear gear = new LandingGear();
//
//        try {
//
//            // Nominal work
//            gear.position = LandingGearPositionEnum.RETRACTED;
//            gear.deploy();
//            Thread.sleep(LandingGear.INERTIA + LandingGear.MOVING_TIME);
//            assertTrue(gear.isDeployed());
//
//            // Is already open
//            gear.deploy();
//            assertFalse(gear.isMoving());
//            Thread.sleep(LandingGear.INERTIA * 2);
//            assertFalse(gear.isMoving());
//
//            // Ask to deploy when retracting
//            long sleepTime = (long) (LandingGear.INERTIA + (LandingGear.MOVING_TIME * 0.5));
//            gear.position = LandingGearPositionEnum.DEPLOYED;
//            gear.retract();
//            Thread.sleep(sleepTime);
//            gear.deploy();
//            Thread.sleep(sleepTime);
//            assertTrue(gear.isDeployed());
//
//            gear.position = LandingGearPositionEnum.DEPLOYED;
//            gear.retract();
//            Thread.sleep(2000);
//            gear.deploy();
//            Thread.sleep(1000);
//            assertTrue(gear.isMoving());
//
//        } catch (InterruptedException e) {
//            fail("Unexpected exception.");
//        }
//    }
//
//    @Test
//    public void testRetract() {
//
//        LandingGear gear = new LandingGear();
//
//        gear.position = LandingGearPositionEnum.DEPLOYED;
//        gear.retract();
//        try {
//
//            // Nominal work
//            gear.position = LandingGearPositionEnum.DEPLOYED;
//            gear.retract();
//            Thread.sleep(LandingGear.INERTIA + LandingGear.MOVING_TIME);
//            assertTrue(gear.isRetracted());
//
//            // Is already open
//            gear.retract();
//            assertFalse(gear.isMoving());
//            Thread.sleep(LandingGear.INERTIA * 2);
//            assertFalse(gear.isMoving());
//
//            // Ask to retract it when deploying
//            long sleepTime = (long) (LandingGear.INERTIA + (LandingGear.MOVING_TIME * 0.5));
//            gear.position = LandingGearPositionEnum.RETRACTED;
//            gear.deploy();
//            Thread.sleep(sleepTime);
//            gear.retract();
//            Thread.sleep(sleepTime);
//            assertTrue(gear.isRetracted());
//
//            gear.position = LandingGearPositionEnum.RETRACTED;
//            gear.deploy();
//            Thread.sleep(2000);
//            gear.retract();
//            Thread.sleep(1000);
//            assertTrue(gear.isMoving());
//        } catch (InterruptedException e) {
//            fail("Unexpected exception.");
//        }
//    }
//}
