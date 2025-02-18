package io.github.ardentengine.core.input;

/**
 * An input event that represents a mouse button being pressed or released.
 *
 * @param button The mouse button identifier.
 * @param modifiers Bitmask for keyboard modifiers.
 * @param isPressed If true, the mouse button's state is pressed. If false, the mouse button's state is released.
 */
public record InputEventMouseButton(int button, int modifiers, boolean isPressed) implements InputEventWithModifiers {

    /**
     * Index of the left mouse button.
     */
    public static final int BUTTON_LEFT = 0;
    /**
     * Index of the right mouse button.
     */
    public static final int BUTTON_RIGHT = 1;
    /**
     * Index of the middle mouse button.
     */
    public static final int BUTTON_MIDDLE = 2;
    /**
     * Index of the fourth mouse button.
     */
    public static final int BUTTON_4 = 3;
    /**
     * Index of the fifth mouse button.
     */
    public static final int BUTTON_5 = 4;
    /**
     * Index of the sixth mouse button.
     */
    public static final int BUTTON_6 = 5;
    /**
     * Index of the seventh mouse button.
     */
    public static final int BUTTON_7 = 6;
    /**
     * Index of the eighth mouse button.
     */
    public static final int BUTTON_8 = 7;

    /**
     * An input event that represents a mouse button being pressed.
     *
     * @param button The mouse button identifier.
     * @param modifiers Bitmask for keyboard modifiers.
     */
    public InputEventMouseButton(int button, int modifiers) {
        this(button, modifiers, true);
    }

    @Override
    public boolean matches(InputEvent event) {
        return event instanceof InputEventMouseButton mouseButton
            && mouseButton.button() == this.button()
            && mouseButton.modifiers() == this.modifiers();
    }
}
