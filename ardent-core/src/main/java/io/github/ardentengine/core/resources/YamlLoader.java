package io.github.ardentengine.core.resources;

import io.github.ardentengine.core.math.*;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.IOException;

public class YamlLoader implements ResourceLoader {

    private final Yaml yaml;

    public YamlLoader() {
        // Create a yaml loader
        var loaderOptions = new LoaderOptions();
        // Do not allow duplicate keys
        loaderOptions.setAllowDuplicateKeys(false);
        // Global tags must be allowed to instantiate the correct type of resource
        loaderOptions.setTagInspector(tag -> true);
        // Create yaml instance
        this.yaml = new Yaml(new LoaderConstructor(loaderOptions));
        // Access private fields
        this.yaml.setBeanAccess(BeanAccess.FIELD);
    }

    @Override
    public Object loadResource(String resourcePath) {
        try (var inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream != null) {
                return this.yaml.load(inputStream);
            }
            System.err.println("Could not find resource " + resourcePath);
        } catch (IOException e) {
            System.err.println("Exception occurred while loading resource " + resourcePath);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String[] supportedExtensions() {
        return new String[] {".yaml", ".yml"};
    }

    private static class LoaderConstructor extends Constructor {

        public LoaderConstructor(LoaderOptions loadingConfig) {
            super(loadingConfig);
            this.yamlConstructors.put(Tag.FLOAT, new ConstructFloat());
            this.yamlConstructors.put(new Tag("!resource"), new ConstructResource());
            this.yamlConstructors.put(new Tag("!Vector2"), new ConstructVector2());
            this.yamlConstructors.put(new Tag("!Vector2i"), new ConstructVector2i());
            this.yamlConstructors.put(new Tag("!Vector3"), new ConstructVector3());
            this.yamlConstructors.put(new Tag("!Vector3i"), new ConstructVector3i());
            this.yamlConstructors.put(new Tag("!Vector4"), new ConstructVector4());
            this.yamlConstructors.put(new Tag("!Vector4i"), new ConstructVector4i());
            this.yamlConstructors.put(new Tag("!Color"), new ConstructColor());
            // TODO: Add tags for Quaternion, Rect2, Rect2i, and AABB
        }

        private class ConstructFloat extends SafeConstructor.ConstructYamlFloat {

            @Override
            public Object construct(Node node) {
                return ((Double) super.construct(node)).floatValue();
            }
        }

        private class ConstructResource extends AbstractConstruct {

            @Override
            public Object construct(Node node) {
                return ResourceManager.getOrLoad(constructScalar((ScalarNode) node));
            }
        }

        private class ConstructVector2 extends AbstractConstruct {

            @Override
            public Object construct(Node node) {
                var mapping = constructMapping((MappingNode) node);
                return new Vector2((float) mapping.get("x"), (float) mapping.get("y"));
            }
        }

        private class ConstructVector2i extends AbstractConstruct {

            @Override
            public Object construct(Node node) {
                var mapping = constructMapping((MappingNode) node);
                return new Vector2i((int) mapping.get("x"), (int) mapping.get("y"));
            }
        }

        private class ConstructVector3 extends AbstractConstruct {

            @Override
            public Object construct(Node node) {
                var mapping = constructMapping((MappingNode) node);
                return new Vector3((float) mapping.get("x"), (float) mapping.get("y"), (float) mapping.get("z"));
            }
        }

        private class ConstructVector3i extends AbstractConstruct {

            @Override
            public Object construct(Node node) {
                var mapping = constructMapping((MappingNode) node);
                return new Vector3i((int) mapping.get("x"), (int) mapping.get("y"), (int) mapping.get("z"));
            }
        }

        private class ConstructVector4 extends AbstractConstruct {

            @Override
            public Object construct(Node node) {
                var mapping = constructMapping((MappingNode) node);
                return new Vector4((float) mapping.get("x"), (float) mapping.get("y"), (float) mapping.get("z"), (float) mapping.get("w"));
            }
        }

        private class ConstructVector4i extends AbstractConstruct {

            @Override
            public Object construct(Node node) {
                var mapping = constructMapping((MappingNode) node);
                return new Vector4i((int) mapping.get("x"), (int) mapping.get("y"), (int) mapping.get("z"), (int) mapping.get("w"));
            }
        }

        private class ConstructColor extends AbstractConstruct {

            @Override
            public Object construct(Node node) {
                var mapping = constructMapping((MappingNode) node);
                return new Color((float) mapping.get("r"), (float) mapping.get("g"), (float) mapping.get("b"), (float) mapping.getOrDefault("a", 1.0f));
            }
        }
    }
}
