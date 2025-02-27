#define SHADER_TYPE default_shader_2d

void fragment_shader() {
    frag_color.rgb = 1.0 - frag_color.rgb;
}
