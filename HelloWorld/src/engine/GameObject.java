package engine;

import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL20.glUseProgram;

public class GameObject {

    private Transform transform;
    private int shader;
    public Texture texture;
    public Renderer renderer;

    private Mesh mesh;

    public Vector4f customColor;

    public GameObject(Transform transform){
        this.transform = transform;
        customColor = new Vector4f(1,1,1,1);
        renderer = new Renderer();
    }


    public void Update(){
        if(shader != 0) {
            glUseProgram(shader);
            ShaderBuilder.setMatrix4f(shader, "modelMatrix", transform.GetModel());
            ShaderBuilder.setMatrix4f(shader, "projectionMatrix", renderer.projectionMatrix);
        }
        texture.setTextures(false);
        texture.bindTexture(true);
        texture.setTextures(true);

        if(mesh != null){
            mesh.render();
        }
        texture.bindTexture(false);
    }







    public void scaleWithTextureSize(float multiplier){
        transform.setScale(texture.width * 0.001f * multiplier,texture.height* 0.001f * multiplier, 1);
    }

    public Vector2f getTextureRatio(){
        return new Vector2f(texture.width * 0.001f, texture.height * 0.001f);
    }



    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public int getShader() {
        return shader;
    }

    public void setShader(int shader) {
        this.shader = shader;
    }
}
