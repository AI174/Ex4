package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.world.Terrain;
import java.awt.Color;
/**
 * Represents the sun in the day-night cycle of the game world.
 * @author adan.ir1, hayanat2002
 * @see GameObject
 * @see Transition
 * @see Terrain
 */
public class Sun {

    private static final String SUN_TAG = "sun";
    private static final int SUN_RADIUS = 70;
    private static final float START_SUN_ANGLE = 0f;
    private static final float END_SUN_ANGLE = 360f;
    private static final float CENTER = 0.5f;
    /**
     * Creates a sun GameObject with the specified window dimensions and cycle length.
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength The length of the day-night cycle.
     * @return The GameObject representing the sun.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        GameObject sun = new GameObject(Vector2.ZERO,new Vector2(SUN_RADIUS,SUN_RADIUS),
                new OvalRenderable(Color.YELLOW));
        Vector2 initialSunCenter = new Vector2(windowDimensions.x()* CENTER,
                windowDimensions.y()*Terrain.START_HEIGHT_RATIO*CENTER);
        sun.setCenter(initialSunCenter);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_TAG);
        Vector2 cycleCenter = new Vector2(windowDimensions.x()*CENTER,
                windowDimensions.y()*(Terrain.START_HEIGHT_RATIO));
        new Transition<>(sun,(Float angle) -> sun.setCenter
                (initialSunCenter.subtract(cycleCenter)
                        .rotated(angle)
                        .add(cycleCenter)), START_SUN_ANGLE, END_SUN_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,null);
        return sun;
    }
}
