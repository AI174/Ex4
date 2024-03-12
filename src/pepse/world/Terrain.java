package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents the terrain in the game world.
 * @author adan.ir1, hayanat2002
 * @see Block
 * @see NoiseGenerator
 * @see ColorSupplier
 */
public class Terrain {
    /**
     * The ratio of the starting height of the terrain.
     */
    public static final float START_HEIGHT_RATIO = (float)2 / 3;
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    private static final String GROUND_TAG = "ground";
    private static final int NOISE_FACTOR = 7;
    private final int groundHeightAtX0;
    private final NoiseGenerator noiseGenerator;

    /**
     * Constructs a terrain with the given window dimensions and seed for noise generation.
     * @param windowDimensions The dimensions of the game window.
     * @param seed The seed for noise generation.
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        this.groundHeightAtX0 = (int)(windowDimensions.y() * START_HEIGHT_RATIO);
        this.noiseGenerator = new NoiseGenerator(seed,groundHeightAtX0);
    }

    /**
     * Calculates the height of the ground at the specified x.
     * @param x The x coordinate.
     * @return The height of the ground at the specified x.
     */
    public float groundHeightAt(float x){
        float noise = (float) noiseGenerator.noise(x, Block.SIZE * NOISE_FACTOR);
        return groundHeightAtX0 + noise;
    }
    /**
     * Creates ground blocks within the specified range.
     * @param minX The minimum x.
     * @param maxX The maximum x.
     * @return A list of ground blocks created within the specified range.
     */
    public List<Block> createInRange(int minX,int maxX){
        List<Block> groundBlocks = new ArrayList<>();
        minX = (int) Math.floor((double) minX / Block.SIZE) * Block.SIZE;
        maxX = (int) Math.floor((double) maxX / Block.SIZE) * Block.SIZE;
        for (int currX = minX; currX <= maxX; currX += Block.SIZE) {
            int currY = (int) Math.floor( groundHeightAt(currX) / Block.SIZE) * Block.SIZE;
            for (int i = 0; i < TERRAIN_DEPTH; i++) {
                Block block = new Block(new Vector2(currX,currY),new RectangleRenderable(ColorSupplier.
                        approximateColor(BASE_GROUND_COLOR)));
                block.setTag(GROUND_TAG);
                groundBlocks.add(block);
                currY += Block.SIZE;
            }
        }
        return groundBlocks;
    }
}
