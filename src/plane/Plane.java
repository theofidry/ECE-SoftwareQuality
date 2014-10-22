package plane;

/**
 * Plane.
 */
public class Plane {

    public Handle        handle   = new Handle();
    public Lights        lights   = new Lights();
    public Software      software = new Software();
    public Wheels        wheels   = new Wheels();

    /**
     * Activate the software.
     */
    public void process() {

        software.process(handle, wheels, lights);
    }


}
