package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Night {

    private static final String NIGHT_TAG = "night";
    private static final float MIDNIGHT_OPACITY = 0.5f;
    private static final float MIDDAY_OPACITY = 0f;
    public static final int TRANSFER_FACTOR = 2;

    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        // I'm not sure about cycleLength/2, seems more reasonable to me :
        // cycleLength is the day ,lets say 24 hours, from 12pm till 12 am it should go from 0 to 0.5 , then
        // from 12am to 12 pm it should go from 0.5 to 0 -> transfer time is 12 hours --> cycleLength / 2
        GameObject night = new GameObject(Vector2.ZERO,windowDimensions,new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(NIGHT_TAG);
        new Transition<>(night,night.renderer()::setOpaqueness, MIDDAY_OPACITY, MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,cycleLength/ TRANSFER_FACTOR,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,null);
        return night;
    }
}
