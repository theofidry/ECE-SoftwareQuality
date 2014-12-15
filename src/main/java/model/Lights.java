package model;

import model.enums.LightsColorEnum;

/**
 * Lights system
 */
public class Lights extends Model {

    /**
     * Color of the lights.
     */
    private LightsColorEnum color = LightsColorEnum.OFF;

    /**
     * Gets this lights color.
     *
     * @return lights color
     */
    public LightsColorEnum getColor() {
        return color;
    }

    /**
     * Sets the lights color.
     *
     * @param color color which will be applied
     */
    public void setColor(LightsColorEnum color) {
        this.color = color;
        changeSupport.firePropertyChange("color", null, color);
    }
}
