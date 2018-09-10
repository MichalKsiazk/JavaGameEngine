package engine;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Vertex {
    public Vertex(Vector3f pos, Vector2f texCoord, Vector3f normal)
    {
        this.pos = pos;
        this.texCoord = texCoord;
        this.normal = normal;
    }


    public Vertex(){
        this.pos = new Vector3f();
        this.texCoord = new Vector2f();
        this.normal = new Vector3f();
        this.material = new Vector3f();
    }


    public Vertex(Vector3f pos, Vector2f texCoord, Vector3f normal, Vector3f material)
    {
        this.pos = pos;
        this.texCoord = texCoord;
        this.normal = normal;
        this.material = material;
    }

    public Vector3f GetPos() { return pos; }
    public Vector2f GetTexCoord() { return texCoord; }
    public Vector3f GetNormal() { return normal; }
    public Vector3f GetMaterial() { return material; }


    public void SetPos(Vector3f pos) { this.pos = pos; }
    public void SetTexCoord(Vector2f texCoord) { this.texCoord = texCoord; }
    public void SetNormal(Vector3f normal) { this.normal = normal; }
    public void SetMaterial(Vector3f material) { this.material = material; }

    private Vector3f pos;
    private Vector2f texCoord;
    private Vector3f normal;
    private Vector3f material;
}

