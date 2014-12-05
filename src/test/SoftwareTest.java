import junit.framework.TestCase;
import org.junit.Test;

/**
 * Tests for {@link java.model.Software}
 */
public class SoftwareTest extends TestCase {

    /**
     * R11: handle pushed down and stay down, all gears deployed and doors closed within 15s.
     */
    @Test
    public void testR11() {
        //TODO
    }

    /**
     * R12: handle pushed up and stay yup, all gears retracted and doors closed within 15s.
     */
    @Test
    public void testR12() {
        //TODO
    }

    /**
     * R21: when handle is down, cannot retract gears.
     */
    @Test
    public void testR21() {
        //TODO
    }

    /**
     * R22: handle pushed down and stay down, all gears deployed and doors closed within 15s.
     */
    @Test
    public void testR22() {
        //TODO
    }

    /**
     * R31: cannot stimulate gears to deploy/retract when doors are opened.
     */
    @Test
    public void testR31() {
        //TODO
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
}
