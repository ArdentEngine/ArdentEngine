#version 450

in vec2 vertex;
in vec2 uv;

out vec4 frag_color;

//uniform ivec2 texture_size;
layout(binding = 0) uniform sampler2D main_texture;

void fragment_shader();

void main() {
//    frag_color = vec4(uv, 0.0, 1.0);
    frag_color = texture(main_texture, uv);
#ifdef SHADER_TYPE
    fragment_shader();
#endif
    // TODO: Discard fragments with a = 0.0 so that they are not written to the depth buffer
}
