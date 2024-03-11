package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;

public class Leaf extends GameObject {
    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    private static final String LEAF_TAG = "leaf";


    public Leaf(Vector2 topLeftCorner, Vector2 dimensions) {
        super(topLeftCorner, dimensions, new RectangleRenderable(ColorSupplier.approximateColor(LEAF_COLOR)));
        this.setTag(LEAF_TAG);
    }

}
