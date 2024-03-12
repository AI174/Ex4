package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
/**
 * Represents a block in the game world.
 * @author adan.ir1, hayanat2002
 * @see danogl.GameObject
 */
public class Block extends GameObject {
    /**
     * The size of the block.
     */
    public static final int SIZE = 30;
    private static final String BLOCK_TAG = "block";
    /**
     * Constructs a block at the specified position with the given renderable appearance.
     * @param topLeftCorner The top-left corner position of the block.
     * @param renderable The renderable appearance of the block.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        this.setTag(BLOCK_TAG);
    }
}
