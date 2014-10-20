package plane;

/**
 * Plane.
 */
public class Plane {

    public Handle        handle   = new Handle();
    public Lights        lights   = new Lights();
    public Software      software = new Software();
    public Wheels        wheels   = new Wheels();
    public GearInterface view     = new GearInterface();

    /**
     * Activate the software.
     */
    public void process() {

        software.process(view, handle, wheels, lights);
    }


}
