import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class Renderer {

    int v_lenght;
    int r_lenght;

    public Renderer(){

    }

    public int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    public void createVBO(int attributeNumber, float[] data){
        int vbo_vertex_ID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL_ARRAY_BUFFER, vbo_vertex_ID);

        glVertexAttribPointer(0, 3, GL_FLOAT, false,0,0);
    }

    public FloatBuffer newFloatBuffer(float data[]){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public IntBuffer newByteBuffer(int data[]){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
