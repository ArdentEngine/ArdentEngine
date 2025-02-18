package io.github.ardentengine.glfw;

import io.github.ardentengine.core.display.Window;
import io.github.ardentengine.core.input.InputEventKey;
import io.github.ardentengine.core.input.InputEventMouseButton;
import io.github.ardentengine.core.input.InputEventScroll;
import io.github.ardentengine.core.input.InputSystem;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

public class GlfwWindow extends Window {

    private static void keyCallback(long window, int key, int scancode, int action, int mods) {
        InputSystem.parseEvent(new InputEventKey(key, mods, action != GLFW.GLFW_RELEASE, action == GLFW.GLFW_REPEAT));
    }

    private static void mouseButtonCallback(long window, int button, int action, int mods) {
        InputSystem.parseEvent(new InputEventMouseButton(button, mods, action == GLFW.GLFW_PRESS));
    }

    private static void scrollCallback(long window, double xOffset, double yOffset) {
        InputSystem.parseEvent(new InputEventScroll((float) xOffset, (float) yOffset));
    }

    private final long window;

    @SuppressWarnings("resource")
    public GlfwWindow(int width, int height, String title) {
        // Create the GLFW window
        this.window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (this.window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        // Set input callbacks
        GLFW.glfwSetKeyCallback(this.window, GlfwWindow::keyCallback);
        GLFW.glfwSetMouseButtonCallback(this.window, GlfwWindow::mouseButtonCallback);
        GLFW.glfwSetScrollCallback(this.window, GlfwWindow::scrollCallback);
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
