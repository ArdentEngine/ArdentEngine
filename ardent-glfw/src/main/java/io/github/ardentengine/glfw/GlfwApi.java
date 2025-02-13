package io.github.ardentengine.glfw;

import io.github.ardentengine.core.display.DisplayApi;
import io.github.ardentengine.core.display.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class GlfwApi extends DisplayApi {

    public GlfwApi() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
    }

    @Override
    public Window getMainWindow() {
        return MainWindow.getInstance();
    }
}
