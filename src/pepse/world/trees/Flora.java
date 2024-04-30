package pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;
import pepse.world.AvatarObserver;
import pepse.world.Block;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents the flora in the game world, including trees, leaves, and fruits.
 * @author adan.ir1, hayanat2002
 * @see Trunk
 * @see Leaf
 * @see Fruit
 */
public class Flora{
    private static final int TRUNK_HEIGHT_UPPER_BOUND = 180;
    private static final int TRUNK_HEIGHT_LOWER_BOUND = 100;
    private static final int LEAF_SIZE = Block.SIZE;
    private static final int LEAVES_RANGE_ROW = 2;
    private static final int LEAVES_RANGE_COL = 1;
    private static final float ADD_TREE_BOUND = .9f;
    private static final float ADD_LEAF_BOUND = .2f;
    private static final float ADD_FRUIT_BOUND = .9f;
    private static final int AVATAR_X_LOCATION = 0;
    private final Random random =new Random();
    private final Function<Float, Float> groundHeightAt;
    private final Consumer<Float> avatarAddEnergy;
    private final Consumer<AvatarObserver> avatarRegisterObserver;

    /**
     * Constructs a Flora object with the specified ground height function.
     * @param groundHeightAt A function that returns the ground height at a given x.
     */
    public Flora(Function<Float, Float> groundHeightAt, Consumer<Float> avatarAddEnergy,
                 Consumer<AvatarObserver> avatarRegisterObserver) {
        this.groundHeightAt = groundHeightAt;
        this.avatarAddEnergy = avatarAddEnergy;
        this.avatarRegisterObserver = avatarRegisterObserver;
    }

    /**
     * Creates flora objects within the specified range.
     * Trees, leaves, and fruits are generated based on random factors.
     * @param minX The minimum x.
     * @param maxX The maximum x.
     * @return A list of GameObjects representing the flora objects created.
     */
    public ArrayList<GameObject> createInRange(int minX, int maxX){
        ArrayList<GameObject> trees = new ArrayList<>();
        minX = (int) Math.floor((double) minX / Block.SIZE) * Block.SIZE;
        maxX = (int) Math.floor((double) maxX / Block.SIZE) * Block.SIZE;
        for (int i = minX; i <= maxX; i+= Block.SIZE) {
            if(i == AVATAR_X_LOCATION){ // because the avatar is there
                continue;
            }
            float randomFloat = random.nextFloat();
            int trunkHeight = random.nextInt(TRUNK_HEIGHT_UPPER_BOUND - TRUNK_HEIGHT_LOWER_BOUND) +
                    TRUNK_HEIGHT_LOWER_BOUND;
            trunkHeight = (int)Math.floor((float)trunkHeight/ Block.SIZE) * Block.SIZE;

            if(randomFloat > ADD_TREE_BOUND){
                trees.add(buildTrunk((float) i,trunkHeight));
                trees.addAll(buildTreeUpperComponents(i,trunkHeight));
            }
        }
        return trees;
    }

    private Trunk buildTrunk(float x, int trunkHeight){
        Trunk trunk = new Trunk(new Vector2(x, groundHeightAt.apply(x)),trunkHeight);
        trunk.setTopLeftCorner(new Vector2(x, groundHeightAt.apply(x) - trunkHeight));
        avatarRegisterObserver.accept(trunk);
        return trunk;
    }

    private List<GameObject> buildTreeUpperComponents(int x, int trunkHeight) {
        ArrayList<GameObject> treeUpperComponents= new ArrayList<>();
        for (int i = (x-LEAF_SIZE*LEAVES_RANGE_ROW); i <=(x+LEAF_SIZE*LEAVES_RANGE_ROW) ; i+=LEAF_SIZE) {
            for (int j = (trunkHeight- LEAF_SIZE*LEAVES_RANGE_COL);
                 j<= (trunkHeight + LEAF_SIZE*LEAVES_RANGE_COL);j+=LEAF_SIZE){

                float randomFloat = random.nextFloat();
                if(randomFloat > ADD_LEAF_BOUND){
                    Leaf leaf = new Leaf(new Vector2(i, groundHeightAt.apply((float)i) - (j + LEAF_SIZE)),
                            Vector2.ONES.mult(LEAF_SIZE));
                    treeUpperComponents.add(leaf);
                    avatarRegisterObserver.accept(leaf);
                }
                float fruitRandomFloat = random.nextFloat();
                if (fruitRandomFloat > ADD_FRUIT_BOUND){
                    Fruit fruit = new Fruit(new Vector2(i, groundHeightAt.apply((float)i) - (j + LEAF_SIZE)),
                            avatarAddEnergy);
                    treeUpperComponents.add(fruit);
                    avatarRegisterObserver.accept(fruit);
                }
            }
        }
        return treeUpperComponents;
    }
}