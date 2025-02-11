package io.github.ardentengine.core.temp;

import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.rendering.RenderingSystem;
import io.github.ardentengine.core.rendering.VertexData;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class EngineTester extends EngineSystem {

    private final VertexData vertexData = RenderingSystem.getInstance().createVertexData();

    @Override
    protected void initialize() {
        // TODO: Buffers should be created by LWJGL's BufferUtils class
        var vertices = ByteBuffer.allocateDirect(8 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertices.put(0.5f).put(0.5f);
        vertices.put(0.5f).put(-0.5f);
        vertices.put(-0.5f).put(-0.5f);
        vertices.put(-0.5f).put(0.5f);
        vertices.flip();
        this.vertexData.setAttribute(0, vertices, 2);
        var indices = ByteBuffer.allocateDirect(6 * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        indices.put(0).put(1).put(3).put(1).put(2).put(3).flip();
        this.vertexData.setIndices(indices);
    }

    @Override
    protected void process() {
        this.vertexData.draw(6);
    }

    @Override
    protected void terminate() {

    }
}
