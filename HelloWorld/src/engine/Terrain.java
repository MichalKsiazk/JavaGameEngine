package engine;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;

public class Terrain {

    public int terrainWidth;
    public int terrainLenght;
    public float fragmentSize;

    public Transform transform;
    public Renderer renderer;

    public Vector3f [] heightMap;

    public ArrayList<Integer> indices;

    int POSITIONS_VB;
    int INDEX_VB;
    int indices_number;

    public int shader;


    public Terrain(Transform transform, int terrainLenght, int terrainWidth, float fragmentSize){
        this.transform = transform;
        this.renderer = new Renderer();
        this.terrainLenght = terrainLenght;
        this.terrainWidth = terrainWidth;
        this.fragmentSize = fragmentSize;
        heightMap = new Vector3f[terrainWidth * terrainLenght];
        indices = new ArrayList<>();
        generateHeightMap();
        initTerrainMesh();
    }

    public void generateHeightMap(){
        for(int z = 0; z < terrainLenght; z++){
            for(int x = 0; x < terrainWidth; x++){
                heightMap[z * terrainWidth + x] = new Vector3f(x * fragmentSize, (float)ThreadLocalRandom.current().nextInt(0, 200) * 0.01f, z * fragmentSize);
            }
        }

        for(int z = 0; z < terrainLenght - 1; z++){
            for(int x = 0; x < terrainWidth - 1; x++){
                indices.add(z * terrainWidth + x);
                indices.add((z + 1) * terrainWidth + x);
                indices.add(z * terrainWidth + x + 1);
                indices.add(z * terrainWidth + x + 1);
                indices.add((z + 1) * terrainWidth + x + 1);
                indices.add((z + 1) * terrainWidth + x);
            }
        }
    }

    public void initTerrainMesh(){
        indices_number = indices.size();

        POSITIONS_VB = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, POSITIONS_VB);
        glBufferData(GL_ARRAY_BUFFER, Utils.newFloatBuffer3f(heightMap, true), GL_STATIC_DRAW);
        //glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        INDEX_VB = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, INDEX_VB);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.newIntBuffer(indices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void Update() {
        glUseProgram(shader);
        ShaderBuilder.setMatrix4f(shader, "projectionMatrix", renderer.projectionMatrix);
        ShaderBuilder.setMatrix4f(shader, "modelMatrix", transform.GetModel());

        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, POSITIONS_VB);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, INDEX_VB);
        glDrawElements(GL_TRIANGLES, indices_number, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDisableVertexAttribArray(0);
    }

}
