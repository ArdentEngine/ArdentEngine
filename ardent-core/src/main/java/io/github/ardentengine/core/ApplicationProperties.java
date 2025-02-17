package io.github.ardentengine.core;

import io.github.ardentengine.core.math.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Static class that stores globally-accessible properties.
 * <p>
 *     Stores values that can be accessed everywhere using {@link ApplicationProperties#get(String)}.
 *     The values are loaded from the {@code application.properties files} and can be used as configuration options.
 * </p>
 */
public final class ApplicationProperties {

    // TODO: Handle default values and allow to add and remove properties

    /**
     * Map of properties remapped to their correct value.
     * <p>
     *     The {@link Properties} class load all properties as strings.
     *     Properties such as numeric values or vectors are parsed when they are first requested and are stored in this map.
     * </p>
     */
    private static final HashMap<String, Object> PROPERTIES = new HashMap<>();

    /**
     * Properties loaded from the {@code application.properties} file.
     */
    private static final Properties LOADED_PROPERTIES = new Properties();

    /**
     * Private method used to parse a numeric string.
     * <p>
     *     Returns an {@link Integer} if the given string represents an integer value.
     *     Returns a {@link Float} if the given string represents a floating point number.
     * </p>
     * <p>
     *     Returns {@code null} if the given value is {@code null} or it is not a numeric string.
     * </p>
     *
     * @param value The string value to be parsed.
     * @return The numeric value represented by the given string or {@code null}.
     */
    private static Number parseNumber(String value) {
        // Check if the value is null to avoid having to check before calling this method
        if (value != null) {
            // Use a regex to check if the string is numeric and what type of number it is
            if (value.matches("^[+-]?\\d+$")) {
                // Return an integer if the string is an integer number
                return Integer.parseInt(value);
            } else if (value.matches("^[+-]?\\d+\\.\\d+([eE][+-]?\\d+)?$")) {
                // Return a float if the string is a floating point number
                return Float.parseFloat(value);
            }
        }
        // Return null to indicate that the value is not a number
        return null;
    }

    /**
     * Returns the value of the property with the given key.
     * <p>
     *     Returns {@code null} if there is no property with the given key.
     * </p>
     * <p>
     *     If the given key maps to a supported record type, then an instance of the correct record will be returned.
     *     Supported types are:
     *     <ul>
     *         <li>{@link Vector2}</li>
     *         <li>{@link Vector2i}</li>
     *         <li>{@link Vector3}</li>
     *         <li>{@link Vector3i}</li>
     *         <li>{@link Vector4}</li>
     *         <li>{@link Vector4i}</li>
     *         <li>{@link Color}</li>
     *     </ul>
     * </p>
     *
     * @param key The property key.
     * @return The value of the requested property.
     */
    public static Object get(String key) {
        // Map the property to its correct type
        return PROPERTIES.computeIfAbsent(key, property -> {
            // Load properties if they haven't been loaded already
            if (LOADED_PROPERTIES.isEmpty()) {
                try (var inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")) {
                    if (inputStream != null) {
                        LOADED_PROPERTIES.load(inputStream);
                    }
                    // TODO: Error handling
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            var value = LOADED_PROPERTIES.getProperty(property);
            // Check if the value exists in the loaded properties
            if (value != null) {
                // Try to parse the value if it is a number
                var numberValue = parseNumber(value);
                if (numberValue != null) {
                    // Return the number value if the value is a numeric string
                    return numberValue;
                } else if (value.equalsIgnoreCase("true")) {
                    // Return true if the value is the boolean true
                    return true;
                } else if (value.equalsIgnoreCase("false")) {
                    // Return false if the value is the boolean false
                    return false;
                }
            } else {
                // Try to parse the x and y value if this property is a vector
                var x = parseNumber(LOADED_PROPERTIES.getProperty(property + ".x"));
                var y = parseNumber(LOADED_PROPERTIES.getProperty(property + ".y"));
                // The result is a vector if the x and y values exist
                if (x != null && y != null) {
                    // Try to parse the z value
                    var z = parseNumber(LOADED_PROPERTIES.getProperty(property + ".z"));
                    // The result is either a 3d or a 4d vector if the z value exists
                    if (z != null) {
                        // Try to parse the w value
                        var w = parseNumber(LOADED_PROPERTIES.getProperty(property + ".w"));
                        // The result is a 4d vector if the w value exists
                        if (w != null) {
                            // Return an integer vector if all four coordinates are integer
                            if (x instanceof Integer && y instanceof Integer && z instanceof Integer && w instanceof Integer) {
                                return new Vector4i(x.intValue(), y.intValue(), z.intValue(), w.intValue());
                            }
                            // Return a 4d vector
                            return new Vector4(x.floatValue(), y.floatValue(), z.floatValue(), w.floatValue());
                        }
                        // Return an integer vector if all three coordinates are integer
                        if (x instanceof Integer && y instanceof Integer && z instanceof Integer) {
                            return new Vector3i(x.intValue(), y.intValue(), z.intValue());
                        }
                        // Return a 3d vector
                        return new Vector3(x.floatValue(), y.floatValue(), z.floatValue());
                    }
                    // Return an integer vector if both coordinates are integer
                    if (x instanceof Integer && y instanceof Integer) {
                        return new Vector2i(x.intValue(), y.intValue());
                    }
                    // Return a 2d vector
                    return new Vector2(x.floatValue(), y.floatValue());
                }
                // Try to parse the r, g, b, and alpha values if this property is a color
                var r = parseNumber(LOADED_PROPERTIES.getProperty(property + ".r"));
                var g = parseNumber(LOADED_PROPERTIES.getProperty(property + ".g"));
                var b = parseNumber(LOADED_PROPERTIES.getProperty(property + ".b"));
                var a = parseNumber(LOADED_PROPERTIES.getProperty(property + ".a"));
                // The result is a color if the r, g, and b values exist
                if (r != null && g != null && b != null) {
                    return new Color(r.floatValue(), g.floatValue(), b.floatValue(), a == null ? 1.0f : a.floatValue());
                }
                // TODO: Allow other values such as Rect2 or AABB
            }
            // Return the value if it is not a numeric string or if it is null
            return value;
        });
    }

    /**
     * Returns the value of the property with the given key or the default value if the property does not exist.
     * <p>
     *     If the given key maps to a supported record type, then an instance of the correct record will be returned.
     *     See {@link ApplicationProperties#get(String)} to see the supported types.
     * </p>
     *
     * @param key The property key.
     * @param defaultValue The default value.
     * @return The value of the requested property or the default value if the property does not exist.
     */
    public static Object get(String key, Object defaultValue) {
        var value = get(key);
        return value == null ? defaultValue : value;
    }

    /**
     * Private constructor used to prevent the instantiation of a static utility class.
     */
    private ApplicationProperties() {
        // Prevent the instantiation of a static utility class
    }
}
