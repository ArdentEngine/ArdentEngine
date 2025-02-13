package io.github.ardentengine.core.resources;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.ByteBuffer;

public class TextureLoader implements ResourceLoader {

    // TODO: Create an alternative texture loader that uses assimp and check which one is faster

    @Override
    public Object loadResource(String resourcePath) {
        try(var inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)) {
            if(inputStream != null) {
                var image = ImageIO.read(inputStream);
                var buffer = ByteBuffer.allocateDirect(4 * image.getWidth() * image.getHeight());
                for(var pixel : image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth())) {
                    buffer.put((byte) ((pixel >> 16) & 0xff));
                    buffer.put((byte) ((pixel >> 8) & 0xff));
                    buffer.put((byte) (pixel & 0xff));
                    buffer.put((byte) ((pixel >> 24) & 0xff));
                }
                var texture = new ImageTexture();
                texture.setImage(buffer.flip(), image.getWidth(), image.getHeight());
                // TODO: Load texture properties
                return texture;
            }
            System.err.println("Could not find image file " + resourcePath);
        } catch (IOException e) {
            System.err.println("Exception occurred while loading image " + resourcePath);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String[] supportedExtensions() {
        // TODO: Test other types
        return new String[] {".png"};
    }
}
