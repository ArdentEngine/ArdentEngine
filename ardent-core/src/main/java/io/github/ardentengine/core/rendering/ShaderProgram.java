package io.github.ardentengine.core.rendering;

public abstract class ShaderProgram {

    public abstract void setVertexCode(CharSequence code);

    public abstract void setFragmentCode(CharSequence code);

    // TODO: Add methods that accept code as byte[] to allow binary SPIR-V in Vulkan

    public abstract void compile();

    public abstract void start();
}
