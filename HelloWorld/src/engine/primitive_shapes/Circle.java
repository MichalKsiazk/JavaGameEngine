package engine.primitive_shapes;

import engine.PrimitiveShapes;
import engine.Renderer;
import engine.ShaderBuilder;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

public class Circle extends PrimitiveShapes{

    public static final String primitiveType = "CIRCLE";

    int accuracy;
    Vector2f radius;
    Type type;


    public Circle(Vector2f radius, int accuracy, Type type, PrimitiveShaders ps) {

        this.accuracy = accuracy;
        this.radius = radius;
        this.type = type;

        this.ps = ps;

        this.shader = ps.circleShader;

        ShaderBuilder.setVector2f(shader, "radius", this.radius);

        super.Init(primitiveType);
    }


    public void Update() {
        ShaderBuilder.setInt(shader, "accuracy", accuracy);
        super.Update();
        ps.DrawVB();
    }

}
