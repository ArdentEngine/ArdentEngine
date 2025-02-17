package io.github.ardentengine.core.resources;

import io.github.ardentengine.core.math.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TestYamlLoader {

    @Test
    public void testLoadYaml() {
        var expected = new LinkedHashMap<>();
        var mappingKey1 = new LinkedHashMap<>();
        var list = new ArrayList<>();
        list.add("value1");
        list.add("value2");
        list.add("value3");
        mappingKey1.put("list", list);
        mappingKey1.put("scalar", "Scalar value");
        expected.put("mappingKey1", mappingKey1);
        var mappingKey2 = new LinkedHashMap<>();
        var subMap = new LinkedHashMap<>();
        subMap.put("integer", 42);
        subMap.put("float", 1.0f);
        mappingKey2.put("subMap", subMap);
        var anotherMap = new LinkedHashMap<>();
        anotherMap.put("boolean", true);
        mappingKey2.put("anotherMap", anotherMap);
        expected.put("mappingKey2", mappingKey2);
        Assertions.assertEquals(expected, ResourceManager.getOrLoad("yaml/test_yaml_file.yaml"));
    }

    @Test
    public void testTags() {
        var result = new LinkedHashMap<>();
        result.put("vec2", new Vector2(1.0f, 2.0f));
        result.put("vec2i", new Vector2i(1, 2));
        result.put("vec3", new Vector3(1.5f, 2.5f, -1.0f));
        result.put("vec3i", new Vector3i(1, 2, 3));
        result.put("vec4", new Vector4(1.1f, 2.2f, -4.2f, 1.0f));
        result.put("vec4i", new Vector4i(1, 2, -3, 4));
        result.put("col", new Color(1.0f, 1.0f, 0.0f));
        Assertions.assertEquals(result, ResourceManager.getOrLoad("yaml/test_tags.yaml"));
    }
}
