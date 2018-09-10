
import engine.*;
import engine.Renderer;
import engine.primitive_shapes.Circle;
import engine.primitive_shapes.PrimitiveShaders;
import org.joml.*;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.lang.Math;
import java.nio.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class HelloWorld {

    // The window handle
    private long window;



    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {

        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(DisplayManager.DISPLAY_WIDTH, DisplayManager.DISPLAY_HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {

        GL.createCapabilities();

        int vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);

        Vertex triangle[] =  {
            new Vertex(new Vector3f(-0.5f, -0.5f, -1), new Vector2f(0, 1), new Vector3f(0, 0, -1), new Vector3f(1.0f,0.0f,1.0f)),
            new Vertex(new Vector3f(-0.5f, 0.5f,-1), new Vector2f(0, 0), new Vector3f(0, 0, -1), new Vector3f(1.0f,0.0f,1.0f)),
            new Vertex(new Vector3f(0.5f, 0.5f, -1), new Vector2f(1, 0), new Vector3f(0, 0, -1), new Vector3f(1.0f,0.0f,1.0f)),
            new Vertex(new Vector3f(0.5f, -0.5f, -1), new Vector2f(1, 1), new Vector3f(0, 0, -1), new Vector3f(1.0f,0.0f,1.0f)),
	    };

        Vector3f material[] = {
                new Vector3f(1,1,1),
                new Vector3f(1,1,1),
                new Vector3f(1,1,1),
                new Vector3f(0.5f,0.5f,1),
        };

        //Utils.linkMaterials(triangle, material);

        int indices[] = {0, 1, 2,
                0, 2, 3,
        };

        ShaderBuilder shaderBuilder = new ShaderBuilder();
        int shader = shaderBuilder.newShaderProgram("src/shaders/test_shaders/vertex_shader.vs", "src/shaders/test_shaders/fragment_shader.fs");
        int terrainShader = shaderBuilder.newShaderProgram (
            "src/shaders/low_poly/low_poly_vertex.vs",
           "src/shaders/low_poly/low_poly_geometry.gs",
            "src/shaders/low_poly/low_poly_fragment.fs"
        );

        OneFileShaderBuilder ofs = new OneFileShaderBuilder("src/shaders/shaders.txt");
        ofs.LoadRawSource();
        int shader33 = ofs.CreateShaderProgram("TERRAIN SHADER");

        IndexedModel model = OBJLoader.RawModel("res/models/stall.obj");
        Texture texture = new Texture("stallTexture.png");

        PrimitiveShaders primitiveShaders = new PrimitiveShaders("src/shaders/primitiveShaders.txt");
        Terrain terrain = new Terrain(new Transform(new Vector3f(0,0,0), new Quaternionf(0,0,0), new Vector3f(1f,1f,1f)),20,20,1);
        terrain.shader = terrainShader;

        Terrain terrain2 = new Terrain(new Transform(new Vector3f(0,5,-20), new Quaternionf(0,0,0), new Vector3f(1f,1f,1f)),20,20,1);
        terrain.shader = terrainShader;

        GameObject stall = new GameObject(new Transform(new Vector3f(10,0,-10), new Quaternionf(0,0,0), new Vector3f(0.1f,0.1f,0.1f)));
        stall.texture = texture;
        stall.setMesh(new Mesh(model.vertices, model.vertices.length, model.indices, model.indices.length, false, GL_TRIANGLES));
        stall.setShader(shader);

        GameObject stall2 = new GameObject(new Transform(new Vector3f(10,0,10), new Quaternionf(0,0,0), new Vector3f(0.1f,0.1f,0.1f)));
        stall2.texture = texture;
        stall2.setMesh(new Mesh(model.vertices, model.vertices.length, model.indices, model.indices.length, false, GL_TRIANGLES));
        stall2.setShader(shader);

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);


        Camera camera = new Camera(window);

        long frameCounter = 0;

        float wave = 0;

        Circle circle = new Circle(new Vector2f(1,1), 20, PrimitiveShapes.Type.EDGES, primitiveShaders);


        while ( !glfwWindowShouldClose(window) ) {




            glEnable(GL_DEPTH_TEST);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer


            ShaderBuilder.setMatrix4f(shader, "viewMatrix", camera.GetCameraModel());
            camera.Update();

            stall.getTransform().rotate(0,0,0);
            stall.Update();
            stall2.Update();
            terrain.Update();
            //terrain2.Update();
            ShaderBuilder.setMatrix4f(circle.shader, "viewMatrix", camera.GetCameraModel());
            //circle.Update();
            circle.transform.rotate(0,0,0);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new HelloWorld().run();
    }

}