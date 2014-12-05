package model;

import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;
import model.enums.DoorStateEnum;

import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Plane doors.
 */
public class Door {

    //
    // ATTRIBUTES
    //
    /**
     * Inertia of the door in ms.
     */
    public static final int INERTIA = 200;

    /**
     * Time a door takes to fully open or close.
     */
    public static final int MOVING_TIME = 3000;

    /**
     * State of the door.
     */
    public DoorStateEnum state = DoorStateEnum.CLOSED;

    private Timer timer = new Timer();
    private long start;

    private final ExtendedPropertyChangeSupport changeSupport = new ExtendedPropertyChangeSupport(this);


    public void addPropertyChangeListener(PropertyChangeListener x) {
        changeSupport.addPropertyChangeListener(x);
    }

    public void removePropertyChangeListener(PropertyChangeListener x) {
        changeSupport.removePropertyChangeListener(x);
    }


    //
    // METHODS
    //

    /**
     * Check if the door is locked open.
     *
     * @return
     */
    public boolean isOpen() {
        return (this.state == DoorStateEnum.OPEN);
    }

    /**
     * Check if the door is locked closed.
     *
     * @return
     */
    public boolean isClosed() {
        return (this.state == DoorStateEnum.CLOSED);
    }

    public boolean isMoving() {
        return (this.state == DoorStateEnum.MOVING);
    }

    /**
     * Close the door.
     */
    public void close() {
        moves(DoorStateEnum.CLOSED);
    }

    /**
     * Open the door.
     */
    public void open() {
        moves(DoorStateEnum.OPEN);
    }


    //
    // HELPERS
    //

    /**
     * Function to implement moving.
     *
     * @param finalState final state to reach
     */
    private void moves(final DoorStateEnum finalState) {

        long movingTime = MOVING_TIME;

        // interrupts all tasks and remove them from the scheduler
        if (state == DoorStateEnum.MOVING) {
            timer.purge();
            movingTime -= System.currentTimeMillis();
        }

        if (state != finalState) {

            final TimerTask taskFinal = new TimerTask() {
                @Override
                public void run() {
                    state = finalState;
                    changeSupport.firePropertyChange("state", null, state);
                }


            };

            TimerTask taskMoving = new TimerTask() {
                @Override
                public void run() {
                    start = System.currentTimeMillis();
                    state = DoorStateEnum.MOVING;
                    changeSupport.firePropertyChange("state", null, state);
                    timer.schedule(taskFinal, MOVING_TIME);
                }
            };

            timer.schedule(taskMoving, INERTIA);
        }
    }
}
