//import junit.framework.TestCase;
//import model.Handle;
//import org.junit.Assert;
//import org.junit.Test;
//
///**
//* Tests for {@link model.Handle}
//*/
//public class HandleTest extends TestCase {
//
//    @Test
//    public void testDefaultHandleState() {
//
//        Handle handle = new Handle();
//        Assert.assertTrue(handle.isUp());
//    }
//
//    @Test
//    public void testIsUp() {
//
//        Handle handle = new Handle();
//
//        handle.position = false;
//        Assert.assertFalse(handle.isUp());
//
//        handle.position = true;
//        Assert.assertTrue(handle.isUp());
//    }
//
//    @Test
//    public void testPushUp() {
//
//        Handle handle = new Handle();
//
//        handle.pushDown();
//        Assert.assertFalse(handle.isUp());
//
//        handle.pushUp();
//        Assert.assertTrue(handle.isUp());
//    }
//
//    @Test
//    public void testPushDown() {
//
//        Handle handle = new Handle();
//
//        handle.pushUp();
//        Assert.assertTrue(handle.isUp());
//
//        handle.pushDown();
//        Assert.assertFalse(handle.isUp());
//    }
//}
