package engine;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {
    public Transform(Vector3f position, Quaternionf rotation, Vector3f scale){
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }
    public Transform(){
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Quaternionf(0, 0, 0);
        this.scale = new Vector3f(1f, 1f, 1f);
    }




    private Vector3f position;
    private Quaternionf rotation;
    private Vector3f scale;


    public Matrix4f GetModel(){
        Matrix4f target = new Matrix4f();
        target.translate(position);
        target.rotate((float)Math.toRadians(rotation.x), new Vector3f(1,0,0));
        target.rotate((float)Math.toRadians(rotation.y), new Vector3f(0,1,0));
        target.rotate((float)Math.toRadians(rotation.z), new Vector3f(0,0,1));
        target.scale(scale);
        return target;
    }

    public Matrix4f GetModelInverted(){
        Matrix4f target = new Matrix4f();
        target.rotate((float)Math.toRadians(rotation.x), new Vector3f(1,0,0));
        target.rotate((float)Math.toRadians(rotation.y), new Vector3f(0,1,0));
        target.rotate((float)Math.toRadians(rotation.z), new Vector3f(0,0,1));
        target.translate(position);
        target.scale(scale);
        return target;
    }




    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPosition(float x, float y, float z){
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void translate(Vector3f  vector){
        this.position.x += vector.x;
        this.position.y += vector.y;
        this.position.z += vector.z;
    }

    public void translate(float x, float y, float z){
        this.position.x += x;
        this.position.y += y;
        this.position.z += z;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation = rotation;
    }

    public void rotate(float x, float y, float z) {
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void setScale(float x, float y, float z){
        this.scale.x = x;
        this.scale.y = y;
        this.scale.z = z;
    }
    public void rescale(float x, float y, float z){
        this.scale.x += x;
        this.scale.y += y;
        this.scale.z += z;
    }
}
