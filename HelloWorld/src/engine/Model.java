package engine;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;

public class Model {
    public ArrayList<Vector3f> positions;
    public ArrayList<Vector2f> texCoords;
    public ArrayList<Vector3f> normals;
    public ArrayList<Integer> indices;
    public ArrayList<Vector3f> materials;

    public Model(){
        positions = new ArrayList<>();
        texCoords = new ArrayList<>();
        normals = new ArrayList<>();
        indices = new ArrayList<>();
        materials = new ArrayList<>();
    }
}

