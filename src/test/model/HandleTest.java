package model;

import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for {@link model.Handle}
 */
public class HandleTest extends TestCase {

    private Handle handle;

    @BeforeClass
    public void setUp() throws Exception {
        handle = new Handle();
    }

    public void testDefaultPosition() throws Exception {
        assertFalse(handle.isUp());
    }

    @Test
    public void testPushUp() throws Exception {
        handle.pushUp();
        assertTrue(handle.isUp());
    }

    @Test
    public void testPushDown() throws Exception {
        handle.pushDown();
        assertFalse(handle.isUp());
    }
}
