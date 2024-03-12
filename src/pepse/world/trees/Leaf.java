package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.AvatarObserver;
import java.awt.Color;
import java.util.Random;

/**
 * Represents a leaf object in the game world.
 * @author adan.ir1, hayanat2002
 * @see GameObject
 * @see AvatarObserver
 */
public class Leaf extends GameObject implements AvatarObserver {
    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    private static final String LEAF_TAG = "leaf";
    private static final float ANGLE = 15f;
    private static final float MIN_SIZE_PORTION = .9f;
    private static final float MAX_SIZE_PORTION = 1.1f;
    private static final int TRANSITION_TIME = 2;
    private static final int RANDOM_WAITING_TIME = 30;
    private static final float RANDOM_WAITING_TIME_FACTOR = .1f;
    private static final float TRANSITION_START = 90f;
    private static final float TRANSITION_END = 0f;

    /**
     * Constructs a Leaf object with the given position and dimensions.
     * @param topLeftCorner The top-left corner position of the leaf.
     * @param dimensions    The dimensions (width and height) of the leaf.
     */
    public Leaf(Vector2 topLeftCorner, Vector2 dimensions) {
        super(topLeftCorner, dimensions,new RectangleRenderable(ColorSupplier.approximateColor(LEAF_COLOR)));
        this.setTag(LEAF_TAG);
        float waitingTime = new Random().nextInt(RANDOM_WAITING_TIME)* RANDOM_WAITING_TIME_FACTOR;
        new ScheduledTask(this, waitingTime, false, this::createTransition);
    }
    /**
     * Responds to avatar jumps by changing the leaf's angle.
     */
    @Override
    public void updateWhenAvatarJumps() {
        new Transition<>(this, this.renderer()::setRenderableAngle,
                TRANSITION_START, TRANSITION_END, Transition.LINEAR_INTERPOLATOR_FLOAT,
                TRANSITION_TIME, Transition.TransitionType.TRANSITION_ONCE, null);
    }

    private void createTransition() {
        new Transition<>(this, this.renderer()::setRenderableAngle,
                ANGLE, -ANGLE, Transition.LINEAR_INTERPOLATOR_FLOAT,
                TRANSITION_TIME, Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);

        new Transition<>(this, this::setDimensions,
                getDimensions().mult(MIN_SIZE_PORTION), getDimensions().mult(MAX_SIZE_PORTION),
                Transition.LINEAR_INTERPOLATOR_VECTOR, TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }
}