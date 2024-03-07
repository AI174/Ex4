package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Terrain {
    public static final float HEIGHT_RATIO_FOR_X_0 = (float)2 / 3;
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    public static final String GROUND_TAG = "ground";
    private final int groundHeightAtX0;
    private final NoiseGenerator noiseGenerator;

    public Terrain(Vector2 windowDimensions, int seed) {
        this.groundHeightAtX0 = (int)(windowDimensions.y() * HEIGHT_RATIO_FOR_X_0);
        this.noiseGenerator = new NoiseGenerator(seed,groundHeightAtX0);
    }

    public float groundHeightAt(float x){
        float noise = (float) noiseGenerator.noise(x, Block.SIZE * 7);
        return groundHeightAtX0 + noise;
    }

    public List<Block> createInRange(int minX,int maxX){
        // not sure if I should add space between them
        List<Block> groundBlocks = new ArrayList<>();
        minX = (int) Math.floor((double) minX / Block.SIZE) * Block.SIZE;
        maxX = (int) Math.floor((double) maxX / Block.SIZE) * Block.SIZE;
        for (int currX = minX; currX <= maxX; currX += Block.SIZE) {
            int currY = (int) Math.floor( groundHeightAt(currX) / Block.SIZE) * Block.SIZE;
            // this is how they want it to be
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

    // the inner for I made at first is:
    //    for (int i = currY; i < windowDimensions.y(); i+= Block.SIZE) {
    //        Block block = new Block(new Vector2(currX,i),new RectangleRenderable(ColorSupplier.
    //                approximateColor(BASE_GROUND_COLOR)));
    //        block.setTag(GROUND_TAG);
    //        groundBlocks.add(block);
    //    }
}
