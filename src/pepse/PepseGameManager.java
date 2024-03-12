package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages the game flow and initializes game objects.
 * @author adan.ir1, hayanat2002
 */
public class PepseGameManager extends GameManager {
    private static final int MIN_X = 0;
    private static final float CYCLE_LENGTH = 30f;
    private static final String LEAF_TAG = "leaf";
    private static final String FRUIT_TAG = "fruit";
    private static final int LEAF_LAYER = Layer.DEFAULT - 1; // now it will not collide with the avatar
    private Vector2 windowDimensions;
    private Avatar avatar;
    private Terrain terrain;
    private ImageReader imageReader;
    private UserInputListener inputListener;

    /**
     * Runs the game.
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    /**
     * Initializes the game by creating and configuring game objects.
     * @param imageReader An image reader instance.
     * @param soundReader A sound reader instance.
     * @param inputListener A user input listener instance.
     * @param windowController A window controller instance.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener
            inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowController.getWindowDimensions();
        // making sky
        GameObject sky = Sky.create(windowDimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
        // making ground
        terrain = new Terrain(windowDimensions,(int)new Random().nextGaussian());
        for (Block block: terrain.createInRange(MIN_X,(int)windowDimensions.x())){
            gameObjects().addGameObject(block,Layer.STATIC_OBJECTS);
        }
        createDayNight();
        createAvatar();
        displayEnergy();
        createTrees();

    }
    private void createDayNight() {
        // making night
        GameObject night = Night.create(windowDimensions, CYCLE_LENGTH);
        // appear in front of the main gameplay or background elements
        gameObjects().addGameObject(night, Layer.FOREGROUND);

        // making sun
        GameObject sun = Sun.create(windowDimensions,CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);

        // making sun halo
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);
    }

    private void createAvatar() {
        // making avatar
        Vector2 downRightCorner = new Vector2(Avatar.AVATAR_WIDTH, terrain.groundHeightAt(0));
        avatar = Avatar.create(downRightCorner,inputListener, imageReader);
        gameObjects().addGameObject(avatar);
    }
    private void displayEnergy() {
        // making energy counter
        TextRenderable textRenderable = new TextRenderable(Float.toString(0));
        GameObject numericCounter = new NumericCounter(textRenderable,avatar::getEnergy);
        numericCounter.setTopLeftCorner(new Vector2(50, 50));
        gameObjects().addGameObject(numericCounter, Layer.UI);
    }

    private void createTrees() {
        Flora flora = new Flora(x ->(float)Math.floor(terrain.groundHeightAt(x) / Block.SIZE) * Block.SIZE,
                avatar::addEnergy);
        ArrayList<ArrayList<AvatarObserver>> floraObjects = flora.createInRange(MIN_X,
                (int) windowDimensions.x());
        ArrayList<AvatarObserver> trunks = floraObjects.get(0);
        ArrayList<AvatarObserver> leafs = floraObjects.get(1);
        ArrayList<AvatarObserver> fruits = floraObjects.get(2);

        for (AvatarObserver obj :trunks){
            avatar.registerObserver(obj);
            gameObjects().addGameObject((GameObject)obj,Layer.STATIC_OBJECTS);
        }
        for (AvatarObserver obj :leafs){
            avatar.registerObserver(obj);
            gameObjects().addGameObject((GameObject)obj,LEAF_LAYER);
        }
        for (AvatarObserver obj :fruits){
            avatar.registerObserver(obj);
            gameObjects().addGameObject((GameObject)obj);
        }

    }

}