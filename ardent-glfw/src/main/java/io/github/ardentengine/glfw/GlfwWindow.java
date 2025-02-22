package io.github.ardentengine.glfw;

import io.github.ardentengine.core.display.CursorMode;
import io.github.ardentengine.core.display.Window;
import io.github.ardentengine.core.input.*;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

public class GlfwWindow extends Window {

    private final long window;

    private double mouseX = 0.0;
    private double mouseY = 0.0;

    @SuppressWarnings("resource")
    public GlfwWindow(int width, int height, String title) {
        // Create the GLFW window
        this.window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (this.window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        // Set input callbacks
        GLFW.glfwSetKeyCallback(this.window, this::keyCallback);
        GLFW.glfwSetMouseButtonCallback(this.window, this::mouseButtonCallback);
        GLFW.glfwSetScrollCallback(this.window, this::scrollCallback);
        GLFW.glfwSetCursorPosCallback(this.window, this::cursorPosCallback);
    }

    private void keyCallback(long window, int key, int scancode, int action, int mods) {
        InputSystem.parseEvent(new InputEventKey(key, mods, action != GLFW.GLFW_RELEASE, action == GLFW.GLFW_REPEAT));
    }

    private void cursorPosCallback(long window, double xPos, double yPos) {
        InputSystem.parseEvent(new InputEventMouseMotion((float) xPos, (float) yPos, (float) (xPos - this.mouseX), (float) (yPos - this.mouseY)));
        this.mouseX = xPos;
        this.mouseY = yPos;
    }

    private void mouseButtonCallback(long window, int button, int action, int mods) {
        InputSystem.parseEvent(new InputEventMouseButton(button, mods, (float) this.mouseX, (float) this.mouseY, action == GLFW.GLFW_PRESS));
    }

    private void scrollCallback(long window, double xOffset, double yOffset) {
        InputSystem.parseEvent(new InputEventScroll((float) xOffset, (float) yOffset));
    }

    public GlfwWindow() {
        this(800, 450, "untitled");
    }

    public void makeContextCurrent() {
        GLFW.glfwMakeContextCurrent(this.window);
    }

    @Override
    public void show() {
        GLFW.glfwShowWindow(this.window);
    }

    @Override
    public void setCursorMode(CursorMode cursorMode) {
        GLFW.glfwSetInputMode(this.window, GLFW.GLFW_CURSOR, switch (cursorMode) {
            case VISIBLE -> GLFW.GLFW_CURSOR_NORMAL;
            case HIDDEN -> GLFW.GLFW_CURSOR_HIDDEN;
            case CAPTURED -> GLFW.GLFW_CURSOR_DISABLED;
            case CONFINED -> GLFW.GLFW_CURSOR_CAPTURED;
        });
    }

    @Override
    public CursorMode getCursorMode() {
        return switch (GLFW.glfwGetInputMode(this.window, GLFW.GLFW_CURSOR)) {
            case GLFW.GLFW_CURSOR_NORMAL -> CursorMode.VISIBLE;
            case GLFW.GLFW_CURSOR_HIDDEN -> CursorMode.HIDDEN;
            case GLFW.GLFW_CURSOR_DISABLED -> CursorMode.CAPTURED;
            case GLFW.GLFW_CURSOR_CAPTURED -> CursorMode.CONFINED;
            default -> null;
        };
    }

    @Override
    public boolean isCloseRequested() {
        return GLFW.glfwWindowShouldClose(this.window);
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(this.window);
    }

    @Override
    public void destroy() {
        Callbacks.glfwFreeCallbacks(this.window);
        GLFW.glfwDestroyWindow(this.window);
    }
}
