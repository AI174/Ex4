package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;


import java.awt.*;

public class SunHalo {

    private static final String SUN_HALO_TAG = "sunHalo";
    private static final Color HALO_COLOR = new Color(255, 255, 0, 20);
    private static final float SUN_HALO_FACTOR = 1.5f;

    public static GameObject create(GameObject sun){
        GameObject sunHalo = new GameObject(sun.getTopLeftCorner(),sun.getDimensions().mult(SUN_HALO_FACTOR),
                new OvalRenderable(HALO_COLOR));
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag(SUN_HALO_TAG);
        sunHalo.addComponent(deltaTime -> sunHalo.setCenter(sun.getCenter()));
        return sunHalo;
    }
}
