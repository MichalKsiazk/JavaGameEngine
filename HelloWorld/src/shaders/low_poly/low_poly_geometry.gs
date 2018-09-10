
//GEOMETRY SHADER


#version 330 core


layout ( triangles ) in;
layout ( line_strip, max_vertices = 6 ) out;


out vec3 color;


void main(void){

    color = vec3(0,1,0);
    gl_Position = gl_in[0].gl_Position;
    EmitVertex();

    color = vec3(0,1,0);
    gl_Position = gl_in[1].gl_Position;
    EmitVertex();

    EndPrimitive();



    color = vec3(0,1,0);
    gl_Position = gl_in[1].gl_Position;
    EmitVertex();

    color = vec3(0,1,0);
    gl_Position = gl_in[2].gl_Position;
    EmitVertex();

    EndPrimitive();



    color = vec3(0,1,0);
    gl_Position = gl_in[0].gl_Position;
    EmitVertex();

    color = vec3(0,1,0);
    gl_Position = gl_in[2].gl_Position;
    EmitVertex();

    EndPrimitive();
}