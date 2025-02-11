package io.github.ardentengine.glfw;

import io.github.ardentengine.core.display.Window;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

public class GlfwWindow extends Window {

    private final long window;

    public GlfwWindow() {
        this.window = GLFW.glfwCreateWindow(800, 450, "This is a hardcoded title", MemoryUtil.NULL, MemoryUtil.NULL);
        if (this.window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        // FIXME: This should not be called in Vulkan
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

    @Override
    public void update() {
        // FIXME: This should not be called in Vulkan
        GLFW.glfwSwapBuffers(this.window);
    }

    @Override
    public void destroy() {
        Callbacks.glfwFreeCallbacks(this.window);
        GLFW.glfwDestroyWindow(this.window);
    }
}
