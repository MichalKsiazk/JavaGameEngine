package engine;

import engine.primitive_shapes.PrimitiveShaders;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class PrimitiveShapes {

    protected static final String SHADER_PATH = "src/shaders/primitiveShaders.txt";


    public int shader;

    protected int POS_VB;
    protected int INDEX_VB;


    Vector3f color;
    public Transform transform;

    boolean visible;

    protected PrimitiveShaders ps;

    public enum Type {
        FILLED,
        EDGES
    }


    public PrimitiveShapes() {

    }

    protected void Init(String type){



        transform = new Transform(new Vector3f(0,0,0), new Quaternionf(0,0,0), new Vector3f(1,1,1));
        //glUseProgram(shader);
        ShaderBuilder.setMatrix4f(shader, "projectionMatrix", Renderer.ProjectionMatrix());
    }

    public void Update() {
        prepareShader();
    }

    protected void prepareShader() {
        glUseProgram(shader);
        ShaderBuilder.setMatrix4f(shader, "modelMatrix", transform.GetModel());
    }

}
