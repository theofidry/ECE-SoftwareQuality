package view;

import model.LandingGear;
import model.enums.LandingGearPositionEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Tests for {@link view.LandingGearPanel}
 */
@RunWith(Parameterized.class)
public class LandingGearPanelTest {

    private LandingGear gear = new LandingGear();

    public LandingGearPanelTest(LandingGear gear) {
        this.gear = gear;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {


        ArrayList<Object[]> provider = new ArrayList<>();
        LandingGear gear = new LandingGear();

        for (LandingGearPositionEnum position : LandingGearPositionEnum.values()) {

            gear.position = position;
            provider.add(new Object[] {gear});
        }

        return provider;
    }

    @Test
    public void testInstantiation_withProperString_expectSuccess() {

        LandingGearPanel gearPanel = new LandingGearPanel(gear, "My gear");

        JLabel label;
        TextField field;

        Assert.assertTrue(gearPanel.getComponentCount() == 2);

        try {
            label = (JLabel) gearPanel.getComponent(0);
            field = (TextField) gearPanel.getComponent(1);

            Assert.assertTrue(label.getText().equals("My gear"));
            if (gear.isDeployed())
                Assert.assertTrue(field.getText().equals("Deployed"));
            if (gear.isRetracted())
                Assert.assertTrue(field.getText().equals("Retracted"));
            if (gear.isMoving())
                Assert.assertTrue(field.getText().equals("Moving"));
        } catch (Exception e) {
            Assert.fail("Unexpected exception.");
        }
    }

    @Test
    public void testInstantiation_withEmptyString_expectDefaultValue() {

        LandingGearPanel gearPanel = new LandingGearPanel(gear, "");

        JLabel label;
        TextField field;

        Assert.assertTrue(gearPanel.getComponentCount() == 2);

        try {
            label = (JLabel) gearPanel.getComponent(0);
            field = (TextField) gearPanel.getComponent(1);

            Assert.assertTrue(label.getText().equals("Gear"));
            if (gear.isDeployed())
                Assert.assertTrue(field.getText().equals("Deployed"));
            if (gear.isRetracted())
                Assert.assertTrue(field.getText().equals("Retracted"));
            if (gear.isMoving())
                Assert.assertTrue(field.getText().equals("Moving"));
        } catch (Exception e) {
            Assert.fail("Unexpected exception.");
        }
    }
}
