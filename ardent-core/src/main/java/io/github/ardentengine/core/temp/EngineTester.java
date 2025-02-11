package io.github.ardentengine.core.temp;

import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.rendering.RenderingSystem;
import io.github.ardentengine.core.rendering.ShaderProgram;
import io.github.ardentengine.core.rendering.TextureData;
import io.github.ardentengine.core.rendering.VertexData;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class EngineTester extends EngineSystem {

    private final VertexData vertexData = RenderingSystem.getInstance().createVertexData();
    private final ShaderProgram shaderProgram = RenderingSystem.getInstance().createShader();
    private final TextureData texture = RenderingSystem.getInstance().createTexture();

    @Override
    protected void initialize() {
        // TODO: Buffers should be created by LWJGL's BufferUtils class
        var vertices = ByteBuffer.allocateDirect(12 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertices.put(0.5f).put(0.5f).put(0.0f);
        vertices.put(0.5f).put(-0.5f).put(0.0f);
        vertices.put(-0.5f).put(-0.5f).put(0.0f);
        vertices.put(-0.5f).put(0.5f).put(0.0f);
        vertices.flip();
        this.vertexData.setAttribute(0, vertices, 3);
        var indices = ByteBuffer.allocateDirect(6 * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        indices.put(0).put(1).put(3).put(1).put(2).put(3).flip();
        this.vertexData.setIndices(indices);
        // TODO: Create a shader loader and a shader resource
        this.shaderProgram.setVertexCode(
            """
            #version 450

            layout(location = 0) in vec3 in_vertex;

            out vec3 vertex;
            out vec2 uv;

            void main() {
                vertex = in_vertex;
                uv = vertex.xy + 0.5;
                gl_Position = vec4(vertex, 1.0);
            }
            """
        );
        this.shaderProgram.setFragmentCode(
            """
            #version 450

            in vec3 vertex;
            in vec2 uv;

            out vec4 frag_color;

            layout(binding = 0) uniform sampler2D main_texture;

            void main() {
                frag_color = texture(main_texture, uv);
            }
            """
        );
        this.shaderProgram.compile();
        // TODO: Create texture resources and load textures
        var pixels = ByteBuffer.allocateDirect(4).putInt(0x00ff00ff).flip();
        this.texture.setTexture(pixels, 1, 1);
    }

    @Override
    protected void process() {
        this.shaderProgram.start();
        this.texture.bind(0);
        this.vertexData.draw(6);
    }

    @Override
    protected void terminate() {

    }
}
