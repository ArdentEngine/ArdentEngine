package io.github.ardentengine.core.resources;

import java.util.HashMap;
import java.util.ServiceLoader;

public final class ResourceManager {

    private static final HashMap<String, ResourceLoader> RESOURCE_LOADERS = new HashMap<>();
    private static final HashMap<String, Object> RESOURCES = new HashMap<>();

    static {
        for(var loader : ServiceLoader.load(ResourceLoader.class)) {
            for(var extension : loader.supportedExtensions()) {
                var previous = RESOURCE_LOADERS.putIfAbsent(extension, loader);
                if(previous != null && !previous.equals(loader)) {
                    System.err.println("Cannot associate " + loader + " with files of type " + extension + " because they are already associated with " + previous);
                }
            }
        }
    }

    public static Object getOrLoad(String resourcePath) {
        var resource = RESOURCES.get(resourcePath);
        if(resource == null) {
            var index = resourcePath.lastIndexOf('.');
            if(index != -1) {
                var extension = resourcePath.substring(index);
                var loader = RESOURCE_LOADERS.get(extension);
                if(loader != null) {
                    resource = loader.loadResource(resourcePath);
                    RESOURCES.put(resourcePath, resource);
                } else {
                    System.err.println("There is no resource loader for resources of type '" + extension + "'");
                }
            } else {
                System.err.println("Invalid path " + resourcePath);
            }
        }
        return resource;
    }

    private ResourceManager() {
        // Prevent the instantiation of a static utility class
    }
}
