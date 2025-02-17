package io.github.ardentengine.core.resources;

import io.github.ardentengine.core.scene.Node;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection"})
public class SceneData {

    private String type;
    private Map<String, Object> properties;
    private List<SceneData> children;

    // TODO: Allow scenes to inherit from others

    // TODO: Allow the properties map to contain a reference to another node in the scene

    private static void setField(Node node, Class<?> inClass, String name, Object value) {
        try {
            // Access the field in the given class
            var field = inClass.getDeclaredField(name);
            field.setAccessible(true);
            // Check the type of the field to assign the value
            var type = field.getType();
            if (type.isAssignableFrom(value.getClass())) {
                // Set the field if the type corresponds
                field.set(node, value);
            } else if (value instanceof Number numberValue) {
                // Numeric fields need to be cast to the correct type
                if(type.equals(byte.class) || type.equals(Byte.class)) {
                    field.set(node, numberValue.byteValue());
                } else if(type.equals(short.class) || type.equals(Short.class)) {
                    field.set(node, numberValue.shortValue());
                } else if(type.equals(int.class) || type.equals(Integer.class)) {
                    field.set(node, numberValue.intValue());
                } else if(type.equals(long.class) || type.equals(Long.class)) {
                    field.set(node, numberValue.longValue());
                } else if(type.equals(float.class) || type.equals(Float.class)) {
                    field.set(node, numberValue.floatValue());
                } else if(type.equals(double.class) || type.equals(Double.class)) {
                    field.set(node, numberValue.doubleValue());
                }
            } else if (value instanceof Boolean booleanValue) {
                // Boolean values need to be converted to the boolean primitive
                if(type.equals(boolean.class) || type.equals(Boolean.class)) {
                    field.set(node, booleanValue);
                }
            }
        } catch (NoSuchFieldException e) {
            // Look for a field with the same name in the superclass
            if (inClass.equals(Node.class)) {
                throw new RuntimeException("Cannot find field with name " + name, e);
            } else {
                setField(node, inClass.getSuperclass(), name, value);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not access field " + name + " in " + inClass, e);
        }
    }

    public final Node instantiate() {
        try {
            // Instantiate the node
            var nodeClass = Class.forName(this.type);
            var node = (Node) nodeClass.getConstructor().newInstance();
            // Set the node's properties
            if (this.properties != null) {
                this.properties.forEach((property, value) -> setField(node, nodeClass, property, value));
            }
            // Instantiate the node's children
            if (this.children != null) {
                for (var child : this.children) {
                    node.addChild(child.instantiate());
                }
            }
            return node;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find node of type " + this.type, e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Exception occurred while instantiating class " + this.type, e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Cannot instantiate class " + this.type + " because it is abstract", e);
        } catch (IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException("Could not find a public no-args constructor in class " + this.type, e);
        }
    }
}
