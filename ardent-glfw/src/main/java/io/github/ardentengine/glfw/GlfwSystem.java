package io.github.ardentengine.glfw;

import io.github.ardentengine.core.Application;
import io.github.ardentengine.core.display.DisplaySystem;
import io.github.ardentengine.core.display.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class GlfwSystem extends DisplaySystem {

    private final Window mainWindow;

    public GlfwSystem() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        // TODO: Load window hints from project settings here
        this.mainWindow = new GlfwWindow();
    }

    @Override
    protected void initialize() {
        this.mainWindow.show();
    }

    @Override
    protected void process() {
        if (this.mainWindow.isCloseRequested()) {
            Application.quit();
        } else {
            // TODO: This "update" method may be removed from the Window class so that it is only in GlfwWindow
            this.mainWindow.update();
            GLFW.glfwPollEvents();
        }
    }

    @Override
    public Window getMainWindow() {
        return this.mainWindow;
    }

    @Override
    protected void terminate() {
        this.mainWindow.destroy();
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }
}
