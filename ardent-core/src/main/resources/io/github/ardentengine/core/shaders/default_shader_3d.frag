#version 450

in vec3 vertex;
in vec2 uv;
in vec3 normal;

layout(std140, binding = 2) uniform Material {
    vec3 ambient;
    vec3 diffuse; // TODO: The user should be allowed to write to these values in custom shaders (SSBO?)
    vec3 specular;
    float shininess;
};

out vec4 frag_color;

void fragment_shader();

struct PointLight {
    vec3 position;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;

    // TODO: Attenuation
};

in vec3 world_position;
in vec3 camera_position;
in vec3 surface_normal;

layout(std140, binding = 3) uniform Lighting {
    PointLight point_lights[128]; // TODO: Hardcoded 128
    int lights_count;
};

vec3 compute_light(PointLight light, vec3 view_direction) {
    // ambient lighting
    vec3 ambient_light = light.ambient * ambient;
    // diffuse lighting
    vec3 light_direction = normalize(light.position - world_position);
    float diffuse_value = max(dot(surface_normal, light_direction), 0.0);
    vec3 diffuse_light = light.diffuse * diffuse_value * diffuse;
    // specular lighting
    vec3 reflection = reflect(-light_direction, surface_normal);
    float specular_value = 0.0;
    // TODO: Can this be done without an 'if'?
    if(shininess > 0.0) {
        specular_value = pow(max(dot(view_direction, reflection), 0.0), shininess);
    }
    vec3 specular_light = light.specular * specular_value * specular;
    // attenuation
//    float distance = length(light.position - world_position);
//    float attenuation = 1.0 / (light.attenuation.x + light.attenuation.y * distance + light.attenuation.z * (distance * distance));
    // result
    return ambient_light + diffuse_light + specular_light;
//    return (ambient + diffuse + specular) * attenuation;
}

void main() {
//    frag_color = vec4(vertex + 0.5, 1.0);
    frag_color = vec4(diffuse, 1.0);
#ifdef SHADER_TYPE
    fragment_shader();
#endif
    vec3 view_direction = normalize(camera_position - world_position);
    vec3 light_result = vec3(0.0);
    for (int i = 0; i < lights_count; i++) {
        light_result += compute_light(point_lights[i], view_direction);
    }
    frag_color.rgb += light_result;
}
