#version 450

layout(location = 0) in vec2 vertex;
layout(location = 1) in vec2 uv;

out vec4 frag_color;

uniform mat3x2 transformation_matrix;
uniform mat3 view_matrix;
uniform mat4 projection_matrix;

uniform int z_index;
uniform ivec2 texture_size;
uniform vec2 offset;
uniform int flip_h;
uniform int flip_v;
uniform int h_frames;
uniform int v_frames;
uniform int frame;

uniform sampler2D sprite_texture;
uniform vec4 modulate = vec4(1.0);

void vertex_shader() {
    vec2 frames = vec2(h_frames, v_frames);
    vec2 uv_scale = vec2(flip_h, flip_v) / frames;
    vec2 uv_offset = vec2(frame % h_frames, frame / h_frames) / frames;
    vertex = vertex * texture_size / frames + offset;
    uv = uv * uv_scale + uv_offset;
    vec2 world_position = transformation_matrix * vec3(vertex, 1.0);
    gl_Position = projection_matrix * vec4(view_matrix * vec3(world_position, z_index), 1.0);
}

void fragment_shader() {
    frag_color = modulate * texture(sprite_texture, uv);
}