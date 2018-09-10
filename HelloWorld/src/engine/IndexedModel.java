package engine;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class IndexedModel {

    public Vertex[] vertices;
    public int[] indices;

    public IndexedModel(Vertex[] vertices, int[] indices){
        this.vertices = vertices;
        this.indices = indices;

    }

    public IndexedModel(Vector3f[] positions, Vector2f[] textCoords, Vector3f[] normals, int indices[]){

        this.vertices = new Vertex[positions.length];
        this.indices = indices;

        for(int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex(positions[i],  textCoords[i], normals[i]);
        }
    }


}
