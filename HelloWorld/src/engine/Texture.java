package engine;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class Texture {

    public ByteBuffer resource;

    public int textureID;

    public int width, height;
    BufferedImage bufferedImage;
    public Texture (String filename){
        resource = loadTexture(filename);
        textureID = glGenTextures();
        initTexture(textureID, resource);
    }

    public void bindTexture(boolean enable){
        if(enable)
            glBindTexture(GL_TEXTURE_2D, textureID);
        else
            glBindTexture(GL_TEXTURE_2D, 0);
    }

    public static void setTextures(boolean enabled){
        if(enabled)
            glEnable(GL_TEXTURE_2D);
        else
            glDisable(GL_TEXTURE_2D);
    }


    public void initTexture(int id, ByteBuffer pixels){
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, bufferedImage.getWidth(), bufferedImage.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
    }


    public ByteBuffer loadTexture(String filename) {
        try {

            bufferedImage = ImageIO.read(new File("./res/textures/" + filename));
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();

            int[] pixels_raw = new int[width * height * 4];
            pixels_raw = bufferedImage.getRGB(0, 0, width, height, null, 0, width);

            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int pixel = pixels_raw[i * width + j];
                    pixels.put((byte) ((pixel >> 16) & 0xFF)); // RED
                    pixels.put((byte) ((pixel >> 8) & 0xFF));  // GREEN
                    pixels.put((byte) (pixel & 0xFF));          // BLUE
                    pixels.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
                }
            }

            pixels.flip();
            return pixels;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
