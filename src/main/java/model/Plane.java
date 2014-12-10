package model;

/**
 * Plane.
 */
public class Plane {

    private Door[] doors = {new Door(), new Door(), new Door()};
    private Handle handle = new Handle();
    private Lights lights = new Lights();
    private Software software;
    private LandingGear[] landingGears = {new LandingGear(), new LandingGear(), new LandingGear()};

    public Door[] getDoors() {
        return doors;
    }

    public Handle getHandle() {
        return handle;
    }

    public Lights getLights() {
        return lights;
    }

    public Software getSoftware() {
        return software;
    }

    public LandingGear[] getLandingGears() {
        return landingGears;
    }

    public Plane() {

        software = new Software(doors, handle, lights, landingGears);
    }

    /**
     * Process the software.
     */
    public void process() {

        software.process();
    }


}
