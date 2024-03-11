package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
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
import pepse.world.trees.Trunk;

import java.util.ArrayList;
import java.util.Random;

public class PepseGameManager extends GameManager {
    public static final int MIN_X = 0;
    private static final float CYCLE_LENGTH = 30f;
    private Vector2 windowDimensions;

    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener
            inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        // initialize fields
        this.windowDimensions = windowController.getWindowDimensions();
        // making sky
        GameObject sky = Sky.create(windowDimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
        // making ground
        Terrain terrain = new Terrain(windowDimensions,(int)new Random().nextGaussian());
        for (Block block: terrain.createInRange(MIN_X,(int)windowDimensions.x())){
            gameObjects().addGameObject(block,Layer.STATIC_OBJECTS);
        }
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

        // making avatar
        int place = 0 + Avatar.AVATAR_WIDTH;
        Vector2 downRightCorner = new Vector2(place, terrain.groundHeightAt(0));
        Avatar avatar = Avatar.create(downRightCorner,inputListener, imageReader);
        gameObjects().addGameObject(avatar);

        // making energy counter
        TextRenderable textRenderable = new TextRenderable(Float.toString(0));
        GameObject numericCounter = new NumericCounter(textRenderable,avatar::getEnergy);
        numericCounter.setTopLeftCorner(new Vector2(50, 50));
        gameObjects().addGameObject(numericCounter, Layer.BACKGROUND );

        // making trees
        Flora flora = new Flora(x -> (float)Math.floor(terrain.groundHeightAt(x) / Block.SIZE) * Block.SIZE,
                 windowDimensions);

        for (GameObject tree:flora.createInRange(MIN_X, (int) windowDimensions.x())) {
            gameObjects().addGameObject(tree);
        }

    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

    }

}