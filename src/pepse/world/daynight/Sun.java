package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.world.Terrain;

import java.awt.*;

public class Sun {

    private static final String SUN_TAG = "sun";
    private static final int SUN_RADIUS = 70;
    private static final float START_SUN_ANGLE = 0f;
    private static final float END_SUN_ANGLE = 360f;

    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        // I'm not sure about the y cords :)
        GameObject sun = new GameObject(Vector2.ZERO,new Vector2(SUN_RADIUS,SUN_RADIUS),
                new OvalRenderable(Color.YELLOW));
        Vector2 initialSunCenter = new Vector2(windowDimensions.x()/2,
                windowDimensions.y()*Terrain.START_HEIGHT_RATIO*0.5f);
        sun.setCenter(initialSunCenter);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_TAG);
        Vector2 cycleCenter = new Vector2(windowDimensions.x()/2,
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
