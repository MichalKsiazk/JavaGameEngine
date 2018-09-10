package engine;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class OBJLoader {

    public static IndexedModel RawModel(String filePath){



        ArrayList<String> output = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                output.add(line);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("error while loading file");
            e.printStackTrace();
            System.exit(-1);
        }

        ArrayList<Vector3f> positions = new ArrayList<>();
        ArrayList<Vector2f> texCoords = new ArrayList<>();
        ArrayList<Vector3f> normals = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();


        for(String line : output){
            if(line.startsWith("vn "))
                normals.add(CastToVector3f(line));
            else if(line.startsWith("vt "))
                texCoords.add(CastToVector2f(line));
            else if(line.startsWith("v "))
                positions.add(CastToVector3f(line));
        }

        Vector3f positionArray[] = new Vector3f[positions.size()];
        Vector2f texCoordsArray[] = new Vector2f[positions.size()];
        Vector3f normalsArray[] = new Vector3f[positions.size()];

        System.out.println("SIZE P:" + positions.size());
        System.out.println("SIZE T:" + texCoords.size());
        System.out.println("SIZE N:" + normals.size());


        for(String line : output) {
            if(line.startsWith("f ")){
                String faces[] = line.split(" ");

                String vertex1[] = faces[1].split("/");
                String vertex2[] = faces[2].split("/");
                String vertex3[] = faces[3].split("/");

                processVertex(vertex1, indices, positions, texCoords, normals, positionArray, texCoordsArray, normalsArray);
                processVertex(vertex2, indices, positions, texCoords, normals, positionArray, texCoordsArray, normalsArray);
                processVertex(vertex3, indices, positions, texCoords, normals, positionArray, texCoordsArray, normalsArray);

            }


        }

        for (int i = 0; i < positionArray.length; i++){
            positionArray[i] = positions.get(i);
        }

        int indicesArray[] = new int[indices.size()];
        for(int i = 0; i < indices.size(); i++){
            indicesArray[i] = indices.get(i);
        }

        return new IndexedModel(positionArray, texCoordsArray, normalsArray, indicesArray);

    }

    private static Vector3f CastToVector3f(String line){
        String split[] = line.split(" ");
        return new Vector3f(Float.parseFloat(split[1]), Float.parseFloat(split[2]), Float.parseFloat(split[3]));
    }

    private static Vector2f CastToVector2f(String line){
        String split[] = line.split(" ");
        return new Vector2f(Float.parseFloat(split[1]), Float.parseFloat(split[2]));
    }


    private static void processVertex(String[] vertex,
                                      ArrayList<Integer> indices,
                                      ArrayList<Vector3f> positions,
                                      ArrayList<Vector2f> texCoords,
                                      ArrayList<Vector3f> normals,
                                      Vector3f[] positionArray,
                                      Vector2f[] texCoordArray,
                                      Vector3f[] normalsArray) {

        int vertexPointer = Integer.parseInt(vertex[0]) - 1;
        //System.out.println(vertexPointer);
        indices.add(vertexPointer);

        if(!vertex[1].equals("")) {
            //System.out.println(vertexPointer + ": empty vertex");
            Vector2f texCoord = texCoords.get(Integer.parseInt(vertex[1]) - 1);
            texCoordArray[vertexPointer] = new Vector2f(texCoord.x, 1 - texCoord.y);
        }
        else{
            texCoordArray[vertexPointer] = new Vector2f(0, 0);
        }
        normalsArray[vertexPointer] = normals.get(Integer.parseInt(vertex[2]) - 1);

    }


}
