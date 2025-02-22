package io.github.ardentengine.core.input;

import io.github.ardentengine.core.math.Vector2;

/**
 * An input event that represents a mouse button being pressed or released.
 *
 * @param button The mouse button identifier.
 * @param modifiers Bitmask for keyboard modifiers.
 * @param position The position of the mouse from the top-left corner of the window.
 * @param isPressed If true, the mouse button's state is pressed. If false, the mouse button's state is released.
 */
public record InputEventMouseButton(int button, int modifiers, Vector2 position, boolean isPressed) implements InputEventMouse, InputEventWithModifiers {

    /** Index of the left mouse button. */
    public static final int BUTTON_LEFT = 0;
    /** Index of the right mouse button. */
    public static final int BUTTON_RIGHT = 1;
    /** Index of the middle mouse button. */
    public static final int BUTTON_MIDDLE = 2;
    /** Index of the fourth mouse button. */
    public static final int BUTTON_4 = 3;
    /** Index of the fifth mouse button. */
    public static final int BUTTON_5 = 4;
    /** Index of the sixth mouse button. */
    public static final int BUTTON_6 = 5;
    /** Index of the seventh mouse button. */
    public static final int BUTTON_7 = 6;
    /** Index of the eighth mouse button. */
    public static final int BUTTON_8 = 7;

    /**
     * An input event that represents a mouse button being pressed or released.
     *
     * @param button The mouse button identifier.
     * @param modifiers Bitmask for keyboard modifiers.
     * @param x The x position of the mouse from the left side of the window.
     * @param y The y position of the mouse from the top side of the window.
     * @param isPressed If true, the mouse button's state is pressed. If false, the mouse button's state is released.
     */
    public InputEventMouseButton(int button, int modifiers, float x, float y, boolean isPressed) {
        this(button, modifiers, new Vector2(x, y), isPressed);
    }

    /**
     * An input event that represents a mouse button being pressed.
     *
     * @param button The mouse button identifier.
     * @param modifiers Bitmask for keyboard modifiers.
     * @param position The position of the mouse from the top-left corner of the window.
     */
    public InputEventMouseButton(int button, int modifiers, Vector2 position) {
        this(button, modifiers, position, true);
    }

    /**
     * An input event that represents a mouse button being pressed.
     *
     * @param button The mouse button identifier.
     * @param modifiers Bitmask for keyboard modifiers.
     * @param x The x position of the mouse from the left side of the window.
     * @param y The y position of the mouse from the top side of the window.
     */
    public InputEventMouseButton(int button, int modifiers, float x, float y) {
        this(button, modifiers, new Vector2(x, y));
    }

    /**
     * An input event that represents a mouse button being pressed.
     *
     * @param button The mouse button identifier.
     * @param modifiers Bitmask for keyboard modifiers.
     */
    public InputEventMouseButton(int button, int modifiers) {
        this(button, modifiers, Vector2.ZERO);
    }

    /**
     * An input event that represents a mouse button being pressed.
     *
     * @param button The mouse button identifier.
     */
    public InputEventMouseButton(int button) {
        this(button, 0);
    }

    @Override
    public boolean matches(InputEvent event, boolean exact) {
        return event instanceof InputEventMouseButton eventMouseButton
            && eventMouseButton.button() == this.button()
            && (!exact || eventMouseButton.modifiers() == this.modifiers());
    }
}
