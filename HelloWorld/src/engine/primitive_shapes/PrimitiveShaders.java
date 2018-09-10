package engine.primitive_shapes;

import engine.OneFileShaderBuilder;
import engine.Utils;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class PrimitiveShaders {

    public int circleShader;
    public int sphereShader;
    public int lineShader;

    public OneFileShaderBuilder ofs;

    public int POS_VB;
    public int INDEX_VB;

    public PrimitiveShaders(String shaderPath) {
        ofs = new OneFileShaderBuilder(shaderPath);
        ofs.LoadRawSource();
        circleShader = ofs.CreateShaderProgram("CIRCLE");

        InitVB();
    }

    void InitVB() {
        POS_VB = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, POS_VB);

        Vector3f pos = new Vector3f(0,0,-1);
        FloatBuffer pos_buffer = BufferUtils.createFloatBuffer(12);
        pos_buffer.put(pos.x);
        pos_buffer.put(pos.y);
        pos_buffer.put(pos.z);
        pos_buffer.flip();

        IntBuffer buffer = BufferUtils.createIntBuffer(4);
        buffer.put(1);


        INDEX_VB = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, INDEX_VB);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        glBufferData(GL_ARRAY_BUFFER, pos_buffer ,GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    void DrawVB() {
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, POS_VB);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, INDEX_VB);
        glDrawElements(GL_POINTS, 1, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }


}
