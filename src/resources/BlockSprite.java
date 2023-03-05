package resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Holds several resolutions of sprite image for the G4Tetris application.
 * @author Zac Andersen (anderzb@uw.edu)
 * @version 0.1
 */
public class BlockSprite {
    /**
     * Minimum sprite texture size.
     */
    private static final int MIN_TEXTURE_SIZE = 16;

    /**
     * Path to sprite texture.
     */
    private static final String TEXTURE_PATH = "src/resources/Tetris_Square.png";

    /**
     * Memoized sizes of sprite texture.
     */
    private static final Map<Integer, BufferedImage> MEMOIZED = new HashMap<>();

    /**
     * Base sprite texture.
     */
    private static BufferedImage myBaseSprite;

    /** Logger for this class */
    private final Logger myLogger = G4Logging.getLogger(this.getClass());

    /**
     * Image of sprite texture.
     */
    private BufferedImage mySprite;

    /**
     * Creates a new sprite object of desired resolution.
     *
     * @param theTargetSize Desired square pixel size of sprite, minimum 16px.
     * @throws IOException If texture file can not be found.
     */
    public BlockSprite(final int theTargetSize) {
        loadImageFile(TEXTURE_PATH);
        mySprite = myBaseSprite;
        resize(theTargetSize);

    }

    /**
     * Creates a new sprite object at default 16px resolution.
     */
    public BlockSprite() {
        loadImageFile(TEXTURE_PATH);
        mySprite = myBaseSprite;
    }

    /**
     * Gets the pixel square size of the sprite.
     * @return Square Height/Width of sprite.
     */
    public int getSize() {
        return mySprite.getWidth();
    }

    /**
     * Wrapper to load image file and crash if file not found.
     * @param thePath Path to load file from.
     */
    private void loadImageFile(final String thePath) {
        if (myBaseSprite == null) {
            try {
                myBaseSprite = ImageIO.read(new File(thePath));
            } catch (final IOException e) {
                myLogger.severe("Failed to load texture image, crashing");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    /**
     * Resizes sprite on a linear integer-only nearest-neighbor scale.
     *
     * @param theTargetSize The desires square size of the sprite.
     */
    public void resize(final int theTargetSize) {

        if (theTargetSize < MIN_TEXTURE_SIZE) {
            throw new IllegalArgumentException("Minimum sprite texture support is 16px");
        }

        if (MEMOIZED.containsKey(theTargetSize)) {
            mySprite = MEMOIZED.get(theTargetSize);
            return;
        }

        final int scaleFactor = theTargetSize / myBaseSprite.getWidth();

        final int bufferSize = myBaseSprite.getWidth() * scaleFactor;
        final BufferedImage newImage = new BufferedImage(bufferSize, bufferSize,
                myBaseSprite.getType());

        for (int x = 0; x < myBaseSprite.getWidth(); x++) {
            for (int y = 0; y < myBaseSprite.getHeight(); y++) {
                final int pixel = myBaseSprite.getRGB(x, y);
                final int targetX = x * scaleFactor;
                final int targetY = y * scaleFactor;

                for (int offsetX = 0; offsetX < scaleFactor; offsetX++) {
                    for (int offsetY = 0; offsetY < scaleFactor; offsetY++) {
                        newImage.setRGB(targetX + offsetX, targetY + offsetY, pixel);
                    }
                }
            }
        }

        MEMOIZED.put(theTargetSize, newImage);

        mySprite = newImage;
    }


}
