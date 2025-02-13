package io.github.ardentengine.core.resources;

public interface ResourceLoader {

    Object loadResource(String resourcePath);

    String[] supportedExtensions();
}
