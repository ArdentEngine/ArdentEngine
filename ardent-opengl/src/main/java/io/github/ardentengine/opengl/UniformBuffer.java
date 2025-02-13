package io.github.ardentengine.opengl;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL31;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class UniformBuffer {

    private final int ubo;

    public UniformBuffer(int binding, long size) {
        var ubo = GL15.glGenBuffers();
        CleaningSystem.registerAction(this, () -> GL15.glDeleteBuffers(ubo));
        this.ubo = ubo;
        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, this.ubo);
        GL15.glBufferData(GL31.GL_UNIFORM_BUFFER, size, GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
        GL31.glBindBufferRange(GL31.GL_UNIFORM_BUFFER, binding, this.ubo, 0, size);
    }

    public void put(ByteBuffer data, long offset) {
        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, this.ubo);
        GL15.glBufferSubData(GL31.GL_UNIFORM_BUFFER, offset, data);
        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
    }

    public void put(ByteBuffer data) {
        this.put(data, 0L);
    }

    public void put(FloatBuffer data, long offset) {
        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, this.ubo);
        GL15.glBufferSubData(GL31.GL_UNIFORM_BUFFER, offset, data);
        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
    }

    public void put(FloatBuffer data) {
        this.put(data, 0L);
    }

    public void put(IntBuffer data, long offset) {
        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, this.ubo);
        GL15.glBufferSubData(GL31.GL_UNIFORM_BUFFER, offset, data);
        GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
    }

    public void put(IntBuffer data) {
        this.put(data, 0L);
    }
}
