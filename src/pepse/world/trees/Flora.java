package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Flora{
    private static final  Color TUNK_COLOR = new Color(100, 50, 20);
    private static final  Color LEAF_COLOR = new Color(50, 200, 30);
    //    private Random random =new Random();
    private final Function<Float, Float> groundHeightAt;
    private final GameObjectCollection gameObjects;
    private final Vector2 windowDimensions;
    private final int seed;


    /**
     * Construct a new GameObject instance.
     *
     * @param groundHeightAt
     */
    public Flora(Function<Float, Float> groundHeightAt, GameObjectCollection gameObjects,
                 Vector2 windowDimensions,int seed) {

        this.groundHeightAt = groundHeightAt;
        this.gameObjects = gameObjects;
        this.windowDimensions = windowDimensions;
        this.seed = seed;
//        createInRange(0, (int) windowDimensions.x());
        buildTree(10);
    }

//    public

    public List<Object> createInRange(int minX, int maxX){
        Random random =new Random();
        minX = (int) Math.floor((double) minX / Block.SIZE) * Block.SIZE;
        maxX = (int) Math.floor((double) maxX / Block.SIZE) * Block.SIZE;


        for (int i = minX; i <= maxX; i+= Block.SIZE) {
            float randomFloat = random.nextFloat();
            if(randomFloat > 0.9f){
                this.buildTree((float) i);
            }
        }

        return new ArrayList<>();
    }


    private void buildTree(float x){
        System.out.println(groundHeightAt.apply(x));
        Trunk trunk = new Trunk(new Vector2(x, groundHeightAt.apply(x)));
        trunk.setTopLeftCorner(new Vector2(x, groundHeightAt.apply(x) - 120 ));
        gameObjects.addGameObject(trunk);

    }


}