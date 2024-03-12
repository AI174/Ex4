package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import java.awt.Color;
/**
 * Represents the sky in the game world.
 * @author adan.ir1, hayanat2002
 * @see GameObject
 */
public class Sky {
    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");
    private static final String SKY_TAG = "sky";
    /**
     * Creates a sky GameObject with the specified window dimensions.
     * @param windowDimensions The dimensions of the game window.
     * @return The GameObject representing the sky.
     */
    public static GameObject create(Vector2 windowDimensions){
        GameObject sky = new GameObject(Vector2.ZERO,windowDimensions,
                new RectangleRenderable(BASIC_SKY_COLOR));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag(SKY_TAG);
        return sky;
    }
}
