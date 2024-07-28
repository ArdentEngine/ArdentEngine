package io.github.ardentengine.glfw;

import io.github.ardentengine.core.Application;
import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.logging.Logger;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

/**
 * Display system used to manage the GLFW window.
 */
public class DisplaySystem implements EngineSystem {

    @Override
    public void initialize() {
        Logger.info("Initializing GLFW display system using LWJGL " + Version.getVersion());
        GLFWErrorCallback.createPrint(System.err).set();
        if(!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        MainWindow.getInstance().show();
    }

    @Override
    public void process() {
        if(!MainWindow.getInstance().isCloseRequested()) {
            MainWindow.getInstance().update();
            GLFW.glfwPollEvents();
        } else {
            Application.quit();
        }
    }

    @Override
    public void terminate() {
        MainWindow.getInstance().destroy();
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    @Override
    public int priority() {
        return 1;
    }
}