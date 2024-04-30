package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.world.Avatar;
import pepse.world.AvatarObserver;
import pepse.world.Block;
import java.awt.Color;
import java.util.function.Consumer;

/**
 * Represents a fruit object in the game world.
 * @author adan.ir1, hayanat2002
 * @see danogl.GameObject
 * @see Avatar
 * @see AvatarObserver
 * @see Block
 */
public class Fruit extends GameObject implements AvatarObserver {
    private static final Color DEFAULT_FRUIT_COLOR = Color.red;
    private static final Color SECOND_FRUIT_COLOR = Color.yellow;
    private static final int FRUIT_SIZE = Block.SIZE;
    private static final int GAME_CYCLE = 30;
    private static final float ADDED_ENERGY = 10f;
    private static final String Fruit_TAG = "fruit";
    private final Consumer<Float> avatarAddEnergy;
    private Color currFruitColor;

    /**
     * Constructs a fruit object with the specified position.
     * @param topLeftCorner The position of the fruit, in window coordinates (pixels).
     */
    public Fruit(Vector2 topLeftCorner, Consumer<Float> avatarAddEnergy) {
        super(topLeftCorner, Vector2.ONES.mult(FRUIT_SIZE), new OvalRenderable(DEFAULT_FRUIT_COLOR));
        this.avatarAddEnergy = avatarAddEnergy;
        this.setTag(Fruit_TAG);
        currFruitColor = DEFAULT_FRUIT_COLOR;
    }

    /**
     * Handles the event when a collision occurs with another GameObject.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.getTag().equals("avatar") && renderer().getRenderable() != null){
            disappear();
            avatarAddEnergy.accept(ADDED_ENERGY);
        }
    }

    /**
     * Updates the fruit's color when the avatar jumps.
     */
    @Override
    public void updateWhenAvatarJumps() {
        swapFruitColor();
    }

    private void disappear(){
        this.renderer().setRenderable(null);
        new ScheduledTask(this, GAME_CYCLE, false,
                ()-> renderer().setRenderable(new OvalRenderable(currFruitColor)));
    }

    private void swapFruitColor(){
        if(currFruitColor.equals(DEFAULT_FRUIT_COLOR)){
            currFruitColor = SECOND_FRUIT_COLOR;
        }else {
            currFruitColor = DEFAULT_FRUIT_COLOR;
        }
        if(renderer().getRenderable() != null) {
            this.renderer().setRenderable(new OvalRenderable(currFruitColor));
        }
    }
}
