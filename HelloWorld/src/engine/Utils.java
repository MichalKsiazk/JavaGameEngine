package engine;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.openvr.Texture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Utils {
    static public FloatBuffer newFloatBuffer(float data[], boolean flip){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        if(flip)
            buffer.flip();
        return buffer;
    }

    static public FloatBuffer newFloatBuffer3f(ArrayList<Vector3f> data, boolean flip){

        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.size() * 3);
        for(Vector3f v3f : data) {
            buffer.put(v3f.x);
            buffer.put(v3f.y);
            buffer.put(v3f.z);
        }
        if(flip)
            buffer.flip();
        return buffer;
    }

    static public FloatBuffer newFloatBuffer3f(Vector3f data[], boolean flip){

        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length * 3);
        for(Vector3f v3f : data) {
            buffer.put(v3f.x);
            buffer.put(v3f.y);
            buffer.put(v3f.z);
        }
        if(flip)
            buffer.flip();
        return buffer;
    }

    static public FloatBuffer newFloatBuffer2f(ArrayList<Vector2f> data){

        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.size() * 2);
        for(Vector2f v2f : data) {
            buffer.put(v2f.x);
            buffer.put(v2f.y);
        }
        buffer.flip();
        return buffer;
    }

    static public FloatBuffer newFloatBuffer4f(ArrayList<Vector4f> data){

        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.size() * 4);
        for(Vector4f v4f : data) {
            buffer.put(v4f.x);
            buffer.put(v4f.y);
            buffer.put(v4f.z);
            buffer.put(v4f.w);
        }
        buffer.flip();
        return buffer;
    }


    static public IntBuffer newIntBuffer(int data[]){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    static public IntBuffer newIntBuffer(ArrayList<Integer> data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.size());
        for(int i : data){
            buffer.put(i);
        }
        buffer.flip();
        return buffer;
    }

    public static void linkMaterials(Vertex[] object, Vector3f[] materials){
        for(int i = 0;  i < object.length; i++){
            object[i].SetMaterial(materials[i]);
        }
    }

}
