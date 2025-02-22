package io.github.ardentengine.core.input;

import io.github.ardentengine.core.math.MathUtils;
import io.github.ardentengine.core.math.Vector2;

/**
 * Input event that represents a scroll on a mouse wheel or touchpad.
 *
 * @param horizontal Horizontal scroll.
 * @param vertical Vertical scroll.
 */
public record InputEventScroll(float horizontal, float vertical) implements InputEvent {

    /**
     * Input event that represents a scroll on a mouse wheel or touchpad.
     *
     * @param scroll Scroll on the x and y axes.
     */
    public InputEventScroll(Vector2 scroll) {
        this(scroll.x(), scroll.y());
    }

    /**
     * Returns the scroll on the x and y axes.
     *
     * @return The scroll on the x and y axes.
     */
    public Vector2 scroll() {
        return new Vector2(this.horizontal(), this.vertical());
    }

    @Override
    public boolean isPressed() {
        return false;
    }

    @Override
    public boolean isReleased() {
        return false;
    }

    @Override
    public float strength() {
        return Math.max(this.horizontal(), this.vertical());
    }

    @Override
    @SuppressWarnings("DeconstructionCanBeUsed")
    public boolean matches(InputEvent event, boolean exact) {
        return event instanceof InputEventScroll eventScroll
            && MathUtils.equalsApprox(this.horizontal(), eventScroll.horizontal())
            && MathUtils.equalsApprox(this.vertical(), eventScroll.vertical());
    }
}
