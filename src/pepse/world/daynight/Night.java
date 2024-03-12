package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import java.awt.Color;
/**
 * Represents the night cycle in the game world.
 * @author adan.ir1, hayanat2002
 * @see GameObject
 * @see Transition
 */
public class Night {

    private static final String NIGHT_TAG = "night";
    private static final float MIDNIGHT_OPACITY = 0.5f;
    private static final float MIDDAY_OPACITY = 0f;
    private static final int TRANSFER_FACTOR = 2;
    /**
     * Creates a night GameObject with the specified window dimensions and cycle length.
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength The length of the day-night cycle.
     * @return The GameObject representing the night.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        GameObject night = new GameObject(Vector2.ZERO,windowDimensions,new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(NIGHT_TAG);
        new Transition<>(night,night.renderer()::setOpaqueness, MIDDAY_OPACITY, MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,cycleLength/ TRANSFER_FACTOR,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,null);
        return night;
    }
}
