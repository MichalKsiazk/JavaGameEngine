package engine;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallback;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class Camera {

    private long window;


    private Vector3f position = new Vector3f(0, 0, 0);

    private Vector3f forward = new Vector3f(0,0,1);
    private Vector3f up = new Vector3f(0,1,0);
    private Vector3f left = new Vector3f(0,0,1);

    public float speed;
    Quaternionf viewDirection = new Quaternionf(0,0,0f);

    Vector3f oldMousePosition = new Vector3f(0,0,0);
    Vector3f deltaMousePosition;

    Matrix4f step;


    public Camera(long window) {
        speed = 0.01f;
        this.window = window;
        oldMousePosition = GetMousePos();

        glfwSetScrollCallback(window, new GLFWScrollCallback() {
            @Override public void invoke (long win, double dx, double dy) {
                System.out.println(dy);
                position.y += dy * speed;
            }
        });


    }

    public void Update() {
            
        calculateDeltaMousePosition();
        move();
    }




    public Matrix4f GetCameraModel(){


        Matrix4f target = new Matrix4f().identity();
        target.rotate((float)Math.toRadians(viewDirection.x), new Vector3f(0,1,0));
        target.rotate((float)Math.toRadians(-viewDirection.y), new Vector3f(1,0,0));
        target.rotate((float)Math.toRadians(-viewDirection.z), new Vector3f(0,0,1));
        target.translate(new Vector3f(-position.x, -position.y, -position.z));
        target.scale(1,1,1);
        return target;
    }


    public Vector3f getPosition() {
        return position;
    }

    void calculateDeltaMousePosition() {

        Vector3f newMousePos = GetMousePos();

        deltaMousePosition = new Vector3f(newMousePos.x - oldMousePosition.x, newMousePos.y - oldMousePosition.y, 0);
        //viewDirection = new Quaternionf(viewDirection.x + deltaMousePosition.x, viewDirection.y + deltaMousePosition.y, 0);
        //System.out.println(viewDirection.x + " " + viewDirection.y);

        oldMousePosition = newMousePos;
    }

    private void move(){


        boolean shiftPressed = glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GL_TRUE;

        viewDirection.x += deltaMousePosition.x;
        viewDirection.y += deltaMousePosition.y;


        speed = shiftPressed ? speed * 3 : speed;

        if(glfwGetKey(window, GLFW_KEY_W) == GL_TRUE) {
            position.z -= speed;
        }
        if(glfwGetKey(window, GLFW_KEY_S) == GL_TRUE) {
            position.z += speed;
        }

        //System.out.println(position.x + " " + position.y + " " + position.z);

        if(glfwGetKey(window, GLFW_KEY_A) == GL_TRUE)
            position.x -= speed;
        if(glfwGetKey(window, GLFW_KEY_D) == GL_TRUE)
            position.x += speed;

        speed = shiftPressed ? speed / 3 : speed;
    }

    Vector3f GetMousePos(){
        DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, xBuffer, yBuffer);
        double x = xBuffer.get(0);
        double y = yBuffer.get(0);
        return new Vector3f((float)x, -(float)y, 0);
    }
}
