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

    private static final  Color LEAF_COLOR = new Color(50, 200, 30);
    private static final int TRUNK_HEIGHT_UPPER_BOUND = 180;
    private static final int TRUNK_HEIGHT_LOWER_BOUND = 100;
    private final Random random =new Random();
    private final Function<Float, Float> groundHeightAt;
//    private final GameObjectCollection gameObjects;
    private final Vector2 windowDimensions;
//    private final int seed;


    public Flora(Function<Float, Float> groundHeightAt,Vector2 windowDimensions) {

        this.groundHeightAt = groundHeightAt;
//        this.gameObjects = gameObjects;
        this.windowDimensions = windowDimensions;
    }

//    public

    public ArrayList<ArrayList<GameObject>> createInRange(int minX, int maxX){
        ArrayList<GameObject> trunks = new ArrayList<>();
        ArrayList<GameObject> leafs = new ArrayList<>();
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
                trunks.add(buildTree((float) i,trunkHeight));
                leafs.addAll(buildTreeLeafs(i,trunkHeight)); // add all the leafs for the curr tree
            }
        }
        ArrayList<ArrayList<GameObject>> trees = new ArrayList<>();
        trees.add(trunks);
        trees.add(leafs);
        return trees;
    }

    private Trunk buildTree(float x, int trunkHeight){
        Trunk trunk = new Trunk(new Vector2(x, groundHeightAt.apply(x)),trunkHeight);
        trunk.setTopLeftCorner(new Vector2(x, groundHeightAt.apply(x) - trunkHeight));
        return trunk;
    }

    private List<GameObject> buildTreeLeafs(int x, int trunkHeight) {
        ArrayList<GameObject> leafs= new ArrayList<>();

        return leafs;
    }




}