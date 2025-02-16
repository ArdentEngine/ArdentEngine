package io.github.ardentengine.glfw;

import io.github.ardentengine.core.Application;
import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.display.DisplayApi;
import org.lwjgl.glfw.GLFW;

public class GlfwSystem extends EngineSystem {

    // TODO: This class and OpenglSystem will need to change

    public GlfwSystem() {
        // FIXME: This is only to enable GLFW
        DisplayApi.getInstance();
//        GLFWErrorCallback.createPrint(System.err).set();
//        if (!GLFW.glfwInit()) {
//            throw new IllegalStateException("Unable to initialize GLFW");
//        }
        // TODO: makeContextCurrent should not be called in Vulkan
        MainWindow.getInstance().makeContextCurrent();
    }

    @Override
    protected void initialize() {
//        MainWindow.getInstance().show();
    }

    @Override
    protected void process() {
        if (MainWindow.getInstance().isCloseRequested()) {
            Application.quit();
        } else {
            // TODO: swapBuffers should not be called in Vulkan
            MainWindow.getInstance().swapBuffers();
            GLFW.glfwPollEvents();
        }
    }

    @Override
    protected void terminate() {
        MainWindow.getInstance().destroy();
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }
}
