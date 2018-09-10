package engine;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

public class OneFileShaderBuilder {

    private static String filePath;
    private static ArrayList<String> rawSource;

    private ShaderBuilder shaderBuilder;



    public OneFileShaderBuilder(String filePath){
        this.shaderBuilder = new ShaderBuilder();
        this.filePath = filePath;

    }

    public void LoadRawSource() {
        rawSource = ShaderBuilder.LoadShaderFileToArray(filePath);
        //System.out.println(rawSource);
    }

    public static ArrayList<String> RawSource(String filePath) {
        return ShaderBuilder.LoadShaderFileToArray(filePath);
    }

    public static int CreateShaderProgram(String shaderName){

        int startIndex = -1;
        int endIndex = -1;


        for(int lineIndex = 0; lineIndex < rawSource.size(); lineIndex ++) {

            //System.out.println(rawSource.get(lineIndex) + " | " + "### START : " + shaderName);


            if(rawSource.get(lineIndex).equals("### START : " + shaderName)){
                System.out.println("line found");
                startIndex = lineIndex;

                for(int i = lineIndex; i < rawSource.size(); i++){
                    if(rawSource.get(i).equals("### END : " + shaderName)){
                        endIndex = i;
                        break;
                    }
                }
                break;
            }
        }

        System.out.println(startIndex + " " + endIndex);

        if(startIndex == -1 || endIndex == -1) {
            System.out.println("ERROR WHILE LOADING: " + shaderName);
            return -1;
        }

        int vertexShader = OneFileShaderBuilder.FindSubShader(startIndex, endIndex, "VERTEX");
        int geometryShader = OneFileShaderBuilder.FindSubShader(startIndex, endIndex, "GEOMETRY");
        int fragmentShader = OneFileShaderBuilder.FindSubShader(startIndex, endIndex, "FRAGMENT");



        if(vertexShader != -1 && fragmentShader != -1 && geometryShader == -1 ){
            return ShaderBuilder.newShaderProgram(vertexShader, fragmentShader);
        }

        if(vertexShader == -1 || fragmentShader == -1){
            System.out.println("VERTEX OR FRAGMENT NOT FOUND");
            return -1;
        }

        if(vertexShader != -1 && fragmentShader != -1 && geometryShader != -1 ){
            int shaderProgram = ShaderBuilder.newShaderProgram(vertexShader, geometryShader, fragmentShader);
            return shaderProgram;
        }




        return -1;
    }

    public static int CreateShaderProgram(String shaderName, String shaderPath){

        int startIndex = -1;
        int endIndex = -1;

        ArrayList<String> rawSource = RawSource(shaderPath);


        for(int lineIndex = 0; lineIndex < rawSource.size(); lineIndex ++) {

            //System.out.println(rawSource.get(lineIndex) + " | " + "### START : " + shaderName);


            if(rawSource.get(lineIndex).equals("### START : " + shaderName)){
                System.out.println("line found");
                startIndex = lineIndex;

                for(int i = lineIndex; i < rawSource.size(); i++){
                    if(rawSource.get(i).equals("### END : " + shaderName)){
                        endIndex = i;
                        break;
                    }
                }
                break;
            }
        }

        System.out.println(startIndex + " " + endIndex);

        if(startIndex == -1 || endIndex == -1) {
            System.out.println("ERROR WHILE LOADING: " + shaderName);
            return -1;
        }

        int vertexShader = OneFileShaderBuilder.FindSubShader(startIndex, endIndex, "VERTEX");
        int geometryShader = OneFileShaderBuilder.FindSubShader(startIndex, endIndex, "GEOMETRY");
        int fragmentShader = OneFileShaderBuilder.FindSubShader(startIndex, endIndex, "FRAGMENT");



        if(vertexShader != -1 && fragmentShader != -1 && geometryShader == -1 ){
            return ShaderBuilder.newShaderProgram(vertexShader, fragmentShader);
        }

        if(vertexShader == -1 || fragmentShader == -1){
            System.out.println("VERTEX OR FRAGMENT NOT FOUND");
            return -1;
        }

        if(vertexShader != -1 && fragmentShader != -1 && geometryShader != -1 ){
            int shaderProgram = ShaderBuilder.newShaderProgram(vertexShader, geometryShader, fragmentShader);
            return shaderProgram;
        }




        return -1;
    }

    private static int FindSubShader(int startIndex, int endIndex, String type){

        int shaderSourceStart = -1;
        int shaderSourceEnd = -1;

        for(int lineIndex = startIndex; lineIndex <= endIndex; lineIndex++){
            if(rawSource.get(lineIndex).contains("START") && rawSource.get(lineIndex).contains(type)){
                shaderSourceStart = lineIndex;
                for(int i = lineIndex; i <= endIndex; i++){
                    if(rawSource.get(i).contains("END") && rawSource.get(i).contains(type)){
                        shaderSourceEnd = i;
                        break;
                    }
                }
                break;
            }
        }

        System.out.println("SHADER INDEX: " + shaderSourceStart + " " + shaderSourceEnd);

        if(shaderSourceEnd != -1 && shaderSourceStart != -1){

            String shaderSource = "";
            for(int i = shaderSourceStart + 1; i < shaderSourceEnd; i++){
                shaderSource = shaderSource + rawSource.get(i) + "\n";
            }

            int shader = -1;

            switch(type){
                case "VERTEX":
                    shader = ShaderBuilder.newShaderFromString(shaderSource, GL_VERTEX_SHADER);
                    break;
                case "GEOMETRY":
                    shader = ShaderBuilder.newShaderFromString(shaderSource, GL_GEOMETRY_SHADER);
                    break;
                case "FRAGMENT":
                    shader = ShaderBuilder.newShaderFromString(shaderSource, GL_FRAGMENT_SHADER);
                    break;
            }
            return shader;
        }
        return -1;

    }

}
