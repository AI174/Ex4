package pepse.world;
import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import java.util.function.Supplier;

/**
 * Represents a numeric counter for displaying a value, such as an energy level.
 * @author adan.ir1, hayanat2002
 * @see GameObject
 * @see Supplier
 */
public class NumericCounter extends GameObject {
    private static final int NUMERIC_COUNTER_SIZE = 50;
    private static final String PERCENT = "%";
    private final Supplier<Float> getEnergy;
    private final TextRenderable textRenderable;

    /**
     * Constructs a numeric counter with the specified TextRenderable and callback function.
     * @param textRenderable The rendering type for displaying the numeric value.
     * @param getEnergy The callback function to retrieve the numeric value.
     */
    public NumericCounter(TextRenderable textRenderable, Supplier<Float> getEnergy) {

        super(Vector2.ZERO, new Vector2(NUMERIC_COUNTER_SIZE,NUMERIC_COUNTER_SIZE), textRenderable);
        this.textRenderable = textRenderable;
        this.getEnergy = getEnergy;
    }

    /**
     * Updates the numeric counter with the latest value retrieved from the callback function.
     * @param deltaTime The time elapsed, in seconds, since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float energy = getEnergy.get();
        textRenderable.setString(energy + PERCENT);
    }

}