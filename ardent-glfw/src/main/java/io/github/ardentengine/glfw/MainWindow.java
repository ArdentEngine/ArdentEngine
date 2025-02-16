package io.github.ardentengine.glfw;

import io.github.ardentengine.core.ApplicationProperties;
import org.lwjgl.glfw.GLFW;

public class MainWindow extends GlfwWindow {

    private static MainWindow instance;

    public static synchronized MainWindow getInstance() {
        if(instance == null) {
            // Window hints
            var visible = (boolean) ApplicationProperties.get("display.window.visible", true);
            GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, visible ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
            var resizable = (boolean) ApplicationProperties.get("display.window.resizable", false);
            GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
            var decorated = (boolean) ApplicationProperties.get("display.window.decorated", true);
            GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, decorated ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
            var focused = (boolean) ApplicationProperties.get("display.window.focused", true);
            GLFW.glfwWindowHint(GLFW.GLFW_FOCUSED, focused ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
            var maximized = (boolean) ApplicationProperties.get("display.window.maximized", false);
            GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, maximized ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
            // TODO: Finish window hints
            // Window size and title
            var width = (int) ApplicationProperties.get("display.window.size.x", 800);
            var height = (int) ApplicationProperties.get("display.window.size.y", 450);
            var title = (String) ApplicationProperties.get("application.config.name", "untitled");
            // Create the main window
            instance = new MainWindow(width, height, title);
        }
        return instance;
    }

    private MainWindow(int width, int height, String title) {
        super(width, height, title);
    }
}
