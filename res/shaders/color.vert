#version 400 core
layout (location = 0) in vec3 position;
layout (location = 1) in vec4 colors;
out vec4 v_colors;
out float v_light;
uniform mat4 perspective;
uniform vec3 perspectivePosition;
uniform float light;

void main(void) {
	
	v_light = light;
	v_colors = colors;
	
	gl_Position = perspective * vec4(position - perspectivePosition, 1.0) ;
}