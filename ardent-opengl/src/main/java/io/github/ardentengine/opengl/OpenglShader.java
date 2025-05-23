package io.github.ardentengine.opengl;

import io.github.ardentengine.core.math.Matrix3x4;
import io.github.ardentengine.core.rendering.ShaderProgram;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL41;

import java.util.HashMap;
import java.util.Map;

public class OpenglShader extends ShaderProgram {

    private final int program;
    private final Map<Integer, Integer> shaders;

    private final HashMap<String, Integer> uniformLocation = new HashMap<>();

    public OpenglShader() {
        var program = GL20.glCreateProgram();
        var shaders = new HashMap<Integer, Integer>();
        CleaningSystem.registerAction(this, () -> {
            for (var shader : shaders.values()) {
                GL20.glDetachShader(program, shader);
                GL20.glDeleteShader(shader);
            }
            GL20.glDeleteProgram(program);
        });
        this.program = program;
        this.shaders = shaders;
    }

    private int getShader(int type) {
        // Create a new shader for this type or return the already existing one
        return this.shaders.computeIfAbsent(type, GL20::glCreateShader);
    }

    private void setShaderCode(int type, CharSequence code) {
        var shader = this.getShader(type);
        GL20.glShaderSource(shader, code);
        GL20.glCompileShader(shader);
        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            // TODO: Better compilation output
            System.out.println(GL20.glGetShaderInfoLog(shader));
        }
        GL20.glAttachShader(this.program, shader);
    }

    @Override
    public void setVertexCode(CharSequence code) {
        this.setShaderCode(GL20.GL_VERTEX_SHADER, code);
    }

    @Override
    public void setFragmentCode(CharSequence code) {
        this.setShaderCode(GL20.GL_FRAGMENT_SHADER, code);
    }

    @Override
    public void compile() {
        GL20.glLinkProgram(this.program);
        GL20.glValidateProgram(this.program);
    }

    @Override
    public void start() {
        GL20.glUseProgram(this.program);
    }

    private int getUniformLocation(String variable) {
        return this.uniformLocation.computeIfAbsent(variable, name -> GL20.glGetUniformLocation(this.program, name));
    }

    @Override
    public void setUniform(String name, Matrix3x4 value) {
        var buffer = BufferUtils.createFloatBuffer(16);
        buffer.put(value.m00()).put(value.m01()).put(value.m02()).put(value.m03());
        buffer.put(value.m10()).put(value.m11()).put(value.m12()).put(value.m13());
        buffer.put(value.m20()).put(value.m21()).put(value.m22()).put(value.m23());
        buffer.put(0.0f).put(0.0f).put(0.0f).put(1.0f);
        GL41.glProgramUniformMatrix4fv(this.program, this.getUniformLocation(name), true, buffer.flip());
    }
}
