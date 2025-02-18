package io.github.ardentengine.core.rendering;

import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.math.Matrix2x3;
import io.github.ardentengine.core.math.Rect2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.function.Consumer;

public class RenderingSystem2D extends EngineSystem {

    private static final int MAX_BATCH_SIZE = 4096;

    private static final ArrayList<Consumer<RenderingSystem2D>> BATCH = new ArrayList<>();

    private final VertexData vertexData = RenderingApi.getInstance().createVertexData();
    private final ShaderProgram defaultShader = RenderingApi.getInstance().createShader();

    // TODO: Add a proper way to create buffers such as LWJGL's BufferUtils
    private final FloatBuffer vertices = ByteBuffer.allocateDirect(MAX_BATCH_SIZE * 8 * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
    private final FloatBuffer uvs = ByteBuffer.allocateDirect(MAX_BATCH_SIZE * 8 * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
    // TODO: Use a buffer for vertex colors

    // TODO: Use a default 1x1 white texture instead so that this is never null to allow to render just rectangles
    private TextureData currentTexture = null;
    private int current = 0;

    @Override
    protected void initialize() {
        // Allocate spaces for vertices
        this.vertexData.setAttribute(0, MAX_BATCH_SIZE * 8 * Float.BYTES, 2);
        // Indices never need to be modified
        var indices = ByteBuffer.allocateDirect(MAX_BATCH_SIZE * 6 * Integer.BYTES).order(ByteOrder.nativeOrder()).asIntBuffer();
        for (int i = 0; i < MAX_BATCH_SIZE * 4; i += 4) {
            indices.put(i).put(i + 1).put(i + 3);
            indices.put(i + 3).put(i + 1).put(i + 2);
        }
        this.vertexData.setIndices(indices.flip());
        // Allocate space for texture coordinates
        this.vertexData.setAttribute(1, MAX_BATCH_SIZE * 8 * Float.BYTES, 2);
        // TODO: Get the shader code from the ShaderLoader
        this.defaultShader.setVertexCode(
            """
            #version 450

            layout(location = 0) in vec2 in_vertex;
            layout(location = 1) in vec2 in_uv;

            out vec2 vertex;
            out vec2 uv;

            layout(std140, binding = 0) uniform Camera2D {
                mat3 view_matrix;
                mat4 projection_matrix;
            };

            void main() {
                vertex = in_vertex;
                uv = in_uv;
                vec3 world_position = vec3(vertex, 1.0);
                gl_Position = projection_matrix * vec4(view_matrix * world_position, 1.0);
            }
            """
        );
        this.defaultShader.setFragmentCode(
            """
            #version 450

            in vec2 vertex;
            in vec2 uv;

            out vec4 frag_color;

            //uniform ivec2 texture_size;
            layout(binding = 0) uniform sampler2D main_texture;

            void main() {
            //    frag_color = vec4(uv, 0.0, 1.0);
                frag_color = texture(main_texture, uv);
            }
            """
        );
        // TODO: Discard fragments with a = 0.0 so that they are not written to the depth buffer
        this.defaultShader.compile();
    }

    private void flush() {
        // Use the current shader and the current texture
        this.defaultShader.start();
        this.currentTexture.bind(0);
        // Update the buffers
        this.vertexData.updateAttribute(0, 0, this.vertices.flip());
        this.vertexData.updateAttribute(1, 0, this.uvs.flip());
        // The batch consists of six indices per element
        this.vertexData.draw(this.current * 6);
        // Reset the buffers
        this.vertices.clear();
        this.uvs.clear();
        this.current = 0;
    }

    private void batch(TextureData texture, Matrix2x3 transform, Rect2 region) {
        // Flush the batch if the texture has changed
        if(this.current > 0 && this.currentTexture != texture) {
            this.flush();
        }
        // Update the batch to draw this texture
        this.currentTexture = texture;
        var halfWidth = texture.width() * 0.5f;
        var halfHeight = texture.height() * 0.5f;
        // Top-left vertex
        this.vertices.put(transform.m00() * -halfWidth + transform.m01() * halfHeight + transform.m02());
        this.vertices.put(transform.m10() * -halfWidth + transform.m11() * halfHeight + transform.m12());
        this.uvs.put(region.left());
        this.uvs.put(region.bottom());
        // Bottom-left vertex
        this.vertices.put(transform.m00() * -halfWidth + transform.m01() * -halfHeight + transform.m02());
        this.vertices.put(transform.m10() * -halfWidth + transform.m11() * -halfHeight + transform.m12());
        this.uvs.put(region.left());
        this.uvs.put(region.top());
        // Bottom-right vertex
        this.vertices.put(transform.m00() * halfWidth + transform.m01() * -halfHeight + transform.m02());
        this.vertices.put(transform.m10() * halfWidth + transform.m11() * -halfHeight + transform.m12());
        this.uvs.put(region.right());
        this.uvs.put(region.top());
        // Top-right vertex
        this.vertices.put(transform.m00() * halfWidth + transform.m01() * halfHeight + transform.m02());
        this.vertices.put(transform.m10() * halfWidth + transform.m11() * halfHeight + transform.m12());
        this.uvs.put(region.right());
        this.uvs.put(region.bottom());
        // Increase the current index and flush the batch if the max size has been reached
        this.current++;
        if(this.current == MAX_BATCH_SIZE) {
            this.flush();
        }
    }

    @Override
    protected void process() {
        // Run through the batch in order
        var iterator = BATCH.iterator();
        while (iterator.hasNext()) {
            iterator.next().accept(this);
            iterator.remove();
        }
        // Flush the batch to draw the last elements
        this.flush();
    }

    public static void batchDraw(TextureData texture, Matrix2x3 transform, Rect2 region) {
        // TODO: Separate opaque (alpha is either 1.0 or 0.0) from translucent (alpha is between 0.0 and 1.0)
        BATCH.add(renderingSystem -> renderingSystem.batch(texture, transform, region));
    }

    @Override
    protected void terminate() {

    }
}
