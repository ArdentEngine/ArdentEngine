package io.github.ardentengine.core.display;

import java.util.ServiceLoader;

public abstract class DisplayApi {

    private static DisplayApi instance;

    public static synchronized DisplayApi getInstance() {
        if(instance == null) {
            // TODO: Handle the case in which there are more or none
            for(var api : ServiceLoader.load(DisplayApi.class)) {
                instance = api;
                break;
            }
        }
        return instance;
    }

    public abstract Window getMainWindow();

    // TODO: Add a way to get the position of the mouse (within a window or within the screen)

    // TODO: Add a method to get key labels

    // TODO: Add methods to set / get clipboard string
}
