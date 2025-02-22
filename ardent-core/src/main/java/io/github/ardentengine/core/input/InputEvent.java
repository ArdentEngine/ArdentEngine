package io.github.ardentengine.core.input;

/**
 * Base interface for input events.
 */
public interface InputEvent {

    // TODO: interface InputEventFromWindow

    /**
     * Checks if this input event is pressed.
     * Used for input events that represent a key or button.
     *
     * @return True if this input event is pressed, otherwise false.
     */
    boolean isPressed();

    /**
     * Checks if this input event is released.
     * Used for input events that represent a key or button.
     *
     * @return True if this input event is released, otherwise false.
     */
    default boolean isReleased() {
        return !this.isPressed();
    }

    /**
     * Checks if this input event is an echo event.
     * <p>
     *     Only used for {@link InputEventKey}.
     *     Any other event returns false.
     * </p>
     *
     * @return True if this input event is an echo event, otherwise false.
     */
    default boolean isEcho() {
        return false;
    }

    /**
     * Checks if this input event matches an action specified in the input map.
     * The input map can be accessed through the {@link InputSystem} class.
     *
     * @param action Name of the action.
     * @return True if this input event matches the given action, otherwise false.
     */
    default boolean isAction(String action) {
        // TODO: This need to be changed to allow exact or non exact match
        return InputSystem.eventIsAction(this, action);
    }

    /**
     * Checks if this input event is pressed, is not an echo event, and matches the given action.
     *
     * @param action Name of the action.
     * @return True if this input event is pressed and matches the given action.
     *
     * @see InputEvent#isAction(String)
     */
    default boolean isActionPressed(String action) {
        return this.isPressed() && !this.isEcho() && this.isAction(action);
    }

    /**
     * Checks if this input event is released and matches the given action.
     *
     * @param action Name of the action.
     * @return True if this input event is released and matches the given action.
     *
     * @see InputEvent#isAction(String)
     */
    default boolean isActionReleased(String action) {
        return this.isReleased() && this.isAction(action);
    }

    /**
     * Returns the strength of this input event as a number between 0 and 1.
     * <p>
     *     For input events that correspond to a key or a button, returns 1 if the event is pressed, otherwise 0.
     *     For input events that correspond to triggers or analog sticks, the number indicates how far away the input is from the dead zone.
     * </p>
     *
     * @return The strength of this input event.
     */
    default float strength() {
        return this.isPressed() ? 1.0f : 0.0f;
    }

    // TODO: getActionStrength

    /**
     * Checks if this event matches the given one.
     * Can be used to check if the given event corresponds to the same key or button as this one.
     *
     * @param event The input event.
     * @param exact If false, additional modifiers will be ignored.
     * @return True if this event matches the given one, otherwise false.
     */
    boolean matches(InputEvent event, boolean exact);

    /**
     * Checks if this event matches the given one.
     * Can be used to check if the given event corresponds to the same key or button as this one.
     *
     * @param event The input event.
     * @return True if this event matches the given one, otherwise false.
     */
    default boolean matches(InputEvent event) {
        return this.matches(event, false);
    }
}
