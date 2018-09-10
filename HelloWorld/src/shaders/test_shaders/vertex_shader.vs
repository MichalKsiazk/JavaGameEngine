#version 330 core


layout(location = 0) in vec3 position;
layout(location = 1) in vec2 coords;

layout(location = 2) in vec3 matt;

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

out vec2 textCoord0;
out vec3 customColor0;


void main()
{
    customColor0 = matt;
	gl_Position =  projectionMatrix * viewMatrix * modelMatrix * vec4(position, 1);
	textCoord0 = coords;
}