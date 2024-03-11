package pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;
import pepse.world.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class Flora{


    private static final int TRUNK_HEIGHT_UPPER_BOUND = 180;
    private static final int TRUNK_HEIGHT_LOWER_BOUND = 100;
    private final Random random =new Random();
    private final Function<Float, Float> groundHeightAt;
    private final Vector2 windowDimensions;
//    private final int seed;


    public Flora(Function<Float, Float> groundHeightAt,Vector2 windowDimensions) {

        this.groundHeightAt = groundHeightAt;
        this.windowDimensions = windowDimensions;
    }

//    public

    public ArrayList<GameObject> createInRange(int minX, int maxX){
        ArrayList<GameObject> trees = new ArrayList<>();
        minX = (int) Math.floor((double) minX / Block.SIZE) * Block.SIZE;
        maxX = (int) Math.floor((double) maxX / Block.SIZE) * Block.SIZE;

        for (int i = minX; i <= maxX; i+= Block.SIZE) {

            float randomFloat = random.nextFloat();
            // they want the trees in different heights
            int trunkHeight = random.nextInt(TRUNK_HEIGHT_UPPER_BOUND - TRUNK_HEIGHT_LOWER_BOUND) +
                    TRUNK_HEIGHT_LOWER_BOUND;
            // we want it to be rounded to the proper height
            trunkHeight = (int)Math.floor((float)trunkHeight/ Block.SIZE) * Block.SIZE;

            if(randomFloat > 0.9f){
                trees.add(buildTree((float) i,trunkHeight));
                trees.addAll(buildTreeLeafs(i,trunkHeight)); // add all the leafs for the curr tree
            }
        }
        return trees;
    }

    private Trunk buildTree(float x, int trunkHeight){
        Trunk trunk = new Trunk(new Vector2(x, groundHeightAt.apply(x)),trunkHeight);
        trunk.setTopLeftCorner(new Vector2(x, groundHeightAt.apply(x) - trunkHeight));
        return trunk;
    }

    private List<GameObject> buildTreeLeafs(int x, int trunkHeight) {
        ArrayList<GameObject> leafs= new ArrayList<>();
        for (int i = x-90; i <=x+90 ; i+=30) {
            for (int j = trunkHeight- 120;j<= trunkHeight + 120;j+=30){
                float randomFloat = random.nextFloat();
                if(randomFloat > 0.9f){
                    Leaf leaf = new Leaf(new Vector2(i,j),Vector2.ONES.mult(30f));
                    leafs.add(leaf);
                }
            }


        }
        return leafs;
    }




}