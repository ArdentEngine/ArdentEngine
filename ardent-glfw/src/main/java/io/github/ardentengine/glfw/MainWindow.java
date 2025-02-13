package io.github.ardentengine.glfw;

public class MainWindow extends GlfwWindow {

    private static MainWindow instance;

    public static synchronized MainWindow getInstance() {
        if(instance == null) {
            // TODO: Window hints should go here
            instance = new MainWindow();
        }
        return instance;
    }
}
