package view;

import junit.framework.TestCase;
import model.Plane;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

/**
 * Tests for {@link view.HandlePanelTest}
 */
public class HandlePanelTest extends TestCase {

    private Plane plane = new Plane();
    private HandlePanel handlePanel;
    private JSlider slider;

    @BeforeClass
    public void setUp() throws Exception {

        PilotInterface pilotInterface = new PilotInterface(plane);

        handlePanel = pilotInterface.getHandlePanel();

        for (Component component : handlePanel.getComponents()) {

            if (component instanceof JSlider) { slider = (JSlider) component; }
        }
    }


    @Test
    public void testSliderValuesLimits() throws Exception {

        slider.setValue(3);
        Assert.assertTrue(slider.getValue() == 1);

        slider.setValue(-1);
        Assert.assertTrue(slider.getValue() == 0);
    }

    @Test
    public void testModelToViewBinding() throws Exception {

        try {
            plane.getHandle().pushUp();
        } catch (IllegalStateException e) {
            Assert.assertTrue(slider.getValue() == 1);
        }

        plane.getHandle().pushDown();
        Assert.assertTrue(slider.getValue() == 0);

        plane.getHandle().pushUp();
        Assert.assertTrue(slider.getValue() == 1);
    }

    @Test
    public void testViewToModelBinding() throws Exception {

        try {
            slider.setValue(0);
        } catch (IllegalStateException e) {
            Assert.assertFalse(plane.getHandle().isUp());
        }

        slider.setValue(1);
        Assert.assertTrue(plane.getHandle().isUp());

        try {
            slider.setValue(0);
        } catch (IllegalStateException e) {
            Assert.assertFalse(plane.getHandle().isUp());
        }
    }
}
