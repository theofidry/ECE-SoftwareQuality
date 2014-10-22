import plane.Plane;

/**
 * Thread handling the actions following the activation of a handle.
 */
public class HandleThread extends Thread {

    public static final int SLEEPTIME_1 = 2000;
    public static final int SLEEPTIME_2 = 2000;

    private GearInterface view;
    private Plane         plane;

    /**
     *
     * @param view
     * @param plane
     */
    public HandleThread(GearInterface view, Plane plane) {

        this.view  = view;
        this.plane = plane;
    }

    @Override
    public void run() {

        try {

            // wait a moment before taking action
            Thread.sleep(SLEEPTIME_1);

            plane.process();    // process the plane software
            view.updateView();  // update the view

            // wait a moment before taking action
            Thread.sleep(SLEEPTIME_2);

            plane.process();    // process the plane software
            view.updateView();  // update the view

        } catch (InterruptedException e) {
            // do nothing: is when the handle has switch of position
        }
    }
}
