package io.github.ardentengine.core;

import io.github.ardentengine.core.math.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestApplicationProperties {

    @Test
    public void testGetString() {
        Assertions.assertEquals("This is a test property", ApplicationProperties.get("test.property.string"));
    }

    @Test
    public void testGetFloat() {
        Assertions.assertEquals(1.2e-4f, ApplicationProperties.get("test.property.float"));
    }

    @Test
    public void testGetInteger() {
        Assertions.assertEquals(42, ApplicationProperties.get("test.property.integer"));
    }

    @Test
    public void testGetBoolean() {
        Assertions.assertEquals(true, ApplicationProperties.get("test.property.bool"));
        Assertions.assertEquals(false, ApplicationProperties.get("test.property.anotherBool"));
    }

    @Test
    public void testGetNull() {
        Assertions.assertNull(ApplicationProperties.get("i.do.not.exist"));
    }

    @Test
    public void testGetVector2() {
        var res = new Vector2(1.0f, 2.0f);
        Assertions.assertEquals(res, ApplicationProperties.get("test.property.vec2"));
        Assertions.assertEquals(res.x(), ApplicationProperties.get("test.property.vec2.x"));
        Assertions.assertEquals(res.y(), ApplicationProperties.get("test.property.vec2.y"));
    }

    @Test
    public void testGetVector2i() {
        var res = new Vector2i(1, 2);
        Assertions.assertEquals(res, ApplicationProperties.get("test.property.vec2i"));
        Assertions.assertEquals(res.x(), ApplicationProperties.get("test.property.vec2i.x"));
        Assertions.assertEquals(res.y(), ApplicationProperties.get("test.property.vec2i.y"));
    }

    @Test
    public void testGetVector3() {
        var res = new Vector3(1.0f, 2.0f, 2.5f);
        Assertions.assertEquals(res, ApplicationProperties.get("test.property.vec3"));
        Assertions.assertEquals(res.x(), ApplicationProperties.get("test.property.vec3.x"));
        Assertions.assertEquals(res.y(), ApplicationProperties.get("test.property.vec3.y"));
        Assertions.assertEquals(res.z(), ApplicationProperties.get("test.property.vec3.z"));
    }

    @Test
    public void testGetVector3i() {
        var res = new Vector3i(1, 2, 3);
        Assertions.assertEquals(res, ApplicationProperties.get("test.property.vec3i"));
        Assertions.assertEquals(res.x(), ApplicationProperties.get("test.property.vec3i.x"));
        Assertions.assertEquals(res.y(), ApplicationProperties.get("test.property.vec3i.y"));
        Assertions.assertEquals(res.z(), ApplicationProperties.get("test.property.vec3i.z"));
    }

    @Test
    public void testGetVector4() {
        var res = new Vector4(1.0f, 2.0f, -1.5f, 2.75f);
        Assertions.assertEquals(res, ApplicationProperties.get("test.property.vec4"));
        Assertions.assertEquals(res.x(), ApplicationProperties.get("test.property.vec4.x"));
        Assertions.assertEquals(res.y(), ApplicationProperties.get("test.property.vec4.y"));
        Assertions.assertEquals(res.z(), ApplicationProperties.get("test.property.vec4.z"));
        Assertions.assertEquals(res.w(), ApplicationProperties.get("test.property.vec4.w"));
    }

    @Test
    public void testGetVector4i() {
        var res = new Vector4i(1, 2, -5, 4);
        Assertions.assertEquals(res, ApplicationProperties.get("test.property.vec4i"));
        Assertions.assertEquals(res.x(), ApplicationProperties.get("test.property.vec4i.x"));
        Assertions.assertEquals(res.y(), ApplicationProperties.get("test.property.vec4i.y"));
        Assertions.assertEquals(res.z(), ApplicationProperties.get("test.property.vec4i.z"));
        Assertions.assertEquals(res.w(), ApplicationProperties.get("test.property.vec4i.w"));
    }

    @Test
    public void testGetColor() {
        var res = new Color(0.9f, 0.8f, 0.2f, 0.5f);
        Assertions.assertEquals(res, ApplicationProperties.get("test.property.color"));
        Assertions.assertEquals(res.r(), ApplicationProperties.get("test.property.color.r"));
        Assertions.assertEquals(res.g(), ApplicationProperties.get("test.property.color.g"));
        Assertions.assertEquals(res.b(), ApplicationProperties.get("test.property.color.b"));
        Assertions.assertEquals(res.a(), ApplicationProperties.get("test.property.color.a"));
    }

    @Test
    public void testGetColorNoAlpha() {
        var res = new Color(0.9f, 0.8f, 0.2f);
        Assertions.assertEquals(res, ApplicationProperties.get("test.property.anotherColor"));
        Assertions.assertEquals(res.r(), ApplicationProperties.get("test.property.anotherColor.r"));
        Assertions.assertEquals(res.g(), ApplicationProperties.get("test.property.anotherColor.g"));
        Assertions.assertEquals(res.b(), ApplicationProperties.get("test.property.anotherColor.b"));
    }

    @Test
    public void testGetDefaultValue() {
        Assertions.assertEquals("Default value", ApplicationProperties.get("i.do.not.exist", "Default value"));
    }
}
