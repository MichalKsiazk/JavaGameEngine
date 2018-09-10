#version 330 core

out vec4 color;
in vec2 textCoord0;
in vec3 customColor0;
uniform sampler2D sampler;


void main(){
	    color = texture(sampler, textCoord0);
}