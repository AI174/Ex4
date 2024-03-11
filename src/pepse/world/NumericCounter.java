package pepse.world;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.util.function.Supplier;

/**
 * Numeric lives counter.
 * @author adan.ir1, hayanat2002
 */
public class NumericCounter extends GameObject {
    private Supplier<Float> getEnergy;
    private final TextRenderable textRenderable;
    private static final int NUMERIC_COUNTER_SIZE = 50;

    /**
     * the constructor, creates a numeric counter.
     * @param textRenderable the rendering type
     * @param
     */
    public NumericCounter(TextRenderable textRenderable, Supplier<Float> getEnergy) {

        super(Vector2.ZERO, new Vector2(NUMERIC_COUNTER_SIZE,NUMERIC_COUNTER_SIZE), textRenderable);
        this.textRenderable = textRenderable;
        this.getEnergy = getEnergy;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float energy = getEnergy.get();
        textRenderable.setString(Float.toString(energy));
    }

}