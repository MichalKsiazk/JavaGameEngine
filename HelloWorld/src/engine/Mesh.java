package engine;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glDrawElementsBaseVertex;
import static org.lwjgl.system.MemoryStack.stackPush;


public class Mesh {

    public int type;


    static int NUM_BUFFERS = 4;

    int POSITIONS_VB;
    int TEXTURES_VB;
    int NORMALS_VB;
    int INDEX_VB;
    int MATERIAL_VB;



    int vertexArrayObject;
    int vertexBufferObjects[];


    int indices_number;

    private boolean use_materials;

    public Mesh(Vertex vertices[], int numVertices, int indices[], int numIndices, boolean use_materials, int type)
    {
        this.use_materials = false;
        this.type = type;
        //vertexArrayObject = newVertexArrayObject();

        Model model = new Model();

        for (int i = 0; i < numVertices; i++)
        {
            model.positions.add(vertices[i].GetPos());
            model.texCoords.add(vertices[i].GetTexCoord());
            model.normals.add(vertices[i].GetNormal());
            model.materials.add(vertices[i].GetMaterial());
        }

        for (int i = 0; i < numIndices; i++)
            model.indices.add(indices[i]);

        InitMesh(model);
    }










    int newVertexArrayObject() {
        int vao_id = glGenVertexArrays();
        glBindVertexArray(vao_id);
        return vao_id;
    }

    public void render() {

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
       // glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);

        glBindBuffer(GL_ARRAY_BUFFER, POSITIONS_VB);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, TEXTURES_VB);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);


       if(use_materials) {
           glBindBuffer(GL_ARRAY_BUFFER, MATERIAL_VB);
           glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
       }

        glBindBuffer(GL_ARRAY_BUFFER, NORMALS_VB);
        glVertexAttribPointer(3, 3, GL_FLOAT, false, 0, 0);



        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, INDEX_VB);
        glDrawElements(type, indices_number, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);


        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
       // glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
    }

    void InitMesh(Model model)
    {

        indices_number = model.indices.size();

        POSITIONS_VB = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, POSITIONS_VB);
        glBufferData(GL_ARRAY_BUFFER, Utils.newFloatBuffer3f(model.positions,  true), GL_STATIC_DRAW);
        //glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);



        TEXTURES_VB = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, TEXTURES_VB);
        glBufferData(GL_ARRAY_BUFFER, Utils.newFloatBuffer2f(model.texCoords), GL_STATIC_DRAW);
        //glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        if(use_materials) {
            MATERIAL_VB = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, MATERIAL_VB);
            glBufferData(GL_ARRAY_BUFFER, Utils.newFloatBuffer3f(model.materials, true), GL_STATIC_DRAW);
        //    //glEnableVertexAttribArray(2);
            glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
        }

        NORMALS_VB = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, NORMALS_VB);
        glBufferData(GL_ARRAY_BUFFER, Utils.newFloatBuffer3f(model.normals, true), GL_STATIC_DRAW);
        glVertexAttribPointer(3, 3, GL_FLOAT, false, 0, 0);




        INDEX_VB = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, INDEX_VB);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.newIntBuffer(model.indices), GL_STATIC_DRAW);


        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
