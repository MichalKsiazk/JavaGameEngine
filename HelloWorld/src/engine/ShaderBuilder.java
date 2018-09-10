package engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

public class ShaderBuilder {


    static public String LoadShaderFileToString(String path) {
        String output = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                output = output + (line) + "\n";
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("error while loading file");
            e.printStackTrace();
            System.exit(-1);
        }
        return output;
    }

    static public ArrayList<String> LoadShaderFileToArray(String path){
        ArrayList<String> output = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
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
        return output;
    }



    public ShaderBuilder() {
    }


    public static int newShaderProgram(String vertexShaderPath, String fragmentShaderPath) {
        int vertexShader = newShader(vertexShaderPath, GL_VERTEX_SHADER);
        int fragmentShader = newShader(fragmentShaderPath, GL_FRAGMENT_SHADER);

        int program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);

        if(glGetProgrami(program, GL_LINK_STATUS) == 0){
            System.err.println(glGetShaderInfoLog(program));
            System.exit(1);
        }

        glValidateProgram(program);

        if(glGetProgrami(program, GL_VALIDATE_STATUS) == 0){
            System.err.println(glGetShaderInfoLog(program));
            System.exit(1);
        }

        glDetachShader(program, vertexShader);
        glDetachShader(program, fragmentShader);
        return program;

    }

    public static int newShaderProgram(int vertexShader, int fragmentShader) {

        int program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);

        if(glGetProgrami(program, GL_LINK_STATUS) == 0){
            System.err.println(glGetShaderInfoLog(program));
            System.exit(1);
        }

        glValidateProgram(program);

        if(glGetProgrami(program, GL_VALIDATE_STATUS) == 0){
            System.err.println(glGetShaderInfoLog(program));
            System.exit(1);
        }

        glDetachShader(program, vertexShader);
        glDetachShader(program, fragmentShader);
        return program;

    }


    public static int newShaderProgram(String vertexShaderPath, String geometryShaderPath, String fragmentShaderPath) {
        int vertexShader = newShader(vertexShaderPath, GL_VERTEX_SHADER);
        int geometryShader = newShader(geometryShaderPath, GL_GEOMETRY_SHADER);
        int fragmentShader = newShader(fragmentShaderPath, GL_FRAGMENT_SHADER);

        int program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, geometryShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);

        if(glGetProgrami(program, GL_LINK_STATUS) == 0){
            System.err.println(glGetShaderInfoLog(program));
            System.exit(1);
        }

        glValidateProgram(program);

        if(glGetProgrami(program, GL_VALIDATE_STATUS) == 0){
            System.err.println(glGetShaderInfoLog(program));
            System.exit(1);
        }

        glDetachShader(program, vertexShader);
        glDetachShader(program, geometryShader);
        glDetachShader(program, fragmentShader);
        return program;

    }

    public static int newShaderProgram(int vertexShader, int geometryShader, int fragmentShader) {

        int program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, geometryShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);

        if(glGetProgrami(program, GL_LINK_STATUS) == 0){
            System.err.println(glGetShaderInfoLog(program));
            System.exit(1);
        }

        glValidateProgram(program);

        if(glGetProgrami(program, GL_VALIDATE_STATUS) == 0){
            System.err.println(glGetShaderInfoLog(program));
            System.exit(1);
        }

        glDetachShader(program, vertexShader);
        glDetachShader(program, geometryShader);
        glDetachShader(program, fragmentShader);
        return program;

    }

    public static int newShader(String shaderPath, int type) {
        String rawSource = LoadShaderFileToString(shaderPath);

        int shader = glCreateShader(type);
        glShaderSource(shader, rawSource);

        glCompileShader(shader);

        if(glGetShaderi(shader,  GL_COMPILE_STATUS) == 0){
            System.err.println(shaderPath + ": \n " + glGetShaderInfoLog(shader));
            //System.exit(1);
        }

        return shader;

    }

    public static int newShaderFromString(String source, int type) {

        int shader = glCreateShader(type);
        glShaderSource(shader, source);

        glCompileShader(shader);

        if(glGetShaderi(shader,  GL_COMPILE_STATUS) == 0){
            System.err.println(type + ": \n " + glGetShaderInfoLog(shader));
            //System.exit(1);
        }

        return shader;

    }

















    public static void setMatrix4f(int program, String uniform, Matrix4f transform){
        int uniformLocation = glGetUniformLocation(program, uniform);

        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        transform.get(fb);

        glUniformMatrix4fv(uniformLocation, false , fb);
    }

    public static void setVector4f(int program, String uniform, Vector4f transform){
        int uniformLocation = glGetUniformLocation(program, uniform);

        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        transform.get(fb);

        glUniform4f(uniformLocation, transform.x,transform.y,transform.z,transform.w);
    }


    public static void setVector3f(int program, String uniform, Vector3f transform){
        int uniformLocation = glGetUniformLocation(program, uniform);

        FloatBuffer fb = BufferUtils.createFloatBuffer(12);
        transform.get(fb);

        glUniform3f(uniformLocation, transform.x,transform.y,transform.z);
    }


    public static void setVector2f(int program, String uniform, Vector2f vec2){
        int uniformLocation = glGetUniformLocation(program, uniform);

        FloatBuffer fb = BufferUtils.createFloatBuffer(8);
        vec2.get(fb);

        glUniform2f(uniformLocation, vec2.x,vec2.y);
    }

    public static void setInt(int program, String uniform, int value){
        int uniformLocation = glGetUniformLocation(program, uniform);
        glUniform1i(uniformLocation, value);
    }


    public void setMatrix4f(){

    }
}
