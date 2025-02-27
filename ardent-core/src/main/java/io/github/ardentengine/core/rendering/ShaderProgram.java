package io.github.ardentengine.core.rendering;

import io.github.ardentengine.core.math.Matrix3x4;

public abstract class ShaderProgram {

    // TODO: Shader files (.vert and .frag) can't stay in the core module because in OpenGL they are stored in the jar as text and in Vulkan the jar must contain the compiled SPIR-V bytecode

    public abstract void setVertexCode(CharSequence code);

    public abstract void setFragmentCode(CharSequence code);

    // TODO: Add methods that accept code as byte[] to allow binary SPIR-V in Vulkan

    public abstract void compile();

    public abstract void start();

    // TODO: This is only temporary until instanced rendering is implemented
    //  Uniform variables shouldn't be set directly from the shader because it affects all objects and also because it is not supported in Vulkan
    public abstract void setUniform(String name, Matrix3x4 value);
}
