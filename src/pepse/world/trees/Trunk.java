package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;

public class Trunk extends GameObject {

    private static final Color TRUNK_COLOR = new Color(100, 50, 20);
    private static final String TRUNK_TAG = "Trunk";
    private static final int TRUNK_HEIGHT = 120;
    private static final int TRUNK_WIDTH = 10; // DON'T CHANGE

    /**
     * Construct a new GameObject instance.
     *
     * @param DownLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     */
    public Trunk(Vector2 DownLeftCorner) {
        super(DownLeftCorner.add(new Vector2(0, -TRUNK_HEIGHT)), new Vector2(TRUNK_WIDTH, TRUNK_HEIGHT),
                new RectangleRenderable(ColorSupplier.approximateColor(TRUNK_COLOR)));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        this.setTag(TRUNK_TAG);
    }

}