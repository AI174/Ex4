package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.AvatarObserver;
import pepse.world.Block;
import java.awt.Color;

/**
 * Represents a trunk object in the game world.
 * @author adan.ir1, hayanat2002
 * @see GameObject
 * @see AvatarObserver
 */

public class Trunk extends GameObject implements AvatarObserver {

    private static final Color TRUNK_COLOR = new Color(100, 50, 20);
    private static final String TRUNK_TAG = "Trunk";
    private static final int TRUNK_WIDTH = Block.SIZE;

    /**
     * Constructs a new Trunk object with the given position and height.
     * @param downLeftCorner The bottom-left corner position of the trunk.
     * @param height The height of the trunk.
     */
    public Trunk(Vector2 downLeftCorner,int height) {
        super(downLeftCorner.add(new Vector2(0, -height)), new Vector2(TRUNK_WIDTH, height),
                new RectangleRenderable(ColorSupplier.approximateColor(TRUNK_COLOR)));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        this.setTag(TRUNK_TAG);
    }

    /**
     * Updates the trunk appearance when the avatar jumps.
     */
    @Override
    public void updateWhenAvatarJumps() {
        this.renderer().setRenderable(new RectangleRenderable(ColorSupplier.approximateColor(TRUNK_COLOR)));
    }
}