#version 400 core
layout (location = 0) in vec3 position;
layout (location = 1) in vec2 coords;

out float v_light;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

uniform float light;
out vec2 v_coords;
void main(void) {
	
	v_light = light;
	v_coords = coords;
	
	gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1.0) ;
}