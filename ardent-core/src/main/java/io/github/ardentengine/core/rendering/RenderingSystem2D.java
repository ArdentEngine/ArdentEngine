package io.github.ardentengine.core.rendering;

import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.math.Matrix2x3;
import io.github.ardentengine.core.math.Rect2;
import io.github.ardentengine.core.resources.ShaderLoader;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.function.Consumer;

public class RenderingSystem2D extends EngineSystem {

    private static final int MAX_BATCH_SIZE = 4096;

    private static final ArrayList<Consumer<RenderingSystem2D>> BATCH = new ArrayList<>();

    private final VertexData vertexData = RenderingApi.getInstance().createVertexData();
    private final ShaderProgram defaultShader = RenderingApi.getInstance().createShader();
    private final TextureData defaultTexture = RenderingApi.getInstance().createTexture();

    private final FloatBuffer vertices = RenderingApi.getInstance().createFloatBuffer(MAX_BATCH_SIZE * 8);
    private final FloatBuffer uvs = RenderingApi.getInstance().createFloatBuffer(MAX_BATCH_SIZE * 8);
    // TODO: Use a buffer for vertex colors

    private ShaderProgram currentShader = this.defaultShader;
    private TextureData currentTexture = this.defaultTexture;
    private int current = 0;

    @Override
    protected void initialize() {
        // Allocate spaces for vertices
        this.vertexData.allocateSpace(0, MAX_BATCH_SIZE * 4, 2);
        // Indices never need to be modified
        var indices = RenderingApi.getInstance().createIntBuffer(MAX_BATCH_SIZE * 6);
        for (int i = 0; i < MAX_BATCH_SIZE * 4; i += 4) {
            indices.put(i).put(i + 1).put(i + 3);
            indices.put(i + 3).put(i + 1).put(i + 2);
        }
        this.vertexData.setIndices(indices.flip());
        // Allocate space for texture coordinates
        this.vertexData.allocateSpace(1, MAX_BATCH_SIZE * 4, 2);
        // Create the default shader
        this.defaultShader.setVertexCode(ShaderLoader.getBuiltinShaderCode("default_shader_2d.vert"));
        this.defaultShader.setFragmentCode(ShaderLoader.getBuiltinShaderCode("default_shader_2d.frag"));
        this.defaultShader.compile();
        // Create the default texture
        this.defaultTexture.setTexture(ByteBuffer.allocateDirect(4).putInt(0xffffffff).flip(), 1, 1);
    }

    private void flush() {
        // Use the current shader and the current texture
        this.currentShader.start();
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

    private void batch(TextureData texture, Matrix2x3 transform, Rect2 region, ShaderProgram shader) {
        // Flush the batch if the texture has changed
        if(this.current > 0 && (this.currentTexture != texture || this.currentShader != shader)) {
            // TODO: Multiple textures can be used in the same draw call
            this.flush();
        }
        // Update the batch to draw this texture
        this.currentShader = shader;
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

    private void batch(TextureData texture, Matrix2x3 transform, Rect2 region) {
        this.batch(texture, transform, region, this.defaultShader);
    }

    @Override
    protected void process() {
        // TODO: Should depth test be enabled or disabled?
        // Run through the batch in order
        var iterator = BATCH.iterator();
        while (iterator.hasNext()) {
            iterator.next().accept(this);
            iterator.remove();
        }
        // Flush the batch to draw the last elements if there are any
        if (this.current > 0) {
            this.flush();
        }
    }

    // TODO: Separate opaque (alpha is either 1.0 or 0.0) from translucent (alpha is between 0.0 and 1.0)

    public static void batchDraw(TextureData texture, Matrix2x3 transform, Rect2 region, ShaderProgram shader) {
        // TODO: This also needs a material
        BATCH.add(renderingSystem -> renderingSystem.batch(texture, transform, region, shader));
    }

    public static void batchDraw(TextureData texture, Matrix2x3 transform, Rect2 region) {
        BATCH.add(renderingSystem -> renderingSystem.batch(texture, transform, region));
    }

    @Override
    protected void terminate() {

    }
}
