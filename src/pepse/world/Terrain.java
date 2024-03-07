package pepse.world;

import danogl.util.Vector2;
import pepse.util.NoiseGenerator;

import java.util.ArrayList;
import java.util.List;

public class Terrain {
    public static final float HEIGHT_RATIO_FOR_X_0 = (float)2 / 3;
    private final int groundHeightAtX0;
    private final NoiseGenerator noiseGenerator;
    public Terrain(Vector2 windowDimensions, int seed) {
        this.groundHeightAtX0 = (int)(windowDimensions.y() * HEIGHT_RATIO_FOR_X_0);
        this.noiseGenerator = new NoiseGenerator(seed,groundHeightAtX0);
    }

    public float groundHeightAt(float x){
        float noise = (float) noiseGenerator.noise(x, Block.SIZE *7);
        return groundHeightAtX0 + noise;
    }

    public List<Block> createInRange(int minX,int maxX){
        List<Block> groundBlocks = new ArrayList<>();
        return groundBlocks;
    }
}
