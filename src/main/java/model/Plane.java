package model;

/**
 * Plane.
 */
public class Plane {

    public Door[]        doors        = {new Door(), new Door(), new Door()};
    public Handle        handle       = new Handle();
    public Lights        lights       = new Lights();
    public Software      software     = new Software();
    public LandingGear[] landingGears = {new LandingGear(), new LandingGear(), new LandingGear()};

    /**
     * Activate the software.
     */
    public void process() {

        software.process(doors, handle, landingGears, lights);
    }


}
