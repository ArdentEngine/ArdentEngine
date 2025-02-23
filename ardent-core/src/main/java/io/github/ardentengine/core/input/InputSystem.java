package io.github.ardentengine.core.input;

import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.math.Vector2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class InputSystem extends EngineSystem {

    /** The input map matches input events to an action. */
    private static final HashMap<String, Set<InputEvent>> INPUT_MAP = new HashMap<>();

    static {
        // TODO: Load this from file instead
        INPUT_MAP.computeIfAbsent("up", action -> new HashSet<>()).add(new InputEventKey(InputEventKey.KEY_UP));
        INPUT_MAP.computeIfAbsent("up", action -> new HashSet<>()).add(new InputEventKey(InputEventKey.KEY_W));
        INPUT_MAP.computeIfAbsent("down", action -> new HashSet<>()).add(new InputEventKey(InputEventKey.KEY_DOWN));
        INPUT_MAP.computeIfAbsent("down", action -> new HashSet<>()).add(new InputEventKey(InputEventKey.KEY_S));
        INPUT_MAP.computeIfAbsent("left", action -> new HashSet<>()).add(new InputEventKey(InputEventKey.KEY_LEFT));
        INPUT_MAP.computeIfAbsent("left", action -> new HashSet<>()).add(new InputEventKey(InputEventKey.KEY_A));
        INPUT_MAP.computeIfAbsent("right", action -> new HashSet<>()).add(new InputEventKey(InputEventKey.KEY_RIGHT));
        INPUT_MAP.computeIfAbsent("right", action -> new HashSet<>()).add(new InputEventKey(InputEventKey.KEY_D));
        INPUT_MAP.computeIfAbsent("space", action -> new HashSet<>()).add(new InputEventKey(InputEventKey.KEY_SPACE));
        INPUT_MAP.computeIfAbsent("shift", action -> new HashSet<>()).add(new InputEventKey(InputEventKey.KEY_LEFT_SHIFT));
        INPUT_MAP.computeIfAbsent("shift", action -> new HashSet<>()).add(new InputEventKey(InputEventKey.KEY_RIGHT_SHIFT));
    }

    /** Keeps track of pressed events. */
    private static final Set<InputEvent> PRESSED_EVENTS = new HashSet<>();
    /** Keeps track of events that were pressed this frame. */
    private static final Set<InputEvent> JUST_PRESSED_EVENTS = new HashSet<>();
    /** Keeps track of events that were released this frame. */
    private static final Set<InputEvent> JUST_RELEASED_EVENTS = new HashSet<>();

    private static Consumer<InputEvent> eventDispatchFunction;

    /**
     * Checks if the given event is associated with the given action in the input map.
     *
     * @param event The input event.
     * @param action Name of the action.
     * @param exact If false, additional modifiers will be ignored.
     * @return True if the given input event matches any of the events mapped to the given action, otherwise false.
     */
    public static boolean eventIsAction(InputEvent event, String action, boolean exact) {
        var actionEvents = INPUT_MAP.get(action);
        if (actionEvents != null) {
            for (var actionEvent : actionEvents) {
                if (actionEvent.matches(event, exact)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the given event is associated with the given action in the input map.
     *
     * @param event The input event.
     * @param action Name of the action.
     * @return True if the given input event matches any of the events mapped to the given action, otherwise false.
     */
    public static boolean eventIsAction(InputEvent event, String action) {
        return eventIsAction(event, action, false);
    }

    // TODO: Add methods to interact with the input map

    /**
     * Parses an input event.
     * Can be used to trigger input events.
     *
     * @param event The event to parse.
     */
    public static void parseEvent(InputEvent event) {
        if (event.isPressed()) {
            if (!event.isEcho()) {
                JUST_PRESSED_EVENTS.add(event);
                PRESSED_EVENTS.add(event);
            }
        } else if (event.isReleased()) {
            PRESSED_EVENTS.removeIf(event::matches);
            JUST_RELEASED_EVENTS.remove(event);
        }
        // TODO: Handle events that are not pressed nor released such as joystick axes and mouse motion
        if (eventDispatchFunction != null) {
            eventDispatchFunction.accept(event);
        }
    }

    public static void setEventDispatchFunction(Consumer<InputEvent> function) {
        // TODO: Replace this when a proper event/observer system is implemented
        eventDispatchFunction = function;
    }

    /**
     * Returns true if the given action is being pressed.
     *
     * @param action The action name.
     * @param exact If false, additional modifiers will be ignored.
     * @return True if the action is being pressed, otherwise false.
     */
    public static boolean isActionPressed(String action, boolean exact) {
        for (var event : PRESSED_EVENTS) {
            if (event.isAction(action, exact)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the given action is being pressed.
     *
     * @param action The action name.
     * @return True if the action is being pressed, otherwise false.
     */
    public static boolean isActionPressed(String action) {
        return isActionPressed(action, false);
    }

    /**
     * Returns true if the given action has been pressed in the current frame.
     * Used for code that needs to run only once when an action is pressed, instead of every frame while it is pressed.
     *
     * @param action The action name.
     * @param exact If false, additional modifiers will be ignored.
     * @return True if the given action has been pressed in the current frame, otherwise false.
     */
    public static boolean isActionJustPressed(String action, boolean exact) {
        for (var event : JUST_PRESSED_EVENTS) {
            if (event.isAction(action, exact)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the given action has been pressed in the current frame.
     * Used for code that needs to run only once when an action is pressed, instead of every frame while it is pressed.
     *
     * @param action The action name.
     * @return True if the given action has been pressed in the current frame, otherwise false.
     */
    public static boolean isActionJustPressed(String action) {
        return isActionJustPressed(action, false);
    }

    /**
     * Returns true if the given action has been released in the current frame.
     *
     * @param action The action name.
     * @param exact If false, additional modifiers will be ignored.
     * @return True if the given action has been released in the current frame, otherwise false.
     */
    public static boolean isActionJustReleased(String action, boolean exact) {
        for (var event : JUST_RELEASED_EVENTS) {
            if (event.isAction(action, exact)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the given action has been released in the current frame.
     *
     * @param action The action name.
     * @return True if the given action has been released in the current frame, otherwise false.
     */
    public static boolean isActionJustReleased(String action) {
        return isActionJustReleased(action, false);
    }

    /**
     * Returns the strength of the given action as a number between 0 and 1.
     * Can handle multiple buttons being held down simultaneously.
     * <p>
     *     For input events that correspond to a key or a button, returns 1 if the event is pressed, otherwise 0.
     *     For input events that correspond to triggers or analog stick, the number indicates how far away the input is from the dead zone.
     * </p>
     *
     * @param action The action name.
     * @param exact If false, additional modifiers will be ignored.
     * @return The strength of the specified action.
     */
    public static float getActionStrength(String action, boolean exact) {
        var strength = 0.0f;
        for (var event : PRESSED_EVENTS) {
            if (event.isAction(action, exact)) {
                strength += event.strength();
            }
        }
        return strength;
    }

    /**
     * Returns the strength of the given action as a number between 0 and 1.
     * Can handle multiple buttons being held down simultaneously.
     * <p>
     *     For input events that correspond to a key or a button, returns 1 if the event is pressed, otherwise 0.
     *     For input events that correspond to triggers or analog stick, the number indicates how far away the input is from the dead zone.
     * </p>
     *
     * @param action The action name.
     * @return The strength of the specified action.
     */
    public static float getActionStrength(String action) {
        return getActionStrength(action, false);
    }

    /**
     * Returns the axis input specified by the given positive and negative action.
     * Can handle multiple buttons being held down simultaneously.
     *
     * @param positive Name of the positive action.
     * @param negative Name of the negative action.
     * @return The axis input specified by the given positive and negative action.
     */
    public static float getAxis(String positive, String negative) {
        return getActionStrength(positive) - getActionStrength(negative);
    }

    /**
     * Returns an input vector specified by the four given actions.
     * Useful to get a vector input, such as from a joystick, directional pad, arrow keys, or WASD keys.
     * The length of the resulting vector is limited to 1.
     *
     * @param up Name of the positive action on the y axis.
     * @param down Name of the negative action on the y axis.
     * @param left Name of the negative action on the x axis.
     * @param right Name of the positive action on the x axis.
     * @return An input vector specified by the four given actions.
     */
    public static Vector2 getVector(String up, String down, String left, String right) {
        var vector = new Vector2(getAxis(right, left), getAxis(up, down));
        if (vector.lengthSquared() > 1.0f) {
            return vector.normalized();
        }
        return vector;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void process() {
        JUST_PRESSED_EVENTS.clear();
        JUST_RELEASED_EVENTS.clear();
    }

    @Override
    protected void terminate() {

    }
}
