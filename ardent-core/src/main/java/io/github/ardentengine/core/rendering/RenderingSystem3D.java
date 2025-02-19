package io.github.ardentengine.core.rendering;

import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.math.Matrix3x4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class RenderingSystem3D extends EngineSystem {

    private static final HashMap<VertexData, Collection<Matrix3x4>> BATCH = new HashMap<>();

    private final ShaderProgram defaultShader = RenderingApi.getInstance().createShader();

    @Override
    protected void initialize() {
        // TODO: Get the shader code from the ShaderLoader
        this.defaultShader.setVertexCode(
            """
            #version 450

            layout(location = 0) in vec3 in_vertex;
            layout(location = 1) in vec2 in_uv;
            layout(location = 2) in vec3 in_normal;

            out vec3 vertex;
            out vec2 uv;
            out vec3 normal;

            uniform mat4 transform;

            layout(std140, binding = 1) uniform Camera3D {
                mat4 view_matrix;
                mat4 projection_matrix;
            };

            void main() {
                vertex = in_vertex;
                uv = in_uv;
                normal = in_normal;
                gl_Position = projection_matrix * view_matrix * transform * vec4(vertex, 1.0);
            }
            """
        );
        this.defaultShader.setFragmentCode(
            """
            #version 450

            in vec3 vertex;
            in vec2 uv;
            in vec3 normal;

            out vec4 frag_color;

            void main() {
                frag_color = vec4(vertex + 0.5, 1.0);
            }
            """
        );
        this.defaultShader.compile();
    }

    @Override
    protected void process() {
        RenderingApi.getInstance().setDepthTest(true);
        this.defaultShader.start();
        for (var vertexData : BATCH.keySet()) {
            // TODO: Use instanced rendering instead
            for (var transform : BATCH.get(vertexData)) {
                this.defaultShader.setUniform("transform", transform);
                vertexData.draw();
            }
        }
        BATCH.clear();
    }

    public static void batchDraw(VertexData vertexData, Matrix3x4 transform) {
        BATCH.computeIfAbsent(vertexData, v -> new ArrayList<>()).add(transform);
    }

    @Override
    protected void terminate() {

    }
}
