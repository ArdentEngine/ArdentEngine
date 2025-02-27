package io.github.ardentengine.core.rendering;

import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.math.Matrix3x4;
import io.github.ardentengine.core.resources.Material;
import io.github.ardentengine.core.resources.Material3D;
import io.github.ardentengine.core.resources.ShaderLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class RenderingSystem3D extends EngineSystem {

    private static final HashMap<ShaderProgram, HashMap<Material, HashMap<VertexData, Collection<Matrix3x4>>>> BATCH = new HashMap<>();

    private final ShaderProgram defaultShader = RenderingApi.getInstance().createShader();
    private final UniformBuffer materialBuffer = RenderingApi.getInstance().createUniformBuffer(2, 16);

    private final Material3D defaultMaterial = new Material3D();

    @Override
    protected void initialize() {
        // Create the default shader
        this.defaultShader.setVertexCode(ShaderLoader.getBuiltinShaderCode("default_shader_3d.vert"));
        this.defaultShader.setFragmentCode(ShaderLoader.getBuiltinShaderCode("default_shader_3d.frag"));
        this.defaultShader.compile();
    }

    @Override
    protected void process() {
        RenderingApi.getInstance().setDepthTest(true);
        // Start rendering 3D objects
        for (var shader : BATCH.keySet()) {
            var shaderBatch = BATCH.get(shader);
            // Use the default shader if no other shader was given
            if (shader == null) {
                shader = this.defaultShader;
            }
            // Start the shader
            shader.start();
            // Draw using this shader
            for (var material : shaderBatch.keySet()) {
                var materialBatch = shaderBatch.get(material);
                // Use the default material if no other material was given
                if (material == null) {
                    material = this.defaultMaterial;
                }
                // Load the material data into the shader
                this.materialBuffer.put(material.getMaterialData());
                // TODO: Use instanced rendering instead
                for (var vertexData : materialBatch.keySet()) {
                    for (var transform : materialBatch.get(vertexData)) {
                        this.defaultShader.setUniform("transform", transform);
                        vertexData.draw();
                    }
                }
            }
        }
        BATCH.clear();
    }

    public static void batchDraw(VertexData vertexData, Matrix3x4 transform, ShaderProgram shader, Material material) {
        // TODO: Separate opaque and transparent objects in two different batches
        BATCH.computeIfAbsent(shader, s -> new HashMap<>())
            .computeIfAbsent(material, m -> new HashMap<>())
            .computeIfAbsent(vertexData, v -> new ArrayList<>())
            .add(transform);
    }

    public static void batchDraw(VertexData vertexData, Matrix3x4 transform, Material material) {
        batchDraw(vertexData, transform, null, material);
    }

    public static void batchDraw(VertexData vertexData, Matrix3x4 transform) {
        batchDraw(vertexData, transform, null);
    }

    @Override
    protected void terminate() {

    }
}
