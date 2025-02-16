package io.github.ardentengine.glfw;

import io.github.ardentengine.core.display.Window;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

public class GlfwWindow extends Window {

    private final long window;

    public GlfwWindow(int width, int height, String title) {
        this.window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (this.window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        // TODO: Add input callbacks here
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
