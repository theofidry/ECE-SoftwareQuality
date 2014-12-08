package view;

import model.Door;
import model.enums.DoorStateEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.swing.*;
import java.awt.TextField;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Tests for {@link view.DoorPanel}
 */
@RunWith(Parameterized.class)
public class DoorPanelTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {


        ArrayList<Object[]> provider = new ArrayList<>();
        Door door = new Door();

        for (DoorStateEnum state: DoorStateEnum.values()) {

            door.state = state;
            provider.add(new Object[] {door});
        }

        return provider;
    }

    private Door door;

    public DoorPanelTest(Door door) {
        this.door = door;
    }

    @Test
    public void testInstantiation_withProperString_expectSuccess() {

        DoorPanel doorPanel = new DoorPanel(door, "My door");

        JLabel label;
        TextField field;

        Assert.assertTrue(doorPanel.getComponentCount() == 2);

        try {
            label = (JLabel)doorPanel.getComponent(0);
            field = (TextField)doorPanel.getComponent(1);

            Assert.assertTrue(label.getText().equals("My door"));
            if (door.isOpen())
                Assert.assertTrue(field.getText().equals("Open"));
            if (door.isClosed())
                Assert.assertTrue(field.getText().equals("Closed"));
            if (door.isMoving())
                Assert.assertTrue(field.getText().equals("Moving"));
        } catch (Exception e) {
            Assert.fail("Unexpected exception.");
        }
    }

    @Test
    public void testInstantiation_withEmptyString_expectDefaultValue() {

        DoorPanel doorPanel = new DoorPanel(door, "");

        JLabel label;
        TextField field;

        Assert.assertTrue(doorPanel.getComponentCount() == 2);

        try {
            label = (JLabel)doorPanel.getComponent(0);
            field = (TextField)doorPanel.getComponent(1);

            Assert.assertTrue(label.getText().equals("Door"));
            if (door.isOpen())
                Assert.assertTrue(field.getText().equals("Open"));
            if (door.isClosed())
                Assert.assertTrue(field.getText().equals("Closed"));
            if (door.isMoving())
                Assert.assertTrue(field.getText().equals("Moving"));
        } catch (Exception e) {
            Assert.fail("Unexpected exception.");
        }
    }
}
