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
}
