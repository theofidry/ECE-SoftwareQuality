package view;

import model.Door;
import model.Lights;
import model.enums.DoorStateEnum;
import model.enums.LightsColorEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Tests for {@link view.LightsPanel}
 */
@RunWith(Parameterized.class)
public class LightsPanelTest {

    private Lights lights = new Lights();

    @Parameterized.Parameters
    public static Collection<Object[]> data() {


        ArrayList<Object[]> provider = new ArrayList<>();
        Lights lights = new Lights();

        for (LightsColorEnum color: LightsColorEnum.values()) {

            lights.setColor(color);
            provider.add(new Object[] {lights});
        }

        return provider;
    }

    @Test
    public void testInstantiation() {

        LightsPanel lightsPanel = new LightsPanel(lights);

        JLabel label;
        TextField field;

        Assert.assertTrue(lightsPanel.getComponentCount() == 2);

        try {
            label = (JLabel)lightsPanel.getComponent(0);
            field = (TextField)lightsPanel.getComponent(1);

            Assert.assertTrue(label.getText().equals("Lights"));
            if (lights.getColor() == LightsColorEnum.OFF)
                Assert.assertTrue(field.getText().equals("Off"));
            if (lights.getColor() == LightsColorEnum.GREEN)
                Assert.assertTrue(field.getText().equals("Green"));
            if (lights.getColor() == LightsColorEnum.ORANGE)
                Assert.assertTrue(field.getText().equals("Orange"));
            if (lights.getColor() == LightsColorEnum.RED)
                Assert.assertTrue(field.getText().equals("Red"));
        } catch (Exception e) {
            Assert.fail("Unexpected exception.");
        }
    }
}
